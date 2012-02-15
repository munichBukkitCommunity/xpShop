package me.ibhh.xpShop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class xpShop extends JavaPlugin {

    private String ActionxpShop;
    private int buy;
    private int sell;
    private int buylevel;
    private int selllevel;
    public double getmoney;
    public int SubstractedXP;
    public float Version = 0;
    int rounds1 = 0;
    int rounds = 0;
    public iConomyHandler Geldsystem;
    public PermissionsHandler Permission;
    private Help Help;
    public static String PrefixConsole = "[xpShop] ";
    public static String Prefix = "[xpShop] ";
    private PanelControl panel;
    public ConfigHandler config;
    public xpShopListener ListenerShop;
    private Update upd;
    public boolean blacklisted;

    /**
     * Called by Bukkit on stopping the server
     *
     * @param
     * @return
     */
    @Override
    public void onDisable() {
        Geldsystem = null;
        Permission = null;
        Help = null;
        panel = null;
        config = null;
        ListenerShop = null;
        try {
            finalize();
        } catch (Throwable ew) {
            Logger("cant finalize!", "Error");
        }
        forceUpdate();
        blacklistUpdate();
        Logger("disabled!", "");
    }

    /**
     * Delete an download new version of xpShop in the Update folder.
     *
     * @param
     * @return
     */
    public boolean autoUpdate(String url, String path, String name, String type) {
        try {
            Update.autoDownload(url, path, name, type);
            return true;
        } catch (Exception e) {
            Logger(e.getMessage(), "Error");
            try {
                Update.autoDownload(url, path + "xpShop\\", name, type);
                return true;
            } catch (Exception ex) {
                Logger(ex.getMessage(), "Error");
                return false;
            }
        }
    }

    public void forceUpdate() {
        String URL = "http://ibhh.de:80/aktuelleversion.html";
        if ((UpdateAvailable(URL, Version) == true)) {
            Logger("New version: " + Update.getNewVersion(URL) + " found!", "Warning");
            Logger("******************************************", "Warning");
            Logger("*********** Please update!!!! ************", "Warning");
            Logger("* http://ibhh.de/xpShop.jar *", "Warning");
            Logger("******************************************", "Warning");
            if (getConfig().getBoolean("autodownload") == true) {
                try {
                    String path = "plugins" + "\\";
                    if (autoUpdate("http://ibhh.de/xpShop.jar", path, "xpShop.jar", "forceupdate")) {
                        Logger("Downloaded new Version!", "Warning");
                        Logger("xpShop will be updated on the next restart!", "Warning");
                    } else {
                        Logger(" Cant download new Version!", "Warning");
                    }
                } catch (Exception e) {
                    Logger("Error on donwloading new Version!", "Error");
                    e.printStackTrace();
                }
            } else {
                Logger("Please type [xpShop download] to download manual! ", "Warning");
            }
        }
    }

    public void blacklistUpdate() {
        if (upd.getBlacklisted("http://ibhh.de/BlacklistxpShop.html") == true) {
            blacklisted = true;
            Logger("Your version is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            try {
                String path = "plugins" + "\\";
                if (autoUpdate("http://ibhh.de/xpShop.jar", path, "xpShop.jar", "forceupdate")) {
                    Logger("Downloaded new Version!", "Warning");
                    Logger("xpShop will be updated on the next restart!", "Warning");
                } else {
                    Logger(" Cant download new Version!", "Warning");
                }
            } catch (Exception e) {
                Logger("Error on donwloading new Version!", "Error");
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets version.
     *
     * @param
     * @return float: Version of the installed plugin.
     */
    public float aktuelleVersion() {
        try {
            Version = Float.parseFloat(getDescription().getVersion());
        } catch (Exception e) {
            Logger("Could not parse version in float", "");
        }
        return Version;
    }

    /**
     * Compares Version to newVersion
     *
     * @param url from newVersion file + currentVersion
     * @return true if newVersion recommend.
     */
    public boolean UpdateAvailable(String url, float currVersion) {
        boolean a = false;
        if (Update.getNewVersion(url) > currVersion) {
            a = true;
        }
        return a;
    }

    public void openGUI() {
        panel = new PanelControl(this);
        panel.setSize(400, 300);
        panel.setLocation(200, 300);
        panel.setVisible(true);
    }

    /**
     * Called by Bukkit on starting the server
     *
     * @param
     * @return
     */
    @Override
    public void onEnable() {
        aktuelleVersion();
        upd = new Update(this);
        if (upd.getBlacklisted("http://ibhh.de/BlacklistxpShop.html") == true) {
            blacklisted = true;
        }
        ListenerShop = new xpShopListener(this);

        config = new ConfigHandler(this);
        config.loadConfigonStart();
        config.reload();
        if (!blacklisted) {
            if (getConfig().getBoolean("firstRun")) {
                try {
                    openGUI();
                } catch (Exception e) {
                    Logger("You cant use the gui, notice that.", "Error");
                    getConfig().set("firstRun", false);
                    saveConfig();
                    reloadConfig();
                    config.reload();
                }
            }

            Permission = new PermissionsHandler(this);
            Help = new Help(this);
            Geldsystem = new iConomyHandler(this);
            String URL = "http://ibhh.de:80/aktuelleversion.html";
            if ((UpdateAvailable(URL, Version) == true)) {
                Logger("New version: " + upd.getNewVersion(URL) + " found!", "Warning");
                Logger("******************************************", "Warning");
                Logger("*********** Please update!!!! ************", "Warning");
                Logger("* http://ibhh.de/xpShop.jar *", "Warning");
                Logger("******************************************", "Warning");
                Logger("xpShop will be updated on the next restart!", "Warning");
                Logger("Version: " + Version + " successfully enabled!", "");
            }
        } else {
            Logger("xpShop version " + Version + " is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            Logger("All funktions deactivated to prevent the server!", "Warning");
        }
    }

    /**
     * Called by Bukkit on reloading the server
     *
     * @param
     * @return
     */
    public void onReload() {
        onDisable();
        onEnable();
    }

    /**
     * Called by Bukkit if player posts a command
     *
     * @param none, cause of Bukkit.
     * @return true if no errors happened else return false to Bukkit, then
     * Bukkit prints /xpShop buy <xp|money>.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!blacklisted) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (cmd.getName().equalsIgnoreCase("xpShop")) {
                    switch (args.length) {
                        case 1:
                            ActionxpShop = args[0];
                            if (ActionxpShop.equalsIgnoreCase("infoxp")) {
                                if (Permission.checkpermissions(player, "xpShop.infoxp.own")) {
                                    infoxp(sender, args);
                                    return true;
                                } else {
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("infolevel")) {
                                if (Permission.checkpermissions(player, "xpShop.infolevel.own")) {
                                    infolevel(sender, args);
                                    return true;
                                } else {
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("version")) {
                                PlayerLogger(player, "Version: " + getDescription().getVersion(), "");
                            } else {
                                Help.help(sender, args);
                            }
                            break;
                        case 2:
                            ActionxpShop = args[0];
                            if (ActionxpShop.equals("selllevel")) {
                                if (Permission.checkpermissions(player, "xpShop.selllevel")) {
                                    if (Tools.isInteger(args[1])) {
                                        selllevel = Integer.parseInt(args[1]);
                                        selllevel(player, this.selllevel, true);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equals("buylevel")) {
                                if (Permission.checkpermissions(player, "xpShop.buylevel")) {
                                    if (Tools.isInteger(args[1])) {
                                        buylevel = Integer.parseInt(args[1]);
                                        buylevel(player, this.buylevel, true);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equals("sell")) {
                                if (Permission.checkpermissions(player, "xpShop.sell")) {
                                    if (Tools.isInteger(args[1])) {
                                        sell = Integer.parseInt(args[1]);
                                        sell(player, this.sell, true, "sell");
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equals("buy")) {
                                if (Permission.checkpermissions(player, "xpShop.buy")) {
                                    if (Tools.isInteger(args[1])) {
                                        buy = Integer.parseInt(args[1]);
                                        buy(player, this.buy, true, "buy");
                                        return true;
                                    }
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("infoxp")) {
                                if (Permission.checkpermissions(player, "xpShop.infoxp.other")) {
                                    if (!Tools.isInteger(args[1])) {
                                        infoxp(sender, args);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("infolevel")) {
                                if (Permission.checkpermissions(player, "xpShop.infolevel.other")) {
                                    if (!Tools.isInteger(args[1])) {
                                        infolevel(sender, args);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("help")) {
                                if (Permission.checkpermissions(player, "xpShop.help")) {
                                    if (!Tools.isInteger(args[1])) {
                                        Help.help(player, args);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else {
                                Help.help(sender, args);
                            }
                            break;
                        case 3:
                            ActionxpShop = args[0];
                            if (ActionxpShop.equalsIgnoreCase("info")) {
                                if (Permission.checkpermissions(player, "xpShop.info")) {
                                    if ((!Tools.isInteger(args[1])) && (Tools.isInteger(args[2]))) {
                                        info(player, args);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else if (ActionxpShop.equalsIgnoreCase("send")) {
                                if (Permission.checkpermissions(player, "xpShop.send")) {
                                    if ((!Tools.isInteger(args[1])) && (Tools.isInteger(args[2]))) {
                                        int xp = Integer.parseInt(args[2]);
                                        sendxp(sender, xp, args[1], args);
                                        return true;
                                    }
                                    PlayerLogger(player, config.commanderrornoint, "Error");
                                    return false;
                                }
                            } else {
                                Help.help(sender, args);
                            }
                            break;
                        default:
                            Help.help(player, args);
                            return false;
                    }
                }
                } else if (cmd.getName().equalsIgnoreCase("xpShop")) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("download")) {
                            String path = "plugins" + "\\";
                            autoUpdate("http://ibhh.de/xpShop.jar", path, "xpShop.jar", "forceupdate");
                            Logger("Downloaded new Version!", "Warning");
                            Logger("xpShop will be updated on the next restart!", "Warning");
                            return true;
                        } else if (args[0].equalsIgnoreCase("gui")) {
                            openGUI();
                            return true;
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            onReload();
                            return true;
                        } else if (args[0].equalsIgnoreCase("debug")) {
                            getConfig().set("debug", !getConfig().getBoolean("debug"));
                            Logger("debug set to: " + getConfig().getBoolean("debug"), "");
                            saveConfig();
                            if (config.debug) {
                                Logger("Config saved!", "Debug");
                            }
                            reloadConfig();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            if (config.debug) {
                                Logger("debug reloaded!", "Debug");
                            }
                            config.reload();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            return true;
                        } else if (args[0].equalsIgnoreCase("autodownload")) {
                            getConfig().set("autodownload", !getConfig().getBoolean("autodownload"));
                            Logger("autodownload set to: " + getConfig().getBoolean("autodownload"), "");
                            saveConfig();
                            if (config.debug) {
                                Logger("Config saved!", "Debug");
                            }
                            reloadConfig();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            if (config.debug) {
                                Logger("debug reloaded!", "Debug");
                            }
                            config.reload();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            return true;
                        } else if (args[0].equalsIgnoreCase("firstRun")) {
                            getConfig().set("firstRun", !getConfig().getBoolean("firstRun"));
                            Logger("firstRun set to: " + getConfig().getBoolean("firstRun"), "");
                            saveConfig();
                            if (config.debug) {
                                Logger("Config saved!", "Debug");
                            }
                            reloadConfig();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            if (config.debug) {
                                Logger("debug reloaded!", "Debug");
                            }
                            config.reload();
                            if (config.debug) {
                                Logger("Config reloaded!", "Debug");
                            }
                            return true;
                        }
                    }
                }
            return false;
        } else {
            blacklistLogger(sender);
            return true;
        }
    }

    public static void blacklistLogger(Player sender) {
        if (sender instanceof Player && sender != null) {
            Player player = (Player) sender;
            PlayerLogger(player, "Your xpShop version is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            PlayerLogger(player, "All funktions deactivated to prevent the server!", "Warning");
        } else {
            Logger("Your xpShop version is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            Logger("All funktions deactivated to prevent the server!", "Warning");
        }
    }

    public static void blacklistLogger(CommandSender sender) {
        if (sender instanceof Player && sender != null) {
            Player player = (Player) sender;
            PlayerLogger(player, "Your xpShop version is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            PlayerLogger(player, "All funktions deactivated to prevent the server!", "Warning");
        } else {
            Logger("Your xpShop version is blacklisted because of bugs, after restart an bugfix will be installed!", "Warning");
            Logger("All funktions deactivated to prevent the server!", "Warning");
        }
    }

    public static void Logger(String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Warning") || TYPE.equalsIgnoreCase("Error")) {
            System.err.println(PrefixConsole + TYPE + ": " + msg);
        } else if (TYPE.equalsIgnoreCase("Debug")) {
            System.out.println(PrefixConsole + "Debug: " + msg);
        } else {
            System.out.println(PrefixConsole + msg);
        }
    }

    public static void PlayerLogger(Player p, String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Error")) {
            p.sendMessage(ChatColor.DARK_BLUE + Prefix + ChatColor.GOLD + "Error: " + msg);
        } else {
            p.sendMessage(ChatColor.DARK_BLUE + Prefix + ChatColor.GOLD + msg);
        }
    }

//    protected Player getPlayer(CommandSender sender, String[] args, int index) {
////        if (args.length > index) {
////            OfflinePlayer players = sender.getServer().getOfflinePlayer(args[index]);
////            return (Player) players.getPlayer();
////        }
//        Player newplayer = null;
////        MinecraftServer server = ((CraftServer)this.getServer()).getServer();
////        EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), args[index], new ItemInWorldManager(server.getWorldServer(0)));
////        newplayer = entity == null ? null : (Player)entity.getBukkitEntity();
//        if (args[index] != null) {
//            Boolean exist = false;
//            for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
//                if (p.getName().toLowerCase().equals(args[index].toLowerCase())) {
//                    exist = true;
//                    newplayer = (Player) p;
//                    break;
//                }
//            }
//            if (!exist) {
//                return null;
//            }
//        }
//        if(!(sender instanceof Player))
//            return null;
//        return (Player) newplayer;
//    }
    //	protected static Player getPlayer(CommandSender sender, String[] args, int index)
    //	{
    //		if (args.length > index) {
    //			List<Player> players = sender.getServer().matchPlayer(args[index]);
    //
    //			if (players.isEmpty()) {
    //				sender.sendMessage("Could not find player with the name: " + args[index]);
    //				return null;
    //			}
    //			return (Player)players.get(0);
    //		}
    //
    //		if (isConsole(sender)) {
    //			return null;
    //		}
    //		return (Player)sender;
    //	}
    public void sendxp(CommandSender sender, int giveamount, String empfaenger, String[] args) {
        Player player = (Player) sender;
        Player empfaenger1;
        try {
            empfaenger1 = Bukkit.getServer().getPlayer(empfaenger);
        } catch (Exception e1) {
            PlayerLogger(player, "Player isnt online.", "Error");
            return;
        }
        if (empfaenger1 != null) {
            sell(sender, giveamount, false, "sendxp"); //Trys to substract amount, else stop.
            try {
                buy(empfaenger1, SubstractedXP, false, "sendxp"); //Gives other player XP wich were substracted.
            } catch (Exception e1) {
                buy(player, giveamount, false, "sendxp");
                PlayerLogger(player, "Player isnt online.", "Error");
                return;
            }
            try {
                PlayerLogger(player, (String.format(config.commandsuccesssentxp, SubstractedXP, args[1])), "");
                PlayerLogger(empfaenger1, (String.format(config.commandsuccessrecievedxp, SubstractedXP, sender.getName())), "");
            } catch (NullPointerException e) {
                PlayerLogger(player, "Error!", "Error");
            }
        } else {
            PlayerLogger(player, "Player " + empfaenger + " isnt online.", "Error");
        }
    }

    public double getLevelXP(int level) {
        return 3.5 * level * (level + 1);
    }

    public double getTOTALXP(Player player) {
        int level = player.getLevel();
        float playerExpp = player.getExp();
        int XPinLevel = (int) (((level + 1) * 7) * playerExpp);
        double Exp1 = (3.5 * level * (level + 1)) + XPinLevel;
        return Exp1;

    }

    public void UpdateXP(CommandSender sender, int amount, String von) {
        Player player = (Player) sender;
        double Expaktuell = getTOTALXP(player) + amount;
        double neuesLevel;
        int neuesLevelx;
        double neueXpp;
        try {
            if (Expaktuell >= 0) {
                neuesLevel = (Math.pow((Expaktuell / 3.5 + 0.25), 0.5) - 0.5);
                neuesLevelx = (int) neuesLevel;
                neueXpp = (neuesLevel - neuesLevelx);
                player.setLevel(neuesLevelx);
                player.setExp((float) neueXpp);
            } else {
                PlayerLogger(player, "Invalid exp count: " + amount, "Error");
            }
        } catch (NumberFormatException ex) {
            PlayerLogger(player, "Invalid exp count: " + amount, "Error");
        }
    }

    public void infolevel(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            PlayerLogger(player, String.format(config.infoownLevel, player.getLevel()), "");
        } else if (args.length == 2) {
            Player empfaenger1;
            try {
                try {
                    empfaenger1 = Bukkit.getServer().getPlayer(args[1]);
                } catch (Exception e1) {
                    PlayerLogger(player, "Player isnt online.", "Error");
                    return;
                }
                if (empfaenger1 != null) {
                    PlayerLogger(player, String.format(config.infootherLevel, empfaenger1.getName(), empfaenger1.getLevel()), "");
                }
            } catch (Exception e) {
                PlayerLogger(player, "Player " + args[1] + " isnt online", "Error");
            }
        }
    }

    public void infoxp(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            PlayerLogger(player, String.format(config.infoownXP, (int) getTOTALXP(player)), "");
        } else if (args.length == 2) {
            Player empfaenger1;
            try {
                try {
                    empfaenger1 = Bukkit.getServer().getPlayer(args[1]);
                } catch (Exception e1) {
                    PlayerLogger(player, "Player isnt online.", "Error");
                    return;
                }
                if (empfaenger1 != null) {
                    PlayerLogger(player, String.format(config.infootherXP, empfaenger1.getName(), (int) getTOTALXP(empfaenger1)), "");
                }
            } catch (Exception e) {
                PlayerLogger(player, "Player " + args[1] + " isnt online", "Error");
            }
        }
    }

    /**
     * Called by onCommand and buylevel, buys XP.
     *
     * @param sender, amount, moneyactive = true if you want that player have to
     * buy XP, false if there is an info what that would cost.
     * @return true if no error occurred.
     */
    public boolean buy(CommandSender sender, int buyamount, boolean moneyactive, String von) {
        Player player = (Player) sender;
        double TOTALXPDOUBLE = (buyamount * config.moneytoxp);

        if (buyamount <= 0) {
            if (!von.equals("sendxp")) {
                PlayerLogger(player, "Invalid Amount!", "Error");
            }
            return false;
        }
        boolean valid;
        valid = false;
        if (moneyactive) {
            if (Geldsystem.getBalance156(player) >= TOTALXPDOUBLE) {
                valid = true;
            } else {
                PlayerLogger(player, config.commanderrornotenoughmoney, "Error");
            }
        } else if (von.equals("sendxp")) {
            valid = true;
        }
        if (valid) {
            if (buyamount > 0) {
                UpdateXP(sender, buyamount, "buy");
                if (moneyactive) {
                    Geldsystem.substractmoney156(TOTALXPDOUBLE, player);
                }
            } else {
                if (!von.equals("buylevel")) {
                    PlayerLogger(player, "Invalid exp count: " + buyamount, "Error");
                    PlayerLogger(player, String.format(config.commanderrorinfo, Geldsystem.getBalance156(player), (int) (Geldsystem.getBalance156(player) / getmoney)), "Error");
                }
            }
            if (ActionxpShop.equalsIgnoreCase("buy")) {
                PlayerLogger(player, String.format(config.commandsuccessbuy, (int) TOTALXPDOUBLE, (int) buyamount), "");

            } else if (ActionxpShop.equalsIgnoreCase("info") && von.equals("buylevel") == false) {
                PlayerLogger(player, String.format(config.infoPrefix + " " + config.commandsuccessbuy, (int) TOTALXPDOUBLE, (int) buyamount), "");
            }
            player.saveData();
            return true;
        }
        return false;
    }

    public int sell(CommandSender sender, int sellamount, boolean moneyactive, String von) {

        Player player = (Player) sender;
        try {
            SubstractedXP = 0;
            double TOTAL = getTOTALXP(player);
            int TOTALint = (int) TOTAL;
            getmoney = config.xptomoney;
            if (sellamount <= TOTAL) {
                UpdateXP(sender, -sellamount, "sell");
                if (moneyactive) {
                    Geldsystem.addmoney156(sellamount * getmoney, player);
                }
                SubstractedXP = sellamount;
            } else {
                PlayerLogger(player, "Invalid exp count: " + sellamount, "Error");
                PlayerLogger(player, config.commanderrornotenoughxp, "Error");
                PlayerLogger(player, String.format(config.commanderrorinfo, TOTALint, (int) (TOTAL * getmoney)), "Error");
                return 0;
            }
        } catch (NumberFormatException ex) {
            PlayerLogger(player, "Invalid exp count: " + sellamount, "Error");
            return 0;
        }
        player.saveData();
        if (ActionxpShop.equalsIgnoreCase("sell")) {
            PlayerLogger(player, String.format(config.commandsuccesssell, SubstractedXP, (int) (sellamount * getmoney)), "");
        } else if (ActionxpShop.equalsIgnoreCase("info") && von.equals("selllevel") == false) {
            PlayerLogger(player, String.format(config.infoPrefix + " " + config.commandsuccesssell, SubstractedXP, (int) (sellamount * getmoney)), "");
        }
        return SubstractedXP;
    }

    /**
     * Buys level for a player.
     *
     * @param sender, amount, moneyactive = true if you want that player have to
     * buy XP, false if there is an info what that would cost.
     * @return
     */
    public void buylevel(CommandSender sender, int levelamontbuy, boolean moneyactive) {
        Player player = (Player) sender;
        int level = player.getLevel();
        double money1 = config.moneytoxp;
        double xpNeededForLevel = getLevelXP(levelamontbuy + level);
        double xpAktuell = getTOTALXP(player);
        double neededXP = xpNeededForLevel - xpAktuell;
        if (Geldsystem.getBalance156(player) < (money1 * neededXP)) {
            PlayerLogger(player, "Stopped because of not having enough money!", "Error");
            PlayerLogger(player, "Invalid exp count: " + levelamontbuy, "Error");
        } else {
            if (moneyactive) {
                buy(sender, (int) (neededXP), true, "buylevel");
            }
        }
        if (ActionxpShop.equalsIgnoreCase("buylevel")) {
            if (config.debug) {
                Logger("String: " + config.commandsuccessbuylevel + "moneytoxp: " + config.moneytoxp + "needed xp: " + neededXP, "Debug");
            }
            PlayerLogger(player, String.format(config.commandsuccessbuylevel, (int) (config.moneytoxp * neededXP), ((int) neededXP)), "");
        } else if (ActionxpShop.equalsIgnoreCase("info")) {
            PlayerLogger(player, String.format(config.infoPrefix + " " + config.commandsuccessbuylevel, (int) (config.moneytoxp * neededXP), (int) neededXP), "");
        }
    }

    /**
     * Sells level from a player.
     *
     * @param sender, amount, moneyactive = true if you want that player have to
     * buy XP, false if there is an info what that would cost.
     * @return
     */
    public void selllevel(CommandSender sender, int levelamountsell, boolean moneyactive) {
        Player player = (Player) sender;
        if (player.getLevel() + player.getExp() <= 0.20) {
            PlayerLogger(player, config.commanderrornotenoughxp, "Error");
        } else {
            int level = player.getLevel();
            double money1 = config.moneytoxp;
            double xpNeededForLevel = getLevelXP(level - levelamountsell);
            double xpAktuell = getTOTALXP(player);
            double XP2Sell = xpAktuell - xpNeededForLevel;
            if (XP2Sell >= 0) {
                if (moneyactive) {
                    sell(sender, (int) XP2Sell, true, "selllevel");
                }
            } else {
                PlayerLogger(player, "Invalid exp count: " + levelamountsell, "Error");
                return;
            }
            if (ActionxpShop.equalsIgnoreCase("selllevel")) {
                PlayerLogger(player, String.format(config.commandsuccessselllevel, (int) XP2Sell, (int) (XP2Sell * money1)), "");
            } else if (ActionxpShop.equalsIgnoreCase("info")) {
                PlayerLogger(player, String.format(config.infoPrefix + " " + config.commandsuccessselllevel, (int) XP2Sell, (int) (XP2Sell * money1)), "");
            }
        }
    }

    /**
     * Shows a player how much a action would cost.
     *
     * @param sender, action, amount.
     * @return
     */
    public void info(CommandSender sender, String[] args) {
        if (args.length == 3 && sender instanceof Player) {
            Player player = (Player) sender;
            int nowlevel = player.getLevel();
            float nowxp = player.getExp();
            int temp = Integer.parseInt(args[2]);
            if (args[1].equals("buy")) {
                buy(player, temp, false, "info");
            } else if (args[1].equals("sell")) {
                sell(player, temp, false, "info");
            } else if (args[1].equals("buylevel")) {
                buylevel(player, temp, false);
            } else if (args[1].equals("selllevel")) {
                selllevel(player, temp, false);
            } else if (args[1].equals("send")) {
                PlayerLogger(player, "There is no info for (send)!", "Error");
            } else {
                PlayerLogger(player, "Command not found!", "Error");
            }
            player.setLevel(nowlevel);
            player.setExp(nowxp);
        } else {
            Help.help(sender, args);
        }
    }
}
