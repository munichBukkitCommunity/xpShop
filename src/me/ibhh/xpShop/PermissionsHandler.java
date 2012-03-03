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
    private int PermPlugin = 0;

    public PermissionsHandler(xpShop pl) {
        this.plugin = pl;
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        final Plugin GMplugin = pluginManager.getPlugin("GroupManager");
        if (GMplugin != null && GMplugin.isEnabled()) {
            groupManager = (GroupManager) GMplugin;

        }
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    plugin.Logger("checking PermissionsPlugin!", "");
                    searchpermplugin();
                }
            }, 1L);
    }

    public void searchpermplugin() {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx") && !plugin.getServer().getPluginManager().isPluginEnabled("GroupManager")) {
            PermPlugin = 1;
            plugin.Logger("Permissions: Hooked into BukkitPermissions!", "");
            return;
        } else if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            PermPlugin = 2;
            plugin.Logger("Permissions: Hooked into PermissionsEX!", "");
            return;
        } else if (plugin.getServer().getPluginManager().isPluginEnabled("GroupManager")) {
            PermPlugin = 3;
            plugin.Logger("Permissions: Hooked into GroupManager!", "");
            return;
        } else {
            PermPlugin = 0;
            plugin.Logger("Permissions: cant find a compatible PermissionsPlugin!", "");
        }
    }

    public boolean checkpermissions(Player player, String action) {
        if (player.isOp()) {
            return true;
        }
        if (PermPlugin == 1) {
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

        } else if (PermPlugin == 2) {
            try {
                PermissionManager permissions = PermissionsEx.getPermissionManager();

                if (permissions.has(player, action)) {
                    return true;
                }
                plugin.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
                return false;
            } catch (Exception e) {
                plugin.Logger("Error on checking permissions with PermissionsEX!", "Error");
                plugin.PlayerLogger(player, "Error on checking permissions with PermissionsEX!", "Error");
                e.printStackTrace();
                return false;
            }

        } else if (PermPlugin == 3) {
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
                plugin.Logger("Error on checking permissions with GroupManager!", "Error");
                plugin.PlayerLogger(player, "Error on checking permissions with GroupManager!", "Error");
                e.printStackTrace();
                return false;
            }
        } else {
            plugin.PlayerLogger(player, plugin.config.permissionsnotfound, "Error");
            searchpermplugin();
            System.out.println("PermissionsEx plugin are not found.");
            return false;
        }
        return false;
    }
}