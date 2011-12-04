package me.ibhh.xpShop;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.iCo6.system.Account;
import com.iCo6.system.Accounts;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class xpShop extends JavaPlugin {


	public String ActionxpShop;
	public double money = 0;
	public double addmoney;
	public double getmoney;
	public int xp = 0;
	int SubstractedXP;

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
				Player player = (Player) sender;
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
							if(permissions.has(player, "xpShop." + ActionxpShop))
							{
								if (ActionxpShop.equals("buy") ||  ActionxpShop.equals("sell"))
								{
									try {
										if (ActionxpShop.equals("buy"))
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
											Account account = new Accounts().get(playerAccount);
											Double Balance = new Accounts().get(playerAccount).getHoldings().getBalance();
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
												Double BalanceEnd = Balance - money;
												account.getHoldings().setBalance(BalanceEnd);
											}	//if (Balance >= money)
											else
											{
												player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.error.notenoughmoney." + getConfig().getString("language"))));
											}
										}
										else if (ActionxpShop.equals("sell"))
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
											Account account = new Accounts().get(playerAccount);
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
														addmoney = xp * (getConfig().getDouble("xptomoney"));
														getmoney = (getConfig().getDouble("xptomoney"));
														while(SubstractedXP < xp)
														{	
															if(player.getExp() <= 0)
															{
																try
																{
																	int level = player.getLevel();
																	level = level - 1;
																	player.setLevel(level);
																	player.setExp( (float) 0.999999);
																	Double Balance = new Accounts().get(playerAccount).getHoldings().getBalance();
																	Double BalanceEnd = Balance + getmoney;
																	account.getHoldings().setBalance(BalanceEnd);
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
																Double Balance = new Accounts().get(playerAccount).getHoldings().getBalance();
																Double BalanceEnd = Balance + getmoney;
																account.getHoldings().setBalance(BalanceEnd);
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
											player.sendMessage(ChatColor.GRAY + "[xpShop]" + ChatColor.RED + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".1")) + " " + xp + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".2")) + " " + addmoney + " " + (getConfig().getString("command.success." + ActionxpShop + "." + getConfig().getString("language") + ".3")));
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
			}	//if (cmd.getName().equalsIgnoreCase("xpShop"))
		}	//if (sender instanceof Player) 
		return true;
	}	//public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	public int SubstractedXP()
	{
		SubstractedXP = SubstractedXP + 1;
		return SubstractedXP;
	}
}



