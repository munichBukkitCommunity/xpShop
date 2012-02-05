package me.ibhh.xpShop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

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
    private iConomyHandler Geldsystem;
    private PermissionsHandler Permission;
    private Help Help;
    public static boolean debug;
    public static String Prefix = "ChatColor.DARK_BLUE + " + "[AuctionTrade] " + " + ChatColor.GOLD";
    private PanelControl panel;

    /**
     * Called by Bukkit on stopping the server
     *
     * @param
     * @return
     */
    @Override
    public void onDisable() {

        System.out.println("[xpShop] disabled!");

    }

    /**
     * Delete an download new version of xpShop in the Update folder.
     *
     * @param
     * @return
     */
    public void autoUpdate(String url, String path, String name) {
        try {
            Update.autoDownload(url, path, "xpShop.jar");
        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("[xpShop]Could not parse version in float");
        }
        return Version;
    }

    /**
     * Checks version with a http-connection
     *
     * @param
     * @return float: latest recommend build.
     */
    public float getNewVersion(String url) {
        float rt2 = 0;
        String zeile;

        try {
            URL myConnection = new URL(url);
            URLConnection connectMe = myConnection.openConnection();

            InputStreamReader lineReader = new InputStreamReader(connectMe.getInputStream());
            BufferedReader br = new BufferedReader(new BufferedReader(lineReader));
            zeile = br.readLine();
            rt2 = Float.parseFloat(zeile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("[xpShop]Exception: IOException!");
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[xpShop]Exception: Exception!");
            return 0;
        }
        return rt2;
    }

    /**
     * Compares Version to newVersion
     *
     * @param url from newVersion file + currentVersion
     * @return true if newVersion recommend.
     */
    public boolean UpdateAvailable(String url, float currVersion) {
        boolean a = false;
        if (getNewVersion(url) > currVersion) {
            a = true;
        }
        return a;
    }

    /**
     * Called by Bukkit on starting the server
     *
     * @param
     * @return
     */
    @Override
    public void onEnable() {
        try {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
            reloadConfig();
            System.out.println("[xpShop] Config file found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        debug = getConfig().getBoolean("debug");

        if (getConfig().getBoolean("firstRun")) {
            panel = new PanelControl(this);
            panel.setSize(400, 300);
            panel.setLocation(200, 300);
            panel.setVisible(true);
        }
        Permission = new PermissionsHandler(this);
        Help = new Help(this);

        System.out.println("[xpShop] Version: " + Version + " successfully enabled!");

        String URL = "http://ibhh.de:80/aktuelleversion.html";
        if ((UpdateAvailable(URL, Version) == true)) {
            System.out.println("[xpShop] New version: " + getNewVersion(URL) + " found!");
            System.out.println("[xpShop] ******************************************");
            System.out.println("[xpShop] *********** Please update!!!! ************");
            System.out.println("[xpShop] * http://ibhh.de/xpShop.jar *");
            System.out.println("[xpShop] ******************************************");
            if (getConfig().getBoolean("autodownload") == true) {
                try {
                    String path = getDataFolder().toString() + "/Update/";
                    autoUpdate("http://ibhh.de/xpShop.jar", path, "xpShop.jar");
                } catch (Exception e) {
                    System.out.println("[xpShop] " + "Error on checking permissions with PermissionsEx!");
                    e.printStackTrace();
                    return;
                }

            } else {
                System.out.println("[xpShop] Please type [xpShop download] to download manual! ");
            }
        }
        Permission = new PermissionsHandler(this);
    }

    /**
     * Called by Bukkit on reloading the server
     *
     * @param
     * @return
     */
    public void onReload() {
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
                        }
                        if (ActionxpShop.equalsIgnoreCase("infolevel")) {
                            if (Permission.checkpermissions(player, "xpShop.infolevel.own")) {
                                infolevel(sender, args);
                                return true;
                            } else {
                                return false;
                            }
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
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equals("buylevel")) {
                            if (Permission.checkpermissions(player, "xpShop.buylevel")) {
                                if (Tools.isInteger(args[1])) {
                                    buylevel = Integer.parseInt(args[1]);
                                    buylevel(player, this.buylevel, true);
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equals("sell")) {
                            if (Permission.checkpermissions(player, "xpShop.sell")) {
                                if (Tools.isInteger(args[1])) {
                                    sell = Integer.parseInt(args[1]);
                                    sell(player, this.sell, true, "sell");
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equals("buy")) {
                            if (Permission.checkpermissions(player, "xpShop.buy")) {
                                if (Tools.isInteger(args[1])) {
                                    buy = Integer.parseInt(args[1]);
                                    buy(player, this.buy, true, "buy");
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equalsIgnoreCase("infoxp")) {
                            if (Permission.checkpermissions(player, "xpShop.infoxp.other")) {
                                if (!Tools.isInteger(args[1])) {
                                    infoxp(sender, args);
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equalsIgnoreCase("infolevel")) {
                            if (Permission.checkpermissions(player, "xpShop.infolevel.other")) {
                                if (!Tools.isInteger(args[1])) {
                                    infolevel(sender, args);
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equalsIgnoreCase("help")) {
                            if (Permission.checkpermissions(player, "xpShop.help")) {
                                if (!Tools.isInteger(args[1])) {
                                    Help.help(player, args);
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
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
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        if (ActionxpShop.equalsIgnoreCase("send")) {
                            if (Permission.checkpermissions(player, "xpShop.send")) {
                                if ((!Tools.isInteger(args[1])) && (Tools.isInteger(args[2]))) {
                                    int xp = Integer.parseInt(args[2]);
                                    sendxp(sender, xp, args[1], args);
                                    return true;
                                }
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + getConfig().getString(new StringBuilder("command.error.noint.").append(getConfig().getString("language")).toString()));
                                return false;
                            }
                        }
                        break;
                    default:
                        Help.help(player, args);
                        return false;
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("xpShop")) {
            if (args.length == 1) {
                if (args[0].equals("download")) {
                    String path = getDataFolder().toString() + "/Update/";
                    autoUpdate("http://ibhh.de/xpShop.jar", path, "xpShop.jar");
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public static void Logger(String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Warning") || TYPE.equalsIgnoreCase("Error")) {
            System.err.println("[AuctionTrade] " + TYPE + msg);
        } else if (TYPE.equalsIgnoreCase("Debug")) {
            System.out.println("[AuctionTrade] Debug:" + msg);
        } else {
            System.out.println("[AuctionTrade] " + msg);
        }
    }

    public static void PlayerLogger(Player p, String msg, String TYPE) {
        if (TYPE.equalsIgnoreCase("Error")) {
            p.sendMessage(Prefix + "Error: " + msg);
        } else {
            p.sendMessage(Prefix + msg);
        }
    }

    protected Player getPlayer(CommandSender sender, String[] args, int index) {
//        if (args.length > index) {
//            OfflinePlayer players = sender.getServer().getOfflinePlayer(args[index]);
//            return (Player) players.getPlayer();
//        }
        Player newplayer = null;
//        MinecraftServer server = ((CraftServer)this.getServer()).getServer();
//        EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), args[index], new ItemInWorldManager(server.getWorldServer(0)));
//        newplayer = entity == null ? null : (Player)entity.getBukkitEntity();
        if (args[index] != null) {
            Boolean exist = false;
            for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
                if (p.getName().toLowerCase().equals(args[index].toLowerCase())) {
                    exist = true;
                    newplayer = (Player) p;
                    break;
                }
            }
            if (!exist) {
                return null;
            }
        }
        if(!(sender instanceof Player))
            return null;
        return (Player) newplayer;
    }
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
        try {

            if (this.getServer().getOfflinePlayer(empfaenger).hasPlayedBefore()) {
                Player empfaenger1 = (Player) getPlayer(sender, args, 1);
                sell(sender, giveamount, false, "sendxp"); //Trys to substract amount, else stop.
                buy(empfaenger1, SubstractedXP, false, "sendxp"); //Gives other player XP wich were substracted.
                try {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("command.success." + "sentxp" + "." + getConfig().getString("language")), SubstractedXP, args[1]));
                    empfaenger1.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("command.success." + "recievedxp" + "." + getConfig().getString("language")), SubstractedXP, sender.getName()));
                } catch (NullPointerException e) {
                    player.sendMessage("Error!");
                }
            } else {
                player.sendMessage("Player wasnt online before.");
            }
        } catch (Exception e) {
            player.sendMessage("Player isnt online");
        }

    }

    public double getLevelXP(int level) {
        return 3.5 * level * (level + 1);
    }

    public double getTOTALXP(CommandSender sender) {
        Player player = (Player) sender;
        int level = player.getLevel();
        float playerExpp = player.getExp();
        int XPinLevel = (int) (((level + 1) * 7) * playerExpp);
        double Exp1 = (3.5 * level * (level + 1)) + XPinLevel;
        return Exp1;

    }

    public void UpdateXP(CommandSender sender, int amount, String von) {
        Player player = (Player) sender;
        double Expaktuell = getTOTALXP(sender) + amount;
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
                player.sendMessage("Invalid exp count: " + amount);
            }
        } catch (NumberFormatException ex) {
            player.sendMessage("Invalid exp count: " + amount);
        }
    }

    public void infolevel(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("info.ownLevel." + getConfig().getString("language")), player.getLevel()));
        } else if (args.length == 2) {
            try {
                Player empfaenger1 = (Player) getPlayer(sender, args, 1);
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("info.otherLevel." + getConfig().getString("language")), empfaenger1.getName(), empfaenger1.getLevel()));
            } catch (Exception e) {
                player.sendMessage("Player isnt online");
            }
        }
    }

    public void infoxp(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("info.ownXP." + getConfig().getString("language")), (int) getTOTALXP(sender)));
        } else if (args.length == 2) {
            try {
                Player empfaenger1 = (Player) getPlayer(sender, args, 1);
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("info.otherXP." + getConfig().getString("language")), empfaenger1.getName(), (int) getTOTALXP(empfaenger1)));
            } catch (Exception e) {
                player.sendMessage("Player isnt online");
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
        double TOTALXPDOUBLE = (buyamount * (getConfig().getDouble("moneytoxp")));

        if (buyamount <= 0) {
            if (!von.equals("sendxp")) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "Invalid Amount!");
            }
            return false;
        }
        boolean valid;
        valid = false;
        if (moneyactive) {
            if (Geldsystem.getBalance156(player) >= TOTALXPDOUBLE) {
                valid = true;
            } else {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("command.error.notenoughmoney." + getConfig().getString("language"))));
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
                    player.sendMessage("Invalid exp count: " + buyamount);
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("command.error.info." + getConfig().getString("language"))), Geldsystem.getBalance156(player), (int) (Geldsystem.getBalance156(player) / getmoney)));
                }
            }
            if (ActionxpShop.equalsIgnoreCase("buy")) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("command.success." + "buy" + "." + getConfig().getString("language")), (int) TOTALXPDOUBLE, (int) buyamount));

            } else if (ActionxpShop.equalsIgnoreCase("info") && von.equals("buylevel") == false) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("info.prefix." + getConfig().getString("language"))) + " " + (getConfig().getString("command.success." + "buy" + "." + getConfig().getString("language"))), (int) TOTALXPDOUBLE, (int) buyamount));
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
            double TOTAL = getTOTALXP(sender);
            int TOTALint = (int) TOTAL;
            getmoney = (getConfig().getDouble("xptomoney"));
            if (sellamount <= TOTAL) {
                UpdateXP(sender, -sellamount, "sell");
                if (moneyactive) {
                    Geldsystem.addmoney156(sellamount * getmoney, player);
                }
                SubstractedXP = sellamount;
            } else {
                player.sendMessage("Invalid exp count: " + sellamount);
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("command.error.info." + getConfig().getString("language"))), TOTALint, (int) (TOTAL * getmoney)));
                return 0;
            }
        } catch (NumberFormatException ex) {
            player.sendMessage("Invalid exp count: " + sellamount);
            return 0;
        }
        player.saveData();
        if (ActionxpShop.equalsIgnoreCase("sell")) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("command.success.sell." + getConfig().getString("language"))), SubstractedXP, (int) (sellamount * getmoney)));
        } else if (ActionxpShop.equalsIgnoreCase("info") && von.equals("selllevel") == false) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("info.prefix." + getConfig().getString("language"))) + " " + (getConfig().getString("command.success.sell." + getConfig().getString("language"))), SubstractedXP, (int) (sellamount * getmoney)));
        }
        return SubstractedXP;
    }

    /**
     * Checks players permissions. Works with Bukkitpermissions and
     * PermissionsEX
     *
     * @param sender, action, which should be performed.
     * @return true if player has permission.
     */
    public boolean checkpermissions(CommandSender sender, String[] args, String other) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!other.isEmpty()) {
                if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
                    try {
                        if (player.hasPermission("xpShop." + other)) {
                            return true;
                        } //if(permissions.has(player, "xpShop." + action))
                        else {
                            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("[xpShop] " + "Error on checking permissions with BukkitPermissions!");
                        player.sendMessage("[xpShop] " + "Error on checking permissions with BukkitPermissions!");
                        e.printStackTrace();
                        return false;
                    }

                } else {
                    if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
                        try {
                            PermissionManager permissions = PermissionsEx.getPermissionManager();

                            // Permission check
                            if (permissions.has(player, "xpShop." + other)) {
                                // yay!
                                return true;
                            } else {
                                // houston, we have a problem :)
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("[xpShop] " + "Error on checking permissions with PermissionsEx!");
                            player.sendMessage("[xpShop] " + "Error on checking permissions with PermissionsEx!");
                            e.printStackTrace();
                            return false;
                        }
                    } else {
                        System.out.println("PermissionsEx plugin are not found.");
                        return false;
                    }
                }
            } else {
                if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
                    try {
                        if (args[0].equals("infolevel") || args[0].equals("infoxp")) {
                            if (args.length == 2) {
                                if (player.hasPermission("xpShop." + args[0] + ".other")) {
                                    return true;
                                } //if(permissions.has(player, "xpShop." + other))
                                else {
                                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                    return false;
                                }
                            } else if (args.length == 1) {
                                if (player.hasPermission("xpShop." + args[0] + ".own")) {
                                    return true;
                                } //if(permissions.has(player, "xpShop." + other))
                                else {
                                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if (player.hasPermission("xpShop." + args[0])) {
                            return true;
                        } //if(permissions.has(player, "xpShop." + other))
                        else {
                            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("[xpShop] " + "Error on checking permissions with BukkitPermissions!");
                        player.sendMessage("[xpShop] " + "Error on checking permissions with BukkitPermissions!");
                        e.printStackTrace();
                        return false;
                    }

                } else {
                    if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
                        try {
                            PermissionManager permissions = PermissionsEx.getPermissionManager();
                            if (args[0].equals("infolevel") || args[0].equals("infoxp")) {
                                if (args.length == 2) {
                                    if (permissions.has(player, "xpShop." + args[0] + ".other")) {
                                        return true;
                                    } //if(permissions.has(player, "xpShop." + other))
                                    else {
                                        player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                        return false;
                                    }
                                } else if (args.length == 1) {
                                    if (permissions.has(player, "xpShop." + args[0] + ".own")) {
                                        return true;
                                    } //if(permissions.has(player, "xpShop." + other))
                                    else {
                                        player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                        return false;
                                    }
                                } else {
                                    return false;
                                }

                            } else // Permission check
                            if (permissions.has(player, "xpShop." + args[0])) {
                                // yay!
                                return true;
                            } else {
                                // houston, we have a problem :)
                                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
                                return false;
                            }
                        } catch (Exception e) {
                            System.out.println("[xpShop] " + "Error on checking permissions with PermissionsEx!");
                            player.sendMessage("[xpShop] " + "Error on checking permissions with PermissionsEx!");
                            e.printStackTrace();
                            return false;
                        }
                    } else {
                        System.out.println("PermissionsEx plugin are not found.");
                        return false;
                    }
                }
            }
        } //if (sender instanceof Player)
        else {
            System.out.println("[xpShop] " + (getConfig().getString("command.error.noplayer" + getConfig().getString("language"))));
            return false;
        }

    } //public boolean checkpermissions(CommandSender sender, String other)

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
        double money1 = (getConfig().getDouble("moneytoxp"));
        double xpNeededForLevel = getLevelXP(levelamontbuy + level);
        double xpAktuell = getTOTALXP(sender);
        double neededXP = xpNeededForLevel - xpAktuell;
        if (Geldsystem.getBalance156(player) < (money1 * neededXP)) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "Stopped because of not having enough money!");
            player.sendMessage("Invalid exp count: " + levelamontbuy);
        } else {
            if (moneyactive) {
                buy(sender, (int) (neededXP), true, "buylevel");
            }
        }
        if (ActionxpShop.equalsIgnoreCase("buylevel")) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("command.success." + "buylevel" + "." + getConfig().getString("language")), (int) (getConfig().getDouble("moneytoxp") * neededXP), (int) neededXP));
        } else if (ActionxpShop.equalsIgnoreCase("info")) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format(getConfig().getString("info.prefix." + getConfig().getString("language")) + " " + (getConfig().getString("command.success.buylevel" + "." + getConfig().getString("language"))), (int) (getConfig().getDouble("moneytoxp") * neededXP), (int) neededXP));
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
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
            return;
        } else {
            int level = player.getLevel();
            double money1 = (getConfig().getDouble("moneytoxp"));
            double xpNeededForLevel = getLevelXP(level - levelamountsell);
            double xpAktuell = getTOTALXP(sender);
            double XP2Sell = xpAktuell - xpNeededForLevel;
            if (XP2Sell >= 0) {
                if (moneyactive) {
                    sell(sender, (int) XP2Sell, true, "selllevel");
                }
            } else {
                player.sendMessage("Invalid exp count: " + levelamountsell);
                return;
            }
            if (ActionxpShop.equalsIgnoreCase("selllevel")) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("command.success." + "selllevel" + "." + getConfig().getString("language"))), (int) XP2Sell, (int) (XP2Sell * money1)));
            } else if (ActionxpShop.equalsIgnoreCase("info")) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + String.format((getConfig().getString("info.prefix." + getConfig().getString("language"))) + " " + (getConfig().getString("command.success.selllevel" + "." + getConfig().getString("language"))), (int) XP2Sell, (int) (XP2Sell * money1)));
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
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "There is no info for (send)!");
            } else {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "Error: Command not found!");
            }
            player.setLevel(nowlevel);
            player.setExp(nowxp);
        } else {
            Help.help(sender, args);
            return;
        }
    }
}