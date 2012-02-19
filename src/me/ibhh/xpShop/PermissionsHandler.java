package me.ibhh.xpShop;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsHandler {

    private xpShop plugin;
    private GroupManager groupManager;

    public PermissionsHandler(xpShop pl) {
        this.plugin = pl;
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        final Plugin GMplugin = pluginManager.getPlugin("GroupManager");
            if (GMplugin != null && GMplugin.isEnabled()) {
                groupManager = (GroupManager) GMplugin;

            }
    }

    public boolean checkpermissions(Player player, String action) {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx") && !plugin.getServer().getPluginManager().isPluginEnabled("GroupManager")) {
            try {
                if (player.hasPermission(action)) {
                    return true;
                }

                plugin.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
                return false;
            } catch (Exception e) {
                plugin.Logger("Error on checking permissions with BukkitPermissions!", "Error");
                plugin.PlayerLogger(player, "Error on checking permissions with BukkitPermissions!", "Error");
                e.printStackTrace();
                return false;
            }

        }

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            try {
                PermissionManager permissions = PermissionsEx.getPermissionManager();

                if (permissions.has(player, action)) {
                    return true;
                }
                plugin.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
                return false;
            } catch (Exception e) {
                plugin.Logger("Error on checking permissions with BukkitPermissions!", "Error");
                plugin.PlayerLogger(player, "Error on checking permissions with BukkitPermissions!", "Error");
                e.printStackTrace();
                return false;
            }

        } else if (plugin.getServer().getPluginManager().isPluginEnabled("GroupManager")) {
            try {
                final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
                {
                    if (handler == null) {
                        if (handler.has(player, action)) {
                            return true;
                        } else {
                            plugin.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                plugin.Logger("Error on checking permissions with BukkitPermissions!", "Error");
                plugin.PlayerLogger(player, "Error on checking permissions with BukkitPermissions!", "Error");
                e.printStackTrace();
                return false;
            }
        }
        System.out.println("PermissionsEx plugin are not found.");
        return false;
    }
}