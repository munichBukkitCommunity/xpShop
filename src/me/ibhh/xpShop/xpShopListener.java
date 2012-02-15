package me.ibhh.xpShop;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class xpShopListener implements Listener {

    private final xpShop plugin;
    private PermissionsHandler Permissions;
    private String[] split;

    public xpShopListener(xpShop plugin) {
        this.plugin = plugin;
        this.Permissions = new PermissionsHandler(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void aendern(SignChangeEvent event) {
        Player p = event.getPlayer();
        String[] line = event.getLines();
        if (plugin.config.debug) {
            xpShop.Logger("First Line " + line[0], "Debug");
        }
        if (event.getLine(0).equalsIgnoreCase("[xpShop]")) {
            if (plugin.config.debug) {
                xpShop.Logger("First Line [xpShop]", "Debug");
            }
            try {
                if (blockIsValid(line, "create", p)) {
                    if (plugin.config.debug) {
                        xpShop.Logger("Sign is valid", "Debug");
                    }
                    if (!line[1].equalsIgnoreCase("AdminShop")) {
                        if (plugin.Permission.checkpermissions(p, "xpShop.create.own")) {
                            if (plugin.config.debug) {
                                xpShop.Logger("First line != null", "Debug");
                            }
                            event.setLine(1, event.getPlayer().getDisplayName());
                            xpShop.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                        } else {
                            if (plugin.config.debug) {
                                xpShop.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create", "Debug");
                            }
                            xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                            event.setCancelled(true);
                        }
                    } else if (line[1].equalsIgnoreCase(p.getDisplayName())) {
                        if (plugin.Permission.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                            event.setLine(0, "[xpShop]");
                        } else {
                            if (plugin.config.debug) {
                                xpShop.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create", "Debug");
                            }
                            xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                            event.setCancelled(true);
                        }
                    } else if (line[1].equalsIgnoreCase("AdminShop")) {
                        if (plugin.Permission.checkpermissions(p, "xpShop.create.admin")) {
                            if (plugin.config.debug) {
                                xpShop.Logger("Player " + p.getDisplayName() + " has permission: xpShop.create.admin", "Debug");
                            }
                            xpShop.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
                        } else {
                            if (plugin.config.debug) {
                                xpShop.Logger("Player " + p.getDisplayName() + " has no permission: xpShop.create.admin", "Debug");
                            }
                            xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                            event.setCancelled(true);
                        }
                    }
                } else {
                    if (plugin.config.debug) {
                        xpShop.Logger("Sign is not valid", "Debug");
                    }
                    xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
                    event.setCancelled(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.setCancelled(true);
                xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
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
//                                    xpShop.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
//                                } else {
//                                    sign.setLine(1, event.getPlayer().getName());
//                                }
//                            } else {
//                                xpShop.PlayerLogger(event.getPlayer(), "Successfully created xpShop!", "");
//                                sign.setLine(0, "[xpShop]");
//                            }
//                        } else {
//                            xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
//                            event.setCancelled(true);
//                        }
//                    } else {
//                        event.setCancelled(true);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    event.setCancelled(true);
//                    xpShop.PlayerLogger(event.getPlayer(), "xpShop creation failed!", "Error");
//                }
//            }
//        }
//    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!(event.getBlock().getState() instanceof Sign)) {
            Block temp = event.getBlock().getRelative(BlockFace.EAST);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.WEST);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.NORTH);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.SOUTH);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            temp = event.getBlock().getRelative(BlockFace.UP);
            if (temp.getState() instanceof Sign) {
                Sign s1 = (Sign) temp.getState();
                if (s1.getLine(0).equalsIgnoreCase("[xpShop]")) {
                    String[] line = s1.getLines();
                    if (this.blockIsValid(line, "break", p)) {
                        if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            event.setCancelled(true);
                        } else if (line[1].equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                        } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                            xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }


        } else {
            Sign s = (Sign) event.getBlock().getState();
            String[] line = s.getLines();
            if (this.blockIsValid(line, "break", p)) {
                if (!this.Permissions.checkpermissions(p, "xpShop.create.own") && !this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                    event.setCancelled(true);
                } else if (s.getLine(1).equalsIgnoreCase(p.getName()) && this.Permissions.checkpermissions(p, "xpShop.create.own")) {
                    xpShop.PlayerLogger(p, "Destroying xpShop!", "");
                } else if (this.Permissions.checkpermissions(p, "xpShop.create.admin")) {
                    xpShop.PlayerLogger(p, "Destroying xpShop (Admin)!", "");
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (!p.isSneaking())) { // && !(p.isSneaking())
                Sign s = (Sign) event.getClickedBlock().getState();
                String[] line = s.getLines();
                if (this.blockIsValid(line, "Interact", p)) {
                    if (this.Permissions.checkpermissions(p, "xpShop.use")) {
                        if (line[1].equalsIgnoreCase("AdminShop")) {
                            double price = getPrice(s, p, true);
                            if (price > 0) {
                                if ((plugin.Geldsystem.getBalance156(p) - price) >= 0) {
                                    plugin.Geldsystem.substractmoney156(price, p);
                                    plugin.UpdateXP(p, (Integer.parseInt(s.getLine(2))), "Sign");
                                    xpShop.PlayerLogger(p, String.format(plugin.config.Shopsuccessbuy, s.getLine(2), "Admin", split[0]), "");
                                } else {
                                    xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                }
                            } else {
                                xpShop.PlayerLogger(p, plugin.config.Shoperrorcantbuyhere, "Error");
                            }
                        } else {
                            Player empfaenger = Bukkit.getServer().getPlayer(s.getLine(1));
                            if (empfaenger != null) {
                                if (plugin.getTOTALXP(empfaenger) >= Integer.parseInt(line[2])) {
                                    if (getPrice(s, p, true) > 0) {
                                        double price = getPrice(s, p, true);
                                        if ((plugin.Geldsystem.getBalance156(p) - price) >= 0) {
                                            plugin.Geldsystem.substractmoney156(price, p);
                                            plugin.UpdateXP(p, (Integer.parseInt(s.getLine(2))), "Sign");
                                            xpShop.PlayerLogger(p, String.format(plugin.config.Shopsuccessbuy, s.getLine(2), s.getLine(1), split[0]), "");
                                            plugin.UpdateXP(empfaenger, -(Integer.parseInt(s.getLine(2))), "Sign");
                                            plugin.Geldsystem.addmoney156(price, empfaenger);
                                            xpShop.PlayerLogger(empfaenger, String.format(plugin.config.Shopsuccesssellerbuy, s.getLine(2), p.getDisplayName(), split[0]), "");
                                        } else {
                                            xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                        }
                                    } else {
                                        xpShop.PlayerLogger(p, plugin.config.Shoperrorcantbuyhere, "Error");
                                    }
                                } else {
                                    xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughxpseller, "Error");
                                }
                            } else {
                                xpShop.PlayerLogger(p, line[1] + " " + plugin.config.playernotonline, "Error");
                            }
                        }
                    }
                }
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((event.hasBlock()) && ((event.getClickedBlock().getState() instanceof Sign)) && (!p.isSneaking())) { // && !(p.isSneaking())
                Sign s = (Sign) event.getClickedBlock().getState();
                String[] line = s.getLines();
                if (this.blockIsValid(line, "Interact", p)) {
                    if (this.Permissions.checkpermissions(p, "xpShop.use")) {
                        if (line[1].equalsIgnoreCase("AdminShop")) {
                            if (plugin.getTOTALXP(p) >= Integer.parseInt(line[2])) {
                                if (getPrice(s, p, false) > 0) {
                                    double price = getPrice(s, p, false);
                                    plugin.Geldsystem.addmoney156(price, p);
                                    plugin.UpdateXP(p, -(Integer.parseInt(s.getLine(2))), "Sign");
                                    xpShop.PlayerLogger(p, String.format(plugin.config.Shopsuccesssell, s.getLine(2), "Admin", split[1]), "");
                                } else {
                                    xpShop.PlayerLogger(p, plugin.config.Shoperrorcantsellhere, "Error");
                                }
                            } else {
                                xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughxpconsumer, "Error");
                            }
                        } else {
                            Player empfaenger = Bukkit.getServer().getPlayer(line[1]);
                            if (empfaenger != null) {
                                if (plugin.getTOTALXP(p) >= Integer.parseInt(line[2])) {
                                    if (getPrice(s, p, false) > 0) {
                                        double price = getPrice(s, p, false);
                                        if ((plugin.Geldsystem.getBalance156(empfaenger) - price) >= 0) {
                                            plugin.Geldsystem.substractmoney156(price, empfaenger);
                                            plugin.UpdateXP(empfaenger, (Integer.parseInt(s.getLine(2))), "Sign");
                                            xpShop.PlayerLogger(empfaenger, String.format(plugin.config.Shopsuccesssellerselled, s.getLine(2), p.getDisplayName(), split[1]), "");
                                            plugin.Geldsystem.addmoney156(price, p);
                                            plugin.UpdateXP(p, -(Integer.parseInt(s.getLine(2))), "Sign");
                                            xpShop.PlayerLogger(p, String.format(plugin.config.Shopsuccesssell, s.getLine(2), s.getLine(1), split[1]), "");
                                        } else {
                                            xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughmoneyconsumer, "Error");
                                        }
                                    } else {
                                        xpShop.PlayerLogger(p, plugin.config.Shoperrorcantsellhere, "Error");
                                    }
                                } else {
                                    xpShop.PlayerLogger(p, plugin.config.Shoperrornotenoughxpconsumer, "Error");
                                }
                            } else {
                                xpShop.PlayerLogger(p, line[1] + " " + plugin.config.playernotonline, "Error");
                            }
                        }
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
            xpShop.Logger("Checking if block is valid!", "Debug");
        }
        String[] temp = null;
        try {
            temp = lines[3].split(":");
            if (plugin.config.debug) {
                xpShop.Logger("Line 3 is: " + lines[3], "Debug");
            }
        } catch (Exception e) {
            if (plugin.config.debug) {
                xpShop.Logger("Contains no : ", "Debug");
            }
        }
        if (Tools.isFloat(temp[0]) && Tools.isFloat(temp[1])) {
            if (plugin.config.debug) {
                xpShop.Logger("Buy and sell amount are ints: " + temp[0] + " und " + temp[1], "Debug");
            }
            if (Float.parseFloat(temp[0]) > 0 || Float.parseFloat(temp[1]) > 0) {
                if (plugin.config.debug) {
                    xpShop.Logger("One of them is greater than 0: " + temp[0] + " und " + temp[1], "Debug");
                }
                if (!(Float.parseFloat(temp[0]) < 0) && !(Float.parseFloat(temp[1]) < 0)) {
                    if (plugin.config.debug) {
                        xpShop.Logger("None of them is smaller than 0: " + temp[0] + " und " + temp[1], "Debug");
                    }
                    if (Tools.isInteger(lines[2])) {
                        if (plugin.config.debug) {
                            xpShop.Logger("Line 2 is int", "Debug");
                        }
                        a = true;
                        if (plugin.config.debug) {
                            xpShop.Logger("block is valid!", "Debug");
                        }
                    }
                } else {
                    if (plugin.config.debug) {
                        xpShop.Logger("One of them is smaller than 0: " + temp[0] + " und " + temp[1], "Debug");
                    }
                }
            } else {
                if (plugin.config.debug) {
                    xpShop.Logger("None of them is greater than 0: " + temp[0] + " und " + temp[1], "Debug");
                }
            }
        } else {
            if (plugin.config.debug) {
                xpShop.Logger("!Tools.isFloat(temp[0]) || !Tools.isFloat(temp[1])", "Debug");
            }
        }

        return a;
    }
}