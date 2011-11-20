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
	private FileConfiguration config;
	
	@Override
	public void onDisable() {
		
		System.out.println("[xpShop] disabled!");
		
	}
	
    public void loadConfig(){
        config.addDefault("language", "de");
        config.addDefault("command.error.toomanyarguments.de", "Dieser Befehl darf nur eine Zahl enthalten!");
        config.addDefault("command.error.toomanyarguments.en", "This command has only one number!");
        config.addDefault("permission.error.de", "Houston, Wir haben ein Problem :) Diesen Befehl darfst Du nicht verwenden!");
        config.addDefault("permission.error.en", "Houston, we have a problem! You can't use this command!");
        config.addDefault("permission.notfound.de", "PermissionsEx plugin nicht gefunden!");
        config.addDefault("permission.notfound.en", "PermissionsEx not found!");
        config.options().copyDefaults(true);
        saveConfig();
    }


	@Override
	public void onEnable() {

		 //getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
	     //getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);
		
		
		
		try {
				config = getConfig();
				loadConfig();
				/*
				File configfile = new File("plugins" + File.separator + "xpShop" + File.separator + "config.yml");
				configfile.mkdir();
				if(!config.contains("language"))
				{config.set("language", "de");}
				if(!config.contains("command.error.toomanyarguments.de"))
				{config.set("command.error.toomanyarguments.de", "Dieser Befehl darf nur eine Zahl enthalten!");}
				if(!config.contains("command.error.toomanyarguments.en"))
				{config.set("command.error.toomanyarguments.en", "This command has only one number!");}
				if(!config.contains("permission.error.de"))
				{config.set("permission.error.de", "Houston, Wir haben ein Problem :) Diesen Befehl darfst Du nicht verwenden!");}
				if(!config.contains("permission.error.en"))
				{config.set("permission.error.en", "Houston, we have a problem! You can't use this command!");}
				if(!config.contains("permission.notfound.de"))
				{config.set("permission.notfound.de", "PermissionsEx plugin nicht gefunden!");}
				if(!config.contains("permission.notfound.en"))
				{config.set("permission.notfound.en", "PermissionsEx not found!");}
				*/
	
				saveConfig();
				System.out.println("[xpShop] Config file found!");		
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		System.out.println("[xpShop] Config file found!");	
		System.out.println("[xpShop] successfully enabled!");
		}
	
	
	public void onReload() {
		onEnable();
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

  }

