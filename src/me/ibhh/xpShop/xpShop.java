package me.ibhh.xpShop;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import com.iCo6.*;

public class xpShop extends JavaPlugin {

	
	public iConomy iConomy = null;
	protected FileConfiguration config;
	
	@Override
	public void onDisable() {
		
		System.out.println("[xpShop] disabled!");
		
	}

	@Override
	public void onEnable() {

		 //getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
	     //getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);
		
		
		
		try {
				config = getConfig();
				System.out.println("[xpShop] Config file found!");		
		}
		catch (Exception e)
		{
			System.out.println("[xpShop] could not be enabled: Exception occured while seaching config file.");
			createconfig();
			e.printStackTrace();
		} 
		System.out.println("[xpShop] Config file found!");	
		System.out.println("[xpShop] successfully enabled!");
		


		

	}
		
	

	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		boolean succeed = false;
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			    PermissionManager permissions = PermissionsEx.getPermissionManager();

			    // Permission check
			    if(permissions.has(player, "xpShop.buy")){
			    	
			    	if (cmd.getName().equalsIgnoreCase("xpShopxp")) {
			    		if (args.length == 1) {

			    				
			  
			    		}
				
			    		else {
			    			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.toomanyarguments." + getConfig().getString("language"))));
			    			succeed = false;
			    		}
			    	}
			    } else {
			    	player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permission.error." + getConfig().getString("language"))));
			    	succeed = false;
			    	return false;

			    }
			} else {		//kein PermissionsEx
			   Logger.getLogger("Minecraft").warning((getConfig().getString("permission.notfound." + getConfig().getString("language"))));
			}
			
			
		}
		
		succeed = true;
		return succeed;
		
	}
	private void createconfig() {

			try {
				getConfig().addDefault("language", "de");
				getConfig().addDefault("command.error.toomanyarguments.de", "Dieser Befehl darf nur eine Zahl enthalten!");
				getConfig().addDefault("command.error.toomanyarguments.en", "This command has only one number!");
				getConfig().addDefault("permission.error.de", "Houston, Wir haben ein Problem :) Diesen Befehl darfst Du nicht verwenden!");
				getConfig().addDefault("permission.error.en", "Houston, we have a problem! You can't use this command!");
				getConfig().addDefault("permission.notfound.de", "PermissionsEx plugin nicht gefunden!");
				getConfig().addDefault("permission.notfound.en", "PermissionsEx not found!");
				//getConfig().options().copyDefaults(true);
				this.saveConfig();
				System.out.println("[xpShop] Config file created!");
				}
			catch (Exception e1)
				{
				System.out.println("[xpShop] Config file could not be created: Exception occured while creating config file.");
				return;
				}
			
	}


  }

