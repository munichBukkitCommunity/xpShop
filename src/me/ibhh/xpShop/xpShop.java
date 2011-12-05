package me.ibhh.xpShop;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijikokun.register.payment.Methods;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import com.iConomy.*;

public class xpShop extends JavaPlugin {


	public String ActionxpShop;
	public double money = 0;
	public double addmoney;
	public double getmoney;
	public int xp = 0;
	int SubstractedXP;
	public iConomy iConomy = null;

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
			if (cmd.getName().equalsIgnoreCase("xpShop")) 
			{
				ActionxpShop = args[0];
				Player player = (Player) sender;
				if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
				{
					PermissionManager permissions = PermissionsEx.getPermissionManager();
					// Permission check
					if(permissions.has(player, "xpShop.buy") || permissions.has(player, "xpShop.sell"))
					{		
						if (args.length == 2) 
						{	
							if (args[0].equals("buy") ||  args[0].equals("sell"))
							{
								if (args[0].equals("buy"))
								{

									if(permissions.has(player, "xpShop.buy"))
									{
										buy(sender, args);
									}
									else
									{
										player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
										return false;
									}
								}
								if (args[0].equals("sell"))
								{

									if(permissions.has(player, "xpShop.sell"))
									{
										sell(sender, args);
									}
									else
									{
										player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
										return false;
									}
								}
							}
						}	//if (ActionxpShop == "buy" ||  ActionxpShop == "sell")
						else	
						{
							player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noargs0." + getConfig().getString("language"))));
							return false;
						}
					}	//if (args.length == 2)
					else 
					{
						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.toomanyarguments." + getConfig().getString("language"))));
						return false;
					}
				}	//if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
				else 
				{		//kein PermissionsEx
					Logger.getLogger("Minecraft").warning((getConfig().getString("permissions.notfound." + getConfig().getString("language"))));
					return false;
				}	
			}	//if (cmd.getName().equalsIgnoreCase("xpShop"))
		}	//if (sender instanceof Player) 
		return true;
	}	//public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)

	public int SubstractedXP()
	{
		SubstractedXP = SubstractedXP + 1;
		return SubstractedXP;
	}

	public boolean buy(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;

		if(Bukkit.getServer().getPluginManager().isPluginEnabled("iConomy"))
		{
			try {
				money = Integer.parseInt (args[1]);
			} catch (Exception E){
				E.printStackTrace();
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.noint." + getConfig().getString("language"))));
				return false;
			}
			double TOTALXPDOUBLE = (money * (getConfig().getDouble("moneytoxp")));						
			int TOTALXP = (int) TOTALXPDOUBLE; 
			String playerAccount = player.getName();
			Double Balance = Methods.getMethod().getAccount(player.getName()).balance();
			if (Balance >= money)
			{
				try
				{
					player.giveExp(TOTALXP);
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + money + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + TOTALXP + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
				}
				catch (NumberFormatException ex) 
				{
					player.sendMessage("Invalid exp count: " + args[1]);
				}
				player.saveData();
				Double BalanceEnd = money;
				Methods.getMethod().getAccount(playerAccount).subtract(BalanceEnd);
			}	//if (Balance >= money)
			else
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughmoney." + getConfig().getString("language"))));
			}
		}
		else
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("iConomy5.error." + getConfig().getString("language"))));
		}
		return false;
	}

	public boolean sell(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;

		if(Bukkit.getServer().getPluginManager().isPluginEnabled("iConomy"))
		{
			try {
				xp = Integer.parseInt (args[1]);
			} catch (Exception E){
				E.printStackTrace();
				return false;
			}
			if (xp == 0)
			{
				return false;
			}
			String playerAccount = player.getName();
			if(player.getLevel() <= 0 && player.getExp() <= 0.01)
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
			}
			else
			{
				try
				{	
					if(SubstractedXP != 0)
					{
						SubstractedXP = 0;
					}
					getmoney = (getConfig().getDouble("xptomoney"));
					while(SubstractedXP < xp || (player.getLevel() == 0 && player.getExp() <= 2))
					{	
						if(player.getExp() <= 0)
						{
							try
							{
								int level = player.getLevel();
								level = level - 1;
								player.setLevel(level);
								player.setExp( (float) 0.999999);
								Double Balanceadd = getmoney;
								Methods.getMethod().getAccount(playerAccount).add(Balanceadd);
							}
							catch (Exception E)
							{
								E.printStackTrace();
								player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.else." + getConfig().getString("language"))));
								return false;
							}
						}
						else
						{	
							SubstractedXP();
							player.giveExp(-1);
							Double Balanceadd = getmoney;
							Methods.getMethod().getAccount(playerAccount).add(Balanceadd);
						}
					}	//while(SubstractedXP > TOTALXP)
				}
				catch (NumberFormatException ex) 
				{
					player.sendMessage("Invalid exp count: " + args[1]);
					return false;
				}
				player.saveData();
			}
			addmoney = SubstractedXP * (getConfig().getDouble("xptomoney"));
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + xp + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + addmoney + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
		}
		return false;
	}
}



