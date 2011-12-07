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
import com.iCo6.system.Accounts;
import com.iConomy.*;
import com.nijikokun.register.payment.Methods;

public class xpShop extends JavaPlugin {


	private String ActionxpShop;
	private double balance;
	private double money = 0;
	private double addmoney;
	private double getmoney;
	private int xp = 0;
	private int SubstractedXP;
	public iConomy iConomy = null;
	public int iConomyversion = 0;

	@Override
	public void onDisable() {

		System.out.println("[xpShop] disabled!");

	}





	@Override
	public void onEnable() 
	{

		//getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new server(this), Priority.Monitor, this);
		//getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new server(this), Priority.Monitor, this);


		iConomyversion();
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
					if(checkpermissions(sender, args[0]))
					{		
						if (args.length == 2) 
						{	
							if (args[0].equals("buy") ||  args[0].equals("sell"))
							{
								if (args[0].equals("buy"))
								{
									buy(sender, args);
								}
								if (args[0].equals("sell"))
								{
									sell(sender, args);
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
			if(getBalance56(player) >= money)
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
				substractmoney56(money, player);
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
			if(player.getLevel() + player.getExp() <= 0.20)
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
				return false;
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
					while(SubstractedXP < xp || player.getLevel() + player.getExp() >= 0.20)
					{	
						if(player.getExp() <= 0)
						{
							try
							{
								int level = player.getLevel();
								level = level - 1;
								player.setLevel(level);
								player.setExp( (float) 0.999999);
								addmoney56(getmoney, player);
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
							addmoney56(getmoney, player);
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
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + SubstractedXP + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + addmoney + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
		}
		else
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("iConomy5.error." + getConfig().getString("language"))));
		}
		return false;
	}
	public boolean checkpermissions(CommandSender sender, String action)
	{	
		if (sender instanceof Player) 
		{
			Player player = (Player) sender;
			PermissionManager permissions = PermissionsEx.getPermissionManager();
			// Permission check
			if(permissions.has(player, "xpShop." + action))
			{
				return true;
			}	//if(permissions.has(player, "xpShop." + action))
			else
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
				return false;
			}
		}	//if (sender instanceof Player)
		else
		{
			System.out.println("[xpShop]" + (getConfig().getString("command.error.noplayer" + getConfig().getString("language"))));
			return false;
		}
	}	//public boolean checkpermissions(CommandSender sender, String action)
	public int iConomyversion()
	{	
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("iConomy"))
		{	
			try
			{
				iConomyversion = getConfig().getInt("iConomy");
			}
			catch (Exception E)
			{
				E.printStackTrace();
			}
		}
		else
		{
			System.out.println("[xpShop]" + ChatColor.RED + (getConfig().getString("iConomy5.error." + getConfig().getString("language"))));
			return 0;
		}
		return iConomyversion;
	}
	public double getBalance56(Player player)
	{
		if(iConomyversion == 5)
		{
			balance = Methods.getMethod().getAccount(player.getName()).balance();
		}
		else if(iConomyversion == 6)
		{
			balance = new Accounts().get(player.getName()).getHoldings().getBalance();
		}
		return balance;
	}
	public void substractmoney56(double amount, Player player)
	{
		if(iConomyversion == 5)
		{
			Methods.getMethod().getAccount(player.getName()).subtract(amount);
		}
		else if(iConomyversion == 6)
		{	
			com.iCo6.system.Account account = new Accounts().get(player.getName());
			account.getHoldings().subtract(amount);
		}
	}
	public void addmoney56(double amount, Player player)
	{
		if(iConomyversion == 5)
		{
			Methods.getMethod().getAccount(player.getName()).add(amount);
		}
		else if(iConomyversion == 6)
		{	
			com.iCo6.system.Account account = new Accounts().get(player.getName());
			account.getHoldings().add(amount);
		}
	}
}

