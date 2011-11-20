package me.ibhh.xpShop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import com.iCo6.*;

public class xpShop extends JavaPlugin {

	
	public iConomy iConomy = null;
	private FileConfiguration messagesconfig = null;
	private File messagesConfigurationFile = new File(getDataFolder(), "config.yml");
	private FileConfiguration config;
	private FileConfiguration messagesConfig;
	
	@Override
	public void onDisable() {
		
		System.out.println("[xpShop] disabled!");
		
	}

	@Override
	public void onEnable() {

		loadConfiguration();
		loadYamlConfiguration();
		 //getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
	     //getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);

	     
		try {
			

				
				System.out.println("[xpShop] Config file not found!");
				
				loadYamlConfiguration();
				
				
				System.out.println("[xpShop] could not be enabled: Config file was missing!");
				return;
			
		}
		catch (Exception e)
		{
			System.out.println("[xpShop] could not be enabled: Exception occured while seaching config file.");
			e.printStackTrace();
			return;
		} 
		
		String xpcost = this.getConfig().getString("dbPath", "1");
		String xpgetcost = this.getConfig().getString("dbUser", "1");
		
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
			    			String name = Bukkit.getName();

			    				
			  
			    		}
				
			    		else {
			    			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "Dieser Befehl darf nur eine Zahl enthalten!");
			    			succeed = false;
			    		}
			    	}
			    } else {
			    	player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "Houston, Wir haben ein Problem :) Diesen Befehl darfst Du nicht verwenden!");
			    	succeed = false;
			    	return false;

			    }
			} else {		//kein PermissionsEx
			   Logger.getLogger("Minecraft").warning("PermissionsEx plugin nicht gefunden!");
			}
			
			
			
		}
		
		succeed = true;
		return succeed;
		
	}
	private void createconfig() {
		
		try {
			this.getConfig().set("xpcost", "1");
			this.getConfig().set("xpgetcost", "1");
			this.saveConfig();
			System.out.println("[xpShop] Config file created!");
		}
		catch (Exception e) {
			
			System.out.println("[xpShop] Config file could not be created: Exception occured while creating config file.");
			e.printStackTrace();
		}
	}
	
	private void loadConfiguration() {
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		}
		public FileConfiguration getMessagesConfig() {
		if (messagesconfig == null) {
		reloadMessagesConfig();
		}
		return messagesconfig;
		}
		public void reloadMessagesConfig() {

		    YamlConfiguration messagesconfig = new YamlConfiguration();

		    try {

		        messagesconfig.load(messagesConfigurationFile);

		    } catch (IOException ex) {
		    	System.out.println("[SimpleItemPermissions] messages config failed to load");
		        return;
		    } catch (InvalidConfigurationException ex) {
		    	System.out.println("[SimpleItemPermissions] messages config failed to load");
		        return;
		    }

		    //Do stuff here with config

		}
		
		public void saveMessagesConfig() {
		try {
		messagesconfig.save(messagesConfigurationFile);
		} catch (IOException ex) {
		Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + messagesConfigurationFile, ex);
		}
		}
		 
		 
		private void loadYamlConfiguration() {
			final YamlConfiguration pluginconfig = new YamlConfiguration();

			try {

			    pluginconfig.load("plugins/xpShop/config.yml");
			    return;

			} catch (IOException ex) {

				System.out.println("[xpShop] Couldn't load config: " + ex.getMessage());
				createconfig();
			    return;

			} catch (InvalidConfigurationException ex) {

				System.out.println("[xpShop] Couldn't load config: " + ex.getMessage());
			    return;

			}
		

  }
}
