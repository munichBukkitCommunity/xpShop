package me.ibhh.xpShop;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import com.iCo6.*;

public class xpShop extends JavaPlugin {

	
	public iConomy iConomy = null;
	public String ActionxpShop = null;
	
	@Override
	public void onDisable() {
		
		System.out.println("[xpShop] disabled!");
		
	}
	




	@Override
	public void onEnable() {

		 //getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
	     //getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);
		
		
		
		try
		{

				this.getConfig().options().copyDefaults(true);
				saveConfig();
				reloadConfig();
				System.out.println("[xpShop] Config file found!");		
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 	
		System.out.println("[xpShop] successfully enabled!");
		}
	
	
	public void onReload() {
		onEnable();
	}
		

	
		
	

	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			    PermissionManager permissions = PermissionsEx.getPermissionManager();

			    // Permission check
			    if(permissions.has(player, "xpShop.buy") || permissions.has(player, "xpShop.sell")){
			    	
			    	if (cmd.getName().equalsIgnoreCase("xpShop")) {
			    		try
			    	    {
			    	      ActionxpShop = args[0].toLowerCase();
			    	    } catch (Exception e) {
			    	      ActionxpShop = null;
			    	    }

			    	    }
			    		if (args.length == 2) {
			    			if (ActionxpShop.equalsIgnoreCase("buy") || ActionxpShop.equalsIgnoreCase("sell"))
			    			{	
			    				if(permissions.has(player, "xpShop." + ActionxpShop)){
			    				if (ActionxpShop == "buy" && ActionxpShop != null && ActionxpShop == "sell")
			    				{
			    					int XPint = player.getExperience();
			    					int money = 0;
			    					int xp = 0;
			    					try {
			    						if (ActionxpShop == "buy"){
					    					try {
					    						if (Tools.isInteger(args[1]) && args[1] != null)
					    						{
					    						money = Integer.parseInt (args[1]);
					    						}
					    						
					    					} catch (Exception E){
					    						E.printStackTrace();
					    						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noint." + getConfig().getString("language"))));
					    						return false;
					    					}
			    						int TOTALXP = XPint + (money * getConfig().getInt("buy"));
			    						player.setExperience(TOTALXP);
			    						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language"))));
			    						}
			    						else
			    						{
			    							if (ActionxpShop == "sell"){
						    					try {
						    						if (Tools.isInteger(args[1]) && args[1] != null)
						    						{
						    						xp = Integer.parseInt (args[1]);
						    						}
						    						
						    					} catch (Exception E){
						    						E.printStackTrace();
						    						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noint." + getConfig().getString("language"))));
						    						return false;
						    					}
			    								int TOTALXP = XPint - (xp * getConfig().getInt("sell"));
					    						player.setExperience(TOTALXP);
					    						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language"))));
			    							}
			    						}
			    					} catch (Exception e1) {
			    						e1.printStackTrace();
			    						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.else." + getConfig().getString("language"))));
			    					}
			    					}	
			    			}
			    			else
			    			{
			    				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noargs0." + getConfig().getString("language"))));
			    				return false;
			    			}
			    		}
			    		else 
			    		{
			    			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.toomanyarguments." + getConfig().getString("language"))));
			    			return false;
			    		}
			    	}
			    } else {
			    	player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
			    	return false;

			    }
			} else {		//kein PermissionsEx
			   Logger.getLogger("Minecraft").warning((getConfig().getString("permissions.notfound." + getConfig().getString("language"))));
			   return false;
			}	
		}
		return true;
	}
}



