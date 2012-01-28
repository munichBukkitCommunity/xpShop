package me.ibhh.xpShop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsHandler
{
  private xpShop AnimalShopV;
  String Error;

  public PermissionsHandler(xpShop AnimalSh)
  {
    this.AnimalShopV = AnimalSh;
  }

  public boolean checkpermissions(Player sender, String action)
  {
    if ((sender instanceof Player))
    {
      Player player = sender;
      try {
        this.Error = (ChatColor.DARK_BLUE + "[AnimalShop]" + ChatColor.GOLD + "Houston we have a problem! You can't use this command!");
      }
      catch (Exception e) {
        e.printStackTrace();
        sender.sendMessage("[AnimalShop] Error on checking permissions (Config)!");
      }
      if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
        try
        {
          if (player.hasPermission(action))
          {
            return true;
          }

          player.sendMessage(this.Error);
          return false;
        }
        catch (Exception e)
        {
          System.out.println("[AnimalShop] Error on checking permissions with BukkitPermissions!");

          player.sendMessage("[AnimalShop] Error on checking permissions with BukkitPermissions!");

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

          player.sendMessage(this.Error);
          return false;
        }
        catch (Exception e)
        {
          System.out.println("[AnimalShop] Error on checking permissions with PermissionsEx!");
          player.sendMessage("[AnimalShop] Error on checking permissions with PermissionsEx!");
          e.printStackTrace();
          return false;
        }

      }

      System.out
        .println("PermissionsEx plugin are not found.");
      return false;
    }

    System.out.println("[AnimalShop] " + this.AnimalShopV.getConfig().getString(new StringBuilder("command.error.noplayer").append(this.AnimalShopV.getConfig().getString("language")).toString()));
    return false;
  }
}