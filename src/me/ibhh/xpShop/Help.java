/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ibhh.xpShop;

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
        Permission = new PermissionsHandler(pl);
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
            xpShop.PlayerLogger(player, "/xpShop infoxp <player>","");
            xpShop.PlayerLogger(player, "/xpShop infolevel <player>","");
            xpShop.PlayerLogger(player, "/xpShop info <action> <amount>","");
            xpShop.PlayerLogger(player, "/xpShop sell <amount>","");
            xpShop.PlayerLogger(player, "/xpShop buy <money>","");
            xpShop.PlayerLogger(player, "/xpShop buylevel <amount>","");
            xpShop.PlayerLogger(player, "/xpShop selllevel <amount>","");
            xpShop.PlayerLogger(player, "/xpShop send <player> <amount>","");
        } else if (args.length == 2) {
            if (!(args[1].equals("buy") || args[1].equals("sell") || args[1].equals("selllevel") || args[1].equals("buylevel") || args[1].equals("send") || args[1].equals("info") || args[0].equals("infoxp") || args[0].equals("infolevel"))) {
                xpShop.PlayerLogger(player, "Cant find command!", "Error");
            } else if (args[1].equals("buy")) {
                if (!Permission.checkpermissions(player, "xpShop.buy")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop buy <xp>","");
                xpShop.PlayerLogger(player, plugin.config.helpbuy,"");
            } else if (args[1].equals("buylevel")) {
                if (!Permission.checkpermissions(player, "xpShop.buylevel")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop buylevel <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpbuylevel,"");
            } else if (args[1].equals("sell")) {
                if (!Permission.checkpermissions(player, "xpShop.sell")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop sell <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpsell,"");
            } else if (args[1].equals("selllevel")) {
                if (!Permission.checkpermissions(player, "xpShop.selllevel")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop selllevel <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpsell,"");
            } else if (args[1].equals("info")) {
                if (!Permission.checkpermissions(player, "xpShop.info")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop info <action> <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfo,"");
            } else if (args[1].equals("send")) {
                if (!Permission.checkpermissions(player, "xpShop.send")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop send <player> <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpsend,"");
            } else if (args[1].equals("infoxp")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop infoxp <player>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfoxp,"");
            } else if (args[1].equals("infolevel")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop infolevel <player>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfolevel,"");
            }
        } else {
            if (!(args[0].equals("buy") || args[0].equals("sell") || args[0].equals("selllevel") || args[0].equals("buylevel") || args[0].equals("send") || args[0].equals("info") || args[0].equals("infoxp") || args[0].equals("infolevel"))) {
                xpShop.PlayerLogger(player, "/xpShop infoxp <player>","");
                xpShop.PlayerLogger(player, "/xpShop infolevel <player>","");
                xpShop.PlayerLogger(player, "/xpShop info <action> <amount>","");
                xpShop.PlayerLogger(player, "/xpShop sell <amount>","");
                xpShop.PlayerLogger(player, "/xpShop buy <money>","");
                xpShop.PlayerLogger(player, "/xpShop buylevel <amount>","");
                xpShop.PlayerLogger(player, "/xpShop selllevel <amount>","");
                xpShop.PlayerLogger(player, "/xpShop send <player> <amount>","");
            } else if (args[0].equals("buy")) {
                if (!Permission.checkpermissions(player, "xpShop.buy")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop buy <money>","");
                xpShop.PlayerLogger(player, plugin.config.helpbuy,"");
            } else if (args[0].equals("buylevel")) {
                if (!Permission.checkpermissions(player, "xpShop.buylevel")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop buy <money>","");
                xpShop.PlayerLogger(player, plugin.config.helpbuylevel,"");
            } else if (args[0].equals("sell")) {
                if (!Permission.checkpermissions(player, "xpShop.sell")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop sell <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpsell,"");
            } else if (args[0].equals("selllevel")) {
                if (!Permission.checkpermissions(player, "xpShop.selllevel")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop selllevel <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpselllevel,"");
            } else if (args[0].equals("info")) {
                if (!Permission.checkpermissions(player, "xpShop.info")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop info <action> <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfo,"");
            } else if (args[0].equals("send")) {
                if (!Permission.checkpermissions(player, "xpShop.send")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop send <player> <amount>","");
                xpShop.PlayerLogger(player, plugin.config.helpsend,"");
            } else if (args[0].equals("infoxp")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop infoxp <player>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfoxp,"");
            } else if (args[0].equals("infolevel")) {
                if (!Permission.checkpermissions(player, "xpShop.infoxp")) {
                    xpShop.PlayerLogger(player, plugin.config.permissionserror, "Error");
                }
                xpShop.PlayerLogger(player, "/xpShop infolevel <player>","");
                xpShop.PlayerLogger(player, plugin.config.helpinfolevel,"");
            }
        }
    }
}
