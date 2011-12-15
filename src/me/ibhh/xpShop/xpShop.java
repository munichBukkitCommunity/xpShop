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
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;
import com.nijikokun.register.payment.Methods;

public class xpShop extends JavaPlugin {


	private String ActionxpShop;
	private Holdings balance5;
	private Double balance;
	private int buy;
	private int sell;
	private int buylevel;
	private int selllevel;
	private double addmoney;
	private double getmoney;
	private int SubstractedXP;
	public iConomy iConomy = null;
	public int iConomyversion = 0;
	private int rounds = 0;

	@Override
	public void onDisable() {

		System.out.println("[xpShop] disabled!");

	}





	@Override
	public void onEnable()
	{

		iConomyversion();
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
		System.out.println("[xpShop]Version: " + getDescription().getVersion() + " successfully enabled!");
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
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("xpShop"))
			{
				if (args.length == 2)
				{
					ActionxpShop = args[0];
					if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
					{
						if(checkpermissions(sender, args[0]) == true)
						{
							if (args [0].equalsIgnoreCase("info")) 
							{
								@SuppressWarnings("deprecation")
								int Experience = player.getExperience();
								sender.sendMessage(player.getName() + "'s exp until the next level is " + Experience);
							}

							if (args[0].equals("help"))
							{
								help(player, args);
							}
							else
								if (args[0].equals("buy"))
								{
									buy = Integer.parseInt (args[1]);
									buy(player, buy);
								}
								else
									if (args[0].equals("sell"))
									{
										sell = Integer.parseInt(args[1]);
										sell(player, sell);
									}
									else
										if (args[0].equals("buylevel"))
										{
											buylevel = Integer.parseInt(args[1]);
											buylevel(player, buylevel);
										}
										else
											if (args[0].equals("selllevel"))
											{
												selllevel = Integer.parseInt(args[1]);
												selllevel(player, selllevel);
											}
											else
											{
												help(player, args);
												return false;
											}
						} //if(checkpermissions(sender, args[0]))
						else
						{
							Logger.getLogger("Minecraft").warning((getConfig().getString("permissions.error." + getConfig().getString("language"))));
							return false;
						}
					} //if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
					else
					{ //kein PermissionsEx
						Logger.getLogger("Minecraft").warning((getConfig().getString("permissions.notfound." + getConfig().getString("language"))));
						return false;
					}
				} //if (args.length == 2)
				else
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.toomanyarguments." + getConfig().getString("language"))));
					help(player, args);
					return false;
				}
			} //if (cmd.getName().equalsIgnoreCase("xpShop"))
		} //if (sender instanceof Player)
		set0();
		return true;
	} //public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)

	public int SubstractedXP()
	{
		SubstractedXP = SubstractedXP + 1;
		return SubstractedXP;
	}

	public boolean buy(CommandSender sender, int buyamount)
	{
		Player player = (Player) sender;
		double TOTALXPDOUBLE = (buyamount * (getConfig().getDouble("moneytoxp")));
		int TOTALXP = (int) TOTALXPDOUBLE;
		if(getBalance156(player) > buyamount)
		{
			try
			{
				player.giveExp(TOTALXP);
				if(ActionxpShop.equalsIgnoreCase("buy"))
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + buyamount + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + TOTALXP + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
				}
			}
			catch (NumberFormatException ex)
			{
				player.sendMessage("Invalid exp count: " + buyamount);
			}
			player.saveData();
			substractmoney156(buyamount, player);
		} //if (Balance >= money)
		else
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughmoney." + getConfig().getString("language"))));
		}
		return false;
	}

	public boolean sell(CommandSender sender, int sellamount)
	{
		Player player = (Player) sender;
		if (sellamount == 0)
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
				/**	 Noch nicht von Bukkit implementiert!
				if(player.getTotalExperience() >= sellamount)
				{
					getmoney = (getConfig().getDouble("xptomoney"));
					int neuXP = player.getTotalExperience() - sellamount;
					player.setTotalExperience(neuXP);
					addmoney156((getmoney * sellamount), player);
					if(ActionxpShop.equalsIgnoreCase("sell"))
					{
						player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + sellamount + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + addmoney + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
					}
				}
				else
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
				}
				 **/
				if(SubstractedXP != 0)
				{
					SubstractedXP = 0;
				}
				getmoney = (getConfig().getDouble("xptomoney"));
				while((SubstractedXP < sellamount) || ((player.getLevel() + player.getExp() >= 0.20) && (SubstractedXP < sellamount)))
				{
					if(player.getExp() <= 0)
					{
						try
						{
							SubstractedXP();
							int level = player.getLevel();
							level = level - 1;
							player.setLevel(level);
							player.setExp( (float) 0.999999);
							addmoney156(getmoney, player);
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
						player.giveExp(-1);
						addmoney156(getmoney, player);
						SubstractedXP();
					}
				} //while(SubstractedXP > TOTALXP)
			}
			catch (NumberFormatException ex)
			{
				player.sendMessage("Invalid exp count: " + sellamount);
				return false;
			}
			player.saveData();
		}
		addmoney = SubstractedXP * (getConfig().getDouble("xptomoney"));
		if(ActionxpShop.equalsIgnoreCase("sell"))
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + SubstractedXP + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + addmoney + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
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
			} //if(permissions.has(player, "xpShop." + action))
			else
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("permissions.error." + getConfig().getString("language"))));
				return false;
			}
		} //if (sender instanceof Player)
		else
		{
			System.out.println("[xpShop]" + (getConfig().getString("command.error.noplayer" + getConfig().getString("language"))));
			return false;
		}
	} //public boolean checkpermissions(CommandSender sender, String action)
	public int iConomyversion()
	{
		try
		{
			iConomyversion = getConfig().getInt("moneyplugin");
		}
		catch (Exception E)
		{
			E.printStackTrace();
		}
		return iConomyversion;
	}
	public Double getBalance156(Player player)
	{
		String name = player.getName();
		if(iConomyversion == 5)
		{
			try
			{
				if(hasAccount5(name) == true)
				{
					balance5 = getAccount5(name).getHoldings();
				}
			}
			catch (Exception E)
			{
				System.out.println("[xpShop]" + "No Account!");
				balance5 = null;
				return balance;
			}
			try
			{
				balance = (double) balance5.balance();
			}
			catch (Exception E)
			{
				System.out.println("[xpShop]" + "No Account!");
				balance5 = null;
				return balance;
			}
			return balance;
		}
		else if(iConomyversion == 6)
		{
			balance = new Accounts().get(player.getName()).getHoldings().getBalance();
		}
		else if(iConomyversion == 1)
		{
			balance = Methods.getMethod().getAccount(player.getName()).balance();
		}
		return balance;
	}
	private Account getAccount5(String name) {
		return com.iConomy.iConomy.getAccount(name);
	}
	private boolean hasAccount5(String name) {
		return com.iConomy.iConomy.hasAccount(name);
	}
	public void substractmoney156(double amountsubstract, Player player)
	{
		String name = player.getName();
		if(iConomyversion == 5)
		{
			getAccount5(name).getHoldings().subtract(amountsubstract);
		}
		else if(iConomyversion == 6)
		{
			com.iCo6.system.Account account = new Accounts().get(player.getName());
			account.getHoldings().subtract(amountsubstract);
		}
		else if(iConomyversion == 1)
		{
			Methods.getMethod().getAccount(player.getName()).subtract(amountsubstract);
		}
		return;
	}
	public void addmoney156(double amountadd, Player player)
	{
		String name = player.getName();
		if(iConomyversion == 5)
		{
			getAccount5(name).getHoldings().add(amountadd);
		}
		else if(iConomyversion == 6)
		{
			com.iCo6.system.Account account = new Accounts().get(player.getName());
			account.getHoldings().add(amountadd);
		}
		else if(iConomyversion == 1)
		{
			Methods.getMethod().getAccount(player.getName()).add(amountadd);
		}
		return;
	}
	public void set0()
	{
		ActionxpShop = "0";
		addmoney = 0;
		getmoney = 0;
		SubstractedXP = 0;
		buy = 0;
		sell = 0;
	}
	public void buylevel(CommandSender sender, int levelamontbuy)
	{
		Player player = (Player) sender;
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("iConomy"))
		{
			int levelbuy = player.getLevel();
			while ((getBalance156(player) > getConfig().getDouble("moneytoxp")) && ((player.getLevel() - levelbuy) <= levelamontbuy) || (player.getLevel() - levelbuy) <= levelamontbuy)
			{
				rounds = rounds + 1;
				buy(sender, 1);
			}
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + ((getConfig().getDouble("moneytoxp")) * rounds) + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + rounds + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
		}
	}
	public void selllevel(CommandSender sender, int levelamontsell)
	{
		Player player = (Player) sender;
		if(player.getLevel() + player.getExp() <= 0.20)
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughxp." + getConfig().getString("language"))));
			return;
		}
		else
		{
			if(Bukkit.getServer().getPluginManager().isPluginEnabled("iConomy"))
			{
				int levelsell = player.getLevel();
				int rounds = 0;
				while ((player.getLevel() > 0) && ((levelsell - player.getLevel()) <= levelamontsell))
				{
					rounds = rounds++;
					sell(sender, 1);
				}
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + rounds + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + ((getConfig().getDouble("xptomoney")) * rounds) + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
			}
		}
	}
	public void help(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		if(args.length == 0 || args.length >= 3)
		{
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop sell <amount>");
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buy <money>");
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buylevel <amount>");
			player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop selllevel <amount>");
		}
		else
			if(!(args[1].equals("buy") || args[1].equals("sell") || args[1].equals("selllevel") || args[1].equals("buylevel")))
			{
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop sell <amount>");
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buy <money>");
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buylevel <amount>");
				player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop selllevel <amount>");
			}
			else
				if (args[1].equals("buy"))
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buy <money>");
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("help.buy" + "." + getConfig().getString("language"))));
				}
				else if (args[1].equals("buylevel"))
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop buy <money>");
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("help.buylevel" + "." + getConfig().getString("language"))));
				}
				else if (args[1].equals("sell"))
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop sell <amount>");
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("help.sell" + "." + getConfig().getString("language"))));
				}
				else if (args[1].equals("selllevel"))
				{
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + "/xpShop sell <amount>");
					player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("help.selllevel" + "." + getConfig().getString("language"))));
				}
	}
}