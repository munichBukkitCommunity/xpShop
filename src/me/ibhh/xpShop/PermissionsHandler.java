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

  public boolean checkpermissions(Player sender, String action)
  {
    if ((sender instanceof Player))
    {
      Player player = sender;
      if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
        try
        {
          if (player.hasPermission(action))
          {
            return true;
          }

          xpShop.PlayerLogger(player, player.getDisplayName() + " " + plugin.getConfig().getString("permissions.error." + plugin.getConfig().getString("language")), "");
          return false;
        }
        catch (Exception e)
        {
          System.out.println("[xpShop] Error on checking permissions with BukkitPermissions!");

          player.sendMessage("[xpShop] Error on checking permissions with BukkitPermissions!");

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

          xpShop.PlayerLogger(player, player.getDisplayName() + " " + plugin.getConfig().getString("permissions.error." + plugin.getConfig().getString("language")), "");
          return false;
        }
        catch (Exception e)
        {
          System.out.println("[xpShop] Error on checking permissions with PermissionsEx!");
          player.sendMessage("[xpShop] Error on checking permissions with PermissionsEx!");
          e.printStackTrace();
          return false;
        }

      }

      System.out.println("PermissionsEx plugin are not found.");
      return false;
    }

    xpShop.Logger(plugin.getConfig().getString("command.error.noplayer" + plugin.getConfig().getString("language")), "Error");
      
    return false;
  }
}