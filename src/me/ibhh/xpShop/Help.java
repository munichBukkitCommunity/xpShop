/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ibhh.xpShop;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Simon
 */
public class Help {
    private xpShop plugin;
    private PermissionsHandler Permission;
    public Help(xpShop pl){
        plugin = pl;
    }
    
    /**
     * Returns help.
     *
     * @param sender, action(String[])
     * @return
     */
    public void help(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0 || args.length >= 3) {
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infoxp <player>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infolevel <player>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop info <action> <amount>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buy <money>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buylevel <amount>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop selllevel <amount>");
            player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop send <player> <amount>");
        } else if (args.length == 2) {
            if (!(args[1].equals("buy") || args[1].equals("sell") || args[1].equals("selllevel") || args[1].equals("buylevel") || args[1].equals("send") || args[1].equals("info") || args[0].equals("infoxp") || args[0].equals("infolevel"))) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "Cant find command!");
            } else if (args[1].equals("buy")) {
                if (!Permission.checkpermissions(player, "xpShop.buy")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + (plugin.getConfig().getString("language"))));

                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buy <xp>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.buy" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("buylevel")) {
                if (!Permission.checkpermissions(player, "xpShop.buylevel")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buylevel <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.buylevel" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("sell")) {
                if (!Permission.checkpermissions(player, "xpShop.sell")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.sell" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("selllevel")) {
                if (!Permission.checkpermissions(player, "xpShop.selllevel")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.selllevel" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("info")) {
                if (!Permission.checkpermissions(player, "xpShop.info")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop info <action> <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.info" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("send")) {
                if (!Permission.checkpermissions(player, "xpShop.send")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop send <player> <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.send" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("infoxp")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infoxp <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.infoxp" + "." + plugin.getConfig().getString("language")));
            } else if (args[1].equals("infolevel")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infolevel <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.infolevel" + "." + plugin.getConfig().getString("language")));
            }
        } else {
            if (!(args[0].equals("buy") || args[0].equals("sell") || args[0].equals("selllevel") || args[0].equals("buylevel") || args[0].equals("send") || args[0].equals("info") || args[0].equals("infoxp") || args[0].equals("infolevel"))) {
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infoxp <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infolevel <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop info <action> <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buy <money>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buylevel <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop selllevel <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop send <player> <amount>");
            } else if (args[0].equals("buy")) {
                if (!Permission.checkpermissions(player, "xpShop.buy")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buy <money>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.buy" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("buylevel")) {
                if (!Permission.checkpermissions(player, "xpShop.buylevel")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop buy <money>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.buylevel" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("sell")) {
                if (!Permission.checkpermissions(player, "xpShop.sell")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.sell" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("selllevel")) {
                if (!Permission.checkpermissions(player, "xpShop.selllevel")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop sell <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.selllevel" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("info")) {
                if (!Permission.checkpermissions(player, "xpShop.info")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop info <action> <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.info" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("send")) {
                if (!Permission.checkpermissions(player, "xpShop.send")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop send <player> <amount>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.send" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("infoxp")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infoxp <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.infoxp" + "." + plugin.getConfig().getString("language")));
            } else if (args[0].equals("infolevel")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("permissions.error" + "." + plugin.getConfig().getString("language")));
                }
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + "/xpShop infolevel <player>");
                player.sendMessage(ChatColor.GRAY + "[xpShop] " + ChatColor.RED + plugin.getConfig().getString("help.infolevel" + "." + plugin.getConfig().getString("language")));
            }
        }
    }
}
