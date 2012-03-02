package me.ibhh.xpShop;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class xpShopListener implements Listener {

    private final xpShop plugin;
    private PermissionsHandler Permissions;
    private String[] split;

    public xpShopListener(xpShop xpShop) {
        this.plugin = xpShop;
        this.Permissions = new PermissionsHandler(plugin);
        xpShop.getServer().getPluginManager().registerEvents(this, xpShop);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void join(PlayerJoinEvent event) {
        if (plugin.config.usedbtomanageXP) {
            String playername;
            Player player = event.getPlayer();
            double XP = plugin.getTOTALXP(player);
            playername = player.getName();
            if (plugin.config.debug) {
                plugin.Logger("Playername (joined): " + playername, "Debug");
            }
            try {
                if (plugin.SQL.isindb(playername)) {
                    if (plugin.config.debug) {
                        plugin.Logger("Playername is in db: " + playername + "With " + XP + " XP! DB: " + plugin.SQL.getXP(playername), "Debug");
                    }
                    XP = plugin.SQL.getXP(playername);
                } else {
                    XP = plugin.getTOTALXP(player);
                    plugin.SQL.InsertAuction(playername, (int) XP);
                    if (plugin.config.debug) {
                        plugin.Logger("Playername insert into db: " + playername + "With " + XP + " XP! DB: " + plugin.SQL.getXP(playername), "Debug");
                    }
                }
            } catch (SQLException we) {
                return;
            }
            player.setLevel(0);
            player.setExp(0);
            plugin.UpdateXP(player, (int) XP, "Join");
            player.saveData();
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void Verzaubern(PlayerLevelChangeEvent event) {
        if (plugin.config.usedbtomanageXP) {
            final Player player = event.getPlayer();
            final String playername = player.getName();
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (plugin.config.debug) {
                        plugin.Logger("Saving new XP!", "");
                    }
                    double XP;
                    XP = plugin.getTOTALXP(player);
                    plugin.SQL.UpdateXP(playername, (int) XP);
                    if (plugin.config.debug) {
                        try {
                            plugin.Logger("Player updated into db: " + playername + "With " + plugin.getTOTALXP(player) + " XP! DB: " + plugin.SQL.getXP(playername), "Debug");
                        } catch (SQLException ex) {
                            Logger.getLogger(xpShopListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, 2L);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void change(PlayerExpChangeEvent event) {
        if (plugin.config.usedbtomanageXP) {
            final Player player = event.getPlayer();
            final String playername = player.getName();
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (plugin.config.debug) {
                        plugin.Logger("Saving new XP!", "");
                    }
                    double XP;
                    XP = plugin.getTOTALXP(player);
                    plugin.SQL.UpdateXP(playername, (int) XP);
                    if (plugin.config.debug) {
                        try {
                            plugin.Logger("Player updated into db: " + playername + "With " + plugin.getTOTALXP(player) + " XP! DB: " + plugin.SQL.getXP(playername), "Debug");
                        } catch (SQLException ex) {
                            Logger.getLogger(xpShopListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, 2L);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void death(PlayerDeathEvent event) {
        if (plugin.config.usedbtomanageXP) {
            double XP;
            Player player = (Player) event.getEntity();
            XP = plugin.getTOTALXP(player);
            plugin.SQL.UpdateXP(player.getName(), (int) XP);
            if (plugin.config.debug) {
                try {
                    plugin.Logger("Player updated into db: " + player.getName() + " With " + plugin.getTOTALXP(player) + " XP! DB: " + plugin.SQL.getXP(player.getName()), "Debug");
                } catch (SQLException ex) {
                    Logger.getLogger(xpShopListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void kick(PlayerKickEvent event) {
        if (plugin.config.usedbtomanageXP) {
            final Player player = event.getPlayer();
            final String playername = player.getName();
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (plugin.config.debug) {
                        plugin.Logger("Saving new XP!", "");
                    }
                    double XP;
                    XP = plugin.getTOTALXP(player);
                    plugin.SQL.UpdateXP(playername, (int) XP);
                    if (plugin.config.debug) {
                        try {
                            plugin.Logger("Player updated into db: " + playername + "With " + plugin.getTOTALXP(player) + " XP! DB: " + plugin.SQL.getXP(player.getName()), "Debug");
                        } catch (SQLException ex) {
                            Logger.getLogger(xpShopListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, 2L);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void quit(PlayerQuitEvent event) {
        if (plugin.config.usedbtomanageXP) {
            final Player player = event.getPlayer();
            final String playername = player.getName();
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (plugin.config.debug) {
                        plugin.Logger("Saving new XP!", "");
                    }
                    double XP;
                    XP = plugin.getTOTALXP(player);
                    plugin.SQL.UpdateXP(playername, (int) XP);
                    if (plugin.config.debug) {
                        try {
                            plugin.Logger("Player updated into db: " + playername + "With " + plugin.getTOTALXP(player) + " XP! DB: " + plugin.SQL.getXP(playername), "Debug");
                        } catch (SQLException ex) {
                            Logger.getLogger(xpShopListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, 2L);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void aendern(SignChangeEvent event) {
        Player p = event.getPlayer();
        String[] line = event.getLines();
        if (plugin.config.debug) {
            plugin.Logger("First Line " + line[0], "Debug");
        }
        if (event.getLine(0).equalsIgnoreCase("[xpShop]")) {
            if (plugin.config.debug) {
                plugin.Logger("First Line [xpShop]", "Debug");
            }
            if (!plugin.Blacklistcode.startsWith("1", 10)) {
                if (plugin.config.debug) {
                    plugin.Logger(plugin.Blacklistcode, "Debug");
                }
                try {
                    if (blockIsValid(line, "create", p)) {
                        if (plugin.config.debug) {
                            plugin.Logger("Sign is valid", "Debug");
                        }
                        if (!line[1].equalsIgnoreCase("AdminShop")) {
                            if (plugin.Permission.checkpermissions(p, "xpShop.create.own")) {
                                if (plugin.config.debug) {
                                    plugin.Logger("First line != null", "Debug");
                                }
                                event.setLine(1, event.getPlayer().getDisplayName());
                                plugin.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                            } else {
                                if (plugin.config.debug) {
                                    plugin.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create", "Debug");
                                }
                                plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                                event.setCancelled(true);
                            }
                        } else if (line[1].equalsIgnoreCase(p.getDisplayName())) {
                            if (plugin.Permission.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                                event.setLine(0, "[xpShop]");
                            } else {
                                if (plugin.config.debug) {
                                    plugin.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create", "Debug");
                                }
                                plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                                event.setCancelled(true);
                            }
                        } else if (line[1].equalsIgnoreCase("AdminShop")) {
                            if (plugin.Permission.checkpermissions(p, "xpShop.create.admin")) {
                                if (plugin.config.debug) {
                                    plugin.Logger("Player " + p.getDisplayName() + " has permission: xpShop.create.admin", "Debug");
                                }
                                plugin.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                            } else {
                                if (plugin.config.debug) {
                                    plugin.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create.admin", "Debug");
                                }
                                plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        if (plugin.config.debug) {
                            plugin.Logger("Sign is not valid", "Debug");
                        }
                        plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                        event.setCancelled(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    event.setCancelled(true);
                    plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                }
            } else {
                if (plugin.config.debug) {
                    plugin.Logger(plugin.Blacklistcode, "Debug");
                }
                plugin.blacklistLogger(p);
                event.setCancelled(true);
            }
        }
    }

//    @EventHandler
//    public void onPlace(BlockPlaceEvent event) {
//        if ((event.getBlock() instanceof Sign)) {
//            Sign sign = (Sign) event.getBlock().getState();
//            String[] line = sign.getLines();
//            Player p = event.getPlayer();
//            if (sign.getLine(0).equalsIgnoreCase("[xpShop]")) {
//                try {
//                    if (plugin.Permission.checkpermissions(p, "xpShop.create")) {
//                        if (blockIsValid(line, "create", p)) {
//                            if (!sign.getLine(1).isEmpty()) {
//                                if (plugin.Permission.checkpermissions(p, "xpShop.create.admin")) {
//                                    plugin.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
//                                } else {
//                                    sign.setLine(1, event.getPlayer().getName());
//                                }
//                            } else {
//                                plugin.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
//                                sign.setLine(0, "[xpShop]");
//                            }
//                        } else {
//                            plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
//                            event.setCancelled(true);
//                        }
//                    } else {
//                        event.setCancelled(true);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    event.setCancelled(true);
//                    plugin.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
//                }
//            }
//        }
//    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!(event.getBlock().getState() instanceof Sign)) {
            Block temp = event.getBlock().getRelative(BlockFace.EAST);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    if (!plugin.Blacklistcode.startsWith("1", 12)) {
                        String[] line = s1.getLines();
                        if (this.blockIsValid(line, "break", p)) {
                            if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                event.setCancelled(true);
                            } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(p, "Destroying xpShop!", "");
                            } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.WEST);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (!plugin.Blacklistcode.startsWith("1", 12)) {
                        if (this.blockIsValid(line, "break", p)) {
                            if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                event.setCancelled(true);
                            } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(p, "Destroying xpShop!", "");
                            } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.NORTH);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (!plugin.Blacklistcode.startsWith("1", 12)) {
                        if (this.blockIsValid(line, "break", p)) {
                            if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                event.setCancelled(true);
                            } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(p, "Destroying xpShop!", "");
                            } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.SOUTH);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (!plugin.Blacklistcode.startsWith("1", 12)) {
                        if (this.blockIsValid(line, "break", p)) {
                            if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                event.setCancelled(true);
                            } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(p, "Destroying xpShop!", "");
                            } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.UP);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (!plugin.Blacklistcode.startsWith("1", 12)) {
                        if (this.blockIsValid(line, "break", p)) {
                            if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                event.setCancelled(true);
                            } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                                plugin.PlayerLogger(p, "Destroying xpShop!", "");
                            } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                                plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            Sign s = (Sign) event.getBlock().getState();
            String[] line = s.getLines();
            if (plugin.config.debug) {
                plugin.Logger("Line 0: " + line[0], "Debug");
            }
            if (line[0].equalsIgnoreCase("[xpShop]")) {
                if (!plugin.Blacklistcode.startsWith("1", 12)) {
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (s.getLine(1).equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            plugin.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            plugin.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else {
                    plugin.blacklistLogger(p);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (!p.isSneaking())) { // && !(p.isSneaking())
                Sign s = (Sign) event.getClickedBlock().getState();
                String[] line = s.getLines();
                String playername = p.getName();
                Player player = p;
                if (line[0].equalsIgnoreCase("[xpShop]")) {
                    if (!plugin.Blacklistcode.startsWith("1", 11)) {
                        if (this.blockIsValid(line, "Interact", p)) {
                            if (this.Permissions.checkpermissions(p, "xpShop.use")) {
                                if (line[1].equalsIgnoreCase("AdminShop")) {
                                    double price = getPrice(s, p, true);
                                    if (price > 0) {
                                        if ((plugin.Geldsystem.getBalance(p) - price) >= 0) {
                                            plugin.Geldsystem.substract(price, p);
                                            plugin.UpdateXP(p, (Integer.parseInt(s.getLine(2))), "Sign");
                                            plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccessbuy, s.getLine(2), "Admin", split[0]), "");
                                        } else {
                                            plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                        }
                                    } else {
                                        plugin.PlayerLogger(p, plugin.config.Shoperrorcantbuyhere, "Error");
                                    }
                                } else {
                                    if(plugin.config.usedbtomanageXP){
                                        try {
                                            if(plugin.SQL.isindb(line[1])){
                                                int XPPlayer = plugin.SQL.getXP(playername);
                                                if (plugin.SQL.getXP(line[1]) >= Integer.parseInt(line[2])) {
                                                if (getPrice(s, p, true) > 0) {
                                                    double price = getPrice(s, p, true);
                                                    if ((plugin.Geldsystem.getBalance(p) - price) >= 0) {
                                                        plugin.Geldsystem.substract(price, p);
                                                        plugin.UpdateXP(p, Integer.parseInt(line[2]), "Sign");
                                                        plugin.SQL.UpdateXP(playername, XPPlayer + Integer.parseInt(line[2]));
                                                        plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccessbuy, s.getLine(2), s.getLine(1), split[0]), "");
                                                        plugin.SQL.UpdateXP(line[1], plugin.SQL.getXP(line[1]) - Integer.parseInt(line[2]));
                                                        plugin.Geldsystem.addmoney(price, line[1]);
                                                        if(plugin.getServer().getPlayer(line[1]) != null){
                                                            Player Empfaenger = plugin.getServer().getPlayer(line[1]);
                                                            plugin.UpdateXP(Empfaenger, -(Integer.parseInt(line[2])), "Sign");
                                                            plugin.PlayerLogger(Empfaenger, String.format(plugin.config.Shopsuccesssellerbuy, s.getLine(2), p.getDisplayName(), split[0]), "");
                                                        }
                                                    } else {
                                                        plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                                    }
                                                } else {
                                                    plugin.PlayerLogger(p, plugin.config.Shoperrorcantbuyhere, "Error");
                                                }
                                            } else {
                                                plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughxpseller, "Error");
                                            }
                                            } else {
                                                plugin.PlayerLogger(player, line[2] + " " + plugin.config.playerwasntonline, "Error");
                                            }
                                        } catch (Exception e){
                                            plugin.PlayerLogger(player, line[2] + " " + plugin.config.playerwasntonline, "Error");
                                        }
  
                                    } else {
                                    Player empfaenger = plugin.getmyOfflinePlayer(line, 1);
                                    if (empfaenger != null) {
                                        if (plugin.config.getPlayerConfig(empfaenger, p)) {
                                            if (plugin.getTOTALXP(empfaenger) >= Integer.parseInt(line[2])) {
                                                if (getPrice(s, p, true) > 0) {
                                                    double price = getPrice(s, p, true);
                                                    if ((plugin.Geldsystem.getBalance(p) - price) >= 0) {
                                                        plugin.Geldsystem.substract(price, p);
                                                        plugin.UpdateXP(p, (Integer.parseInt(s.getLine(2))), "Sign");
                                                        plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccessbuy, s.getLine(2), s.getLine(1), split[0]), "");
                                                        plugin.UpdateXP(empfaenger, -(Integer.parseInt(s.getLine(2))), "Sign");
                                                        plugin.Geldsystem.addmoney(price, empfaenger);
                                                        empfaenger.saveData();
                                                        plugin.PlayerLogger(empfaenger, String.format(plugin.config.Shopsuccesssellerbuy, s.getLine(2), p.getDisplayName(), split[0]), "");
                                                    } else {
                                                        plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                                    }
                                                } else {
                                                    plugin.PlayerLogger(p, plugin.config.Shoperrorcantbuyhere, "Error");
                                                }
                                            } else {
                                                plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughxpseller, "Error");
                                            }
                                        }
                                    } else {
                                        plugin.PlayerLogger(p, line[1] + " " + plugin.config.playerwasntonline, "Error");
                                    }
                                    }
                                }
                            }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (!p.isSneaking())) { // && !(p.isSneaking())
                Sign s = (Sign) event.getClickedBlock().getState();
                String[] line = s.getLines();
                String playername = p.getName();
                Player player = p;
                if (line[0].equalsIgnoreCase("[xpShop]")) {
                    if (!plugin.Blacklistcode.startsWith("1", 11)) {
                        if (this.blockIsValid(line, "Interact", p)) {
                            if (this.Permissions.checkpermissions(p, "xpShop.use")) {
                                if (line[1].equalsIgnoreCase("AdminShop")) {
                                    if (plugin.getTOTALXP(p) >= Integer.parseInt(line[2])) {
                                        if (getPrice(s, p, false) > 0) {
                                            double price = getPrice(s, p, false);
                                            plugin.Geldsystem.addmoney(price, p);
                                            plugin.UpdateXP(p, -(Integer.parseInt(s.getLine(2))), "Sign");
                                            plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccesssell, s.getLine(2), "Admin", split[1]), "");
                                        } else {
                                            plugin.PlayerLogger(p, plugin.config.Shoperrorcantsellhere, "Error");
                                        }
                                    } else {
                                        plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughxpconsumer, "Error");
                                    }
                                } else {
                                    if(plugin.config.usedbtomanageXP){
                                        try {
                                            if(plugin.SQL.isindb(line[1])){
                                                int XPPlayer = plugin.SQL.getXP(playername);
                                            if (plugin.SQL.getXP(playername) >= Integer.parseInt(line[2])) {
                                                if (getPrice(s, p, false) > 0) {
                                                    double price = getPrice(s, p, false);
                                                    if ((plugin.Geldsystem.getBalance(line[1]) - price) >= 0) {
                                                        plugin.Geldsystem.substract(price, line[1]);
                                                        plugin.Geldsystem.addmoney(price, p);
                                                        plugin.UpdateXP(p, -(Integer.parseInt(line[2])), "Sign");
                                                        plugin.SQL.UpdateXP(playername, XPPlayer - Integer.parseInt(line[2]));
                                                        plugin.SQL.UpdateXP(line[1], plugin.SQL.getXP(line[1]) + Integer.parseInt(line[2]));
                                                        plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccesssell, line[2], line[1], split[1]), "");
                                                        Player empfaenger = plugin.getServer().getPlayer(line[1]);
                                                        if(empfaenger != null){
                                                            plugin.UpdateXP(empfaenger, (Integer.parseInt(line[2])), "Sign");
                                                            plugin.PlayerLogger(empfaenger, String.format(plugin.config.Shopsuccesssellerselled, s.getLine(2), playername, split[1]), "");
                                                        }
                                                    } else {
                                                        plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                                    }
                                                } else {
                                                    plugin.PlayerLogger(p, plugin.config.Shoperrorcantsellhere, "Error");
                                                }
                                            } else {
                                                plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughxpconsumer, "Error");
                                            }
                                            } else {
                                                plugin.PlayerLogger(player, line[2] + " " + plugin.config.playerwasntonline, "Error");
                                            }
                                         
                                        } catch (Exception e){
                                            plugin.PlayerLogger(player, line[2] + " " + plugin.config.playerwasntonline, "Error");
                                        }
                                    } else {
                                            
                                    Player empfaenger = plugin.getmyOfflinePlayer(line, 1);
                                    if (empfaenger != null) {
                                        if (plugin.config.getPlayerConfig(empfaenger, p)) {
                                            if (plugin.getTOTALXP(p) >= Integer.parseInt(line[2])) {
                                                if (getPrice(s, p, false) > 0) {
                                                    double price = getPrice(s, p, false);
                                                    if ((plugin.Geldsystem.getBalance(empfaenger) - price) >= 0) {
                                                        plugin.Geldsystem.substract(price, empfaenger);
                                                        plugin.UpdateXP(empfaenger, (Integer.parseInt(s.getLine(2))), "Sign");
                                                        plugin.PlayerLogger(empfaenger, String.format(plugin.config.Shopsuccesssellerselled, s.getLine(2), p.getDisplayName(), split[1]), "");
                                                        plugin.Geldsystem.addmoney(price, p);
                                                        plugin.UpdateXP(p, -(Integer.parseInt(s.getLine(2))), "Sign");
                                                        empfaenger.saveData();
                                                        plugin.PlayerLogger(p, String.format(plugin.config.Shopsuccesssell, s.getLine(2), s.getLine(1), split[1]), "");
                                                    } else {
                                                        plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                                    }
                                                } else {
                                                    plugin.PlayerLogger(p, plugin.config.Shoperrorcantsellhere, "Error");
                                                }
                                            } else {
                                                plugin.PlayerLogger(p, plugin.config.Shoperrornotenoughxpconsumer, "Error");
                                            }
                                        }
                                    } else {
                                        plugin.PlayerLogger(p, line[1] + " " + plugin.config.playerwasntonline, "Error");
                                    }
                                }
                            }
                        }
                        }
                    } else {
                        plugin.blacklistLogger(p);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private double getPrice(Sign s, Player p, boolean buy) {
        split = s.getLine(3).split(":");
        double doubeline1 = 0;
        try {
            if (buy) {
                doubeline1 = Double.parseDouble(split[0]);
            } else {
                doubeline1 = Double.parseDouble(split[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doubeline1;
    }

    public boolean blockIsValid(String[] lines, String von, Player p) {
        boolean a = false;
        if (plugin.config.debug) {
            plugin.Logger("Checking if block is valid!", "Debug");
        }
        String[] temp = null;
        try {
            temp = lines[3].split(":");
            if (plugin.config.debug) {
                plugin.Logger("Line 3 is: " + lines[3], "Debug");
            }
        } catch (Exception e) {
            if (plugin.config.debug) {
                plugin.Logger("Contains no : ", "Debug");
            }
        }
        try {
            if (Tools.isFloat(temp[0]) && Tools.isFloat(temp[1])) {
                if (plugin.config.debug) {
                    plugin.Logger("Buy and sell amount are ints: " + temp[0] + " und " + temp[1], "Debug");
                }
                if (Float.parseFloat(temp[0]) > 0 || Float.parseFloat(temp[1]) > 0) {
                    if (plugin.config.debug) {
                        plugin.Logger("One of them is greater than 0: " + temp[0] + " und " + temp[1], "Debug");
                    }
                    if (!(Float.parseFloat(temp[0]) < 0) && !(Float.parseFloat(temp[1]) < 0)) {
                        if (plugin.config.debug) {
                            plugin.Logger("None of them is smaller than 0: " + temp[0] + " und " + temp[1], "Debug");
                        }
                        if (Tools.isInteger(lines[2])) {
                            if (Integer.parseInt(lines[2]) > 0) {
                                if (plugin.config.debug) {
                                    plugin.Logger("Line 2 is int", "Debug");
                                }
                                a = true;
                                if (plugin.config.debug) {
                                    plugin.Logger("block is valid!", "Debug");
                                }
                            }
                        }
                    } else {
                        if (plugin.config.debug) {
                            plugin.Logger("One of them is smaller than 0: " + temp[0] + " und " + temp[1], "Debug");
                        }
                    }
                } else {
                    if (plugin.config.debug) {
                        plugin.Logger("None of them is greater than 0: " + temp[0] + " und " + temp[1], "Debug");
                    }
                }
            } else {
                if (plugin.config.debug) {
                    plugin.Logger("!Tools.isFloat(temp[0]) || !Tools.isFloat(temp[1])", "Debug");
                }
            }
        } catch (Exception ew) {
        }

        return a;
    }
}