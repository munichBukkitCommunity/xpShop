package me.ibhh.xpShop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsHandler
{
  private xpShop plugin;

  public PermissionsHandler(xpShop pl)
  {
    this.plugin = pl;
  }

  public boolean checkpermissions(Player player, String action)
  {
      if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
        try
        {
          if (player.hasPermission(action))
          {
            return true;
          }

          xpShop.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
          return false;
        }
        catch (Exception e)
        {
          xpShop.Logger("Error on checking permissions with BukkitPermissions!", "Error");
          xpShop.PlayerLogger(player, "Error on checking permissions with BukkitPermissions!", "Error");
          e.printStackTrace();
          return false;
        }

      }

      if (Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
      {
        try
        {
          PermissionManager permissions = PermissionsEx.getPermissionManager();

          if (permissions.has(player, action))
          {
            return true;
          }
          xpShop.PlayerLogger(player, player.getDisplayName() + " " + plugin.config.permissionserror + "(" + action + ")", "Error");
          return false;
        }
        catch (Exception e)
        {
          xpShop.Logger("Error on checking permissions with BukkitPermissions!", "Error");
          xpShop.PlayerLogger(player, "Error on checking permissions with BukkitPermissions!", "Error");
          e.printStackTrace();
          return false;
        }

      }
      System.out.println("PermissionsEx plugin are not found.");
      return false;
  }
}