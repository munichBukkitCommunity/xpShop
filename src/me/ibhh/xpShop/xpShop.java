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
	public String ActionxpShop = "";
	public int XPint;
	public int money = 0;
	public int xp = 0;
	
	@Override
	public void onDisable() {
		
		System.out.println("[xpShop] disabled!");
		
	}
	




	@Override
	public void onEnable() 
	{

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

	
	public void onReload() 
	{
		onEnable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (sender instanceof Player) 
		{
	    	int TOTALXP = 0;
			if (cmd.getName().equalsIgnoreCase("xpShop")) 
	    	{
			Player player = (Player) sender;
			player.sendMessage(label+ ";" + args[0] + ";" + args[1]);
			if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
			{
				PermissionManager permissions = PermissionsEx.getPermissionManager();
				// Permission check
				if(permissions.has(player, "xpShop.buy") || permissions.has(player, "xpShop.sell"))
				{		
					try
					{
						ActionxpShop = args[0];	//.toLowerCase();
					} catch (Exception e) 
					{
						e.printStackTrace();
						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.else." + getConfig().getString("language"))));
					}
					if (args.length == 2) 
					{	
						player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + ActionxpShop);
						if(permissions.has(player, "xpShop." + ActionxpShop))
						{
							player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + ActionxpShop);
							if (ActionxpShop.equals("buy") ||  ActionxpShop.equals("sell"))
							{
								try
								{
									XPint = player.getExperience();
									player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";XPint" + XPint);
								}
								catch (Exception e3)
								{
									e3.printStackTrace();
									player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.else." + getConfig().getString("language"))));

								}
								try {
									if (ActionxpShop.equals("buy"))
									{
										try {
												money = Integer.parseInt (args[1]);
												player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + args[1] + ";Money:" + money);
										} catch (Exception E){
											E.printStackTrace();
											player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noint." + getConfig().getString("language"))));
											return false;
										}
										TOTALXP = XPint + (money * (getConfig().getInt("moneytoxp")));
										player.sendMessage(label+ ";" + XPint + ";" + args[1] + ";" + ActionxpShop + ";" + money);
										player.setExperience(TOTALXP);
										player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + ActionxpShop + ";" + money + ";" + TOTALXP);
										player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language"))));
									}
									else if (ActionxpShop.equals("sell"))
										{
											try {
													xp = Integer.parseInt (args[1]);
													player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + args[1] + ";Money:" + money);
											} catch (Exception E){
												E.printStackTrace();
												player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noint." + getConfig().getString("language"))));
												return false;
											}
											TOTALXP = XPint - (xp * (getConfig().getInt("xptomoney")));
											player.sendMessage(label+ ";" + XPint + ";" + args[1] + ";" + ActionxpShop + ";" + money);
											player.setExperience(TOTALXP);
											player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + ActionxpShop + ";" + money + ";" + TOTALXP);
											player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language"))));
									}	//if (ActionxpShop == "sell")
								} // try
								catch (Exception e1) 
								{
									e1.printStackTrace();
									player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.else." + getConfig().getString("language"))));
								}

							}	//if (ActionxpShop == "buy" ||  ActionxpShop == "sell")
							else	
							{
								player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noargs0." + getConfig().getString("language"))));
								return false;
							}
						}	//if(permissions.has(player, "xpShop." + ActionxpShop))
						else
						{
							player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
							return false;
						}
					}	//if (args.length == 2)
					else 
					{
						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.toomanyarguments." + getConfig().getString("language"))));
						return false;
					}
				}	//if(permissions.has(player, "xpShop.buy") || permissions.has(player, "xpShop.sell"))
				else 
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
					return false;
				}
			}	//if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
			else 
			{		//kein PermissionsEx
				Logger.getLogger("Minecraft").warning((getConfig().getString("permissions.notfound." + getConfig().getString("language"))));
				return false;
			}	
		player.sendMessage(label+ ";" + args[0] + ";" + args[1] + ";" + "fertig" + TOTALXP);
	    	}	//if (cmd.getName().equalsIgnoreCase("xpShop"))
		}	//if (sender instanceof Player) 
	return true;
	}	//public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
}



