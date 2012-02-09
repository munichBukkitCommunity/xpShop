package me.ibhh.xpShop;

import com.iCo6.system.Accounts;
import com.iConomy.iConomy;
import com.nijikokun.register.payment.Methods;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class iConomyHandler {

    private static int iConomyversion = 0;
    private com.iConomy.system.Holdings balance5;
    private Double balance;
    private xpShop plugin;
    public static Economy economy = null;

    public iConomyHandler(xpShop pl) {
        plugin = pl;
        plugin.aktuelleVersion();
        if (setupEconomy() == true) {
            iConomyversion = 2;
            xpShop.Logger("hooked into Vault", "");
        }
    }

    private static boolean packageExists(String[] packages) {
        try {
            String[] arrayOfString = packages;
            int j = packages.length;
            for (int i = 0; i < j; i++) {
                String pkg = arrayOfString[i];
                Class.forName(pkg);
            }
            return true;
        } catch (Exception localException) {
        }
        return false;
    }

    private Boolean setupEconomy() {
        try {
            RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                economy = economyProvider.getProvider();
            }
        } catch (NoClassDefFoundError e) {
            return false;
        }
        return (economy != null);
    }

    public static int iConomyversion() {
        try {
            if (packageExists(new String[]{"net.milkbowl.vault.economy.Economy"})) {
                iConomyversion = 2;
                xpShop.Logger("hooked into Vault", "");
            }else
            if (packageExists(new String[]{"com.nijikokun.register.payment.Methods"})) {
                iConomyversion = 1;
                xpShop.Logger("hooked into Register", "");
            } else if (packageExists(new String[]{"com.iConomy.iConomy", "com.iConomy.system.Account", "com.iConomy.system.Holdings"})) {
                iConomyversion = 5;
                xpShop.Logger("hooked into iConomy5", "");
            } else if (packageExists(new String[]{"com.iCo6.system.Accounts"})) {
                iConomyversion = 6;
                xpShop.Logger("hooked into iConomy6", "");
            } else {
                xpShop.Logger("cant hook into iConomy5, iConomy6 or Register. Downloading Register!", "");
                xpShop.Logger(" ************ Please configure Register!!!!! **********", "Warning");
                try {
                    String path = "plugins/";
                    Update.autoDownload(
                            "http://mirror.nexua.org/Register/latest/stable/Register.jar",
                            path, "Register.jar");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
            iConomyversion = 0;
        }
        return iConomyversion;
    }

    public Double getBalance156(Player player) {
        String name = player.getName();
        if (iConomyversion == 5) {
            try {
                if (hasAccount5(name)) {
                    this.balance5 = getAccount5(name).getHoldings();
                }
            } catch (Exception E) {
                xpShop.Logger("No Account! Please report it to an admin!", "Error");
                xpShop.PlayerLogger(player, "No Account! Please report it to an admin!", "Error");

                E.printStackTrace();
                this.balance5 = null;
                return this.balance;
            }
            try {
                this.balance = Double.valueOf(this.balance5.balance());
            } catch (Exception E) {
                xpShop.Logger("No Account! Please report it to an admin!", "Error");
                xpShop.PlayerLogger(player, "No Account! Please report it to an admin!", "Error");

                E.printStackTrace();
                this.balance5 = null;
                return this.balance;
            }
            return this.balance;
        }
        if (iConomyversion == 6) {
            try {
                this.balance = new Accounts().get(player.getName()).getHoldings().getBalance();
            } catch (Exception e) {
                xpShop.Logger("No Account! Please report it to an admin!", "Error");
                xpShop.PlayerLogger(player, "No Account! Please report it to an admin!", "Error");
                e.printStackTrace();
                this.balance5 = null;
                return this.balance;
            }
        }
        if (iConomyversion == 1) {
            try {
                this.balance =
                        Double.valueOf(Methods.getMethod().getAccount(player.getName()).balance());
            } catch (Exception e) {
                xpShop.Logger("No Account! Please report it to an admin!", "Error");
                xpShop.PlayerLogger(player, "No Account! Please report it to an admin!", "Error");
                e.printStackTrace();
                this.balance5 = null;
                return this.balance;
            }
        }
        if (iConomyversion == 2) {
            this.balance = economy.getBalance(name);
            return balance;
        }

        return this.balance;
    }

    private com.iConomy.system.Account getAccount5(String name) {
        return iConomy.getAccount(name);
    }

    private boolean hasAccount5(String name) {
        return iConomy.hasAccount(name);
    }

    public void substractmoney156(double amountsubstract, Player player) {
        String name = player.getName();
        if (iConomyversion == 5) {
            try {
                getAccount5(name).getHoldings().subtract(amountsubstract);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 6) {
            try {
                com.iCo6.system.Account account = new Accounts().get(player.getName());
                account.getHoldings().subtract(amountsubstract);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 1) {
            try {
                Methods.getMethod().getAccount(player.getName()).subtract(amountsubstract);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 2) {
            try {
                economy.withdrawPlayer(name, amountsubstract);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        }
    }

    public void addmoney156(double amountadd, Player player) {
        String name = player.getName();
        if (iConomyversion == 5) {
            try {
                getAccount5(name).getHoldings().add(amountadd);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 6) {
            try {
                com.iCo6.system.Account account = new Accounts().get(player.getName());
                account.getHoldings().add(amountadd);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 1) {
            try {
                Methods.getMethod().getAccount(player.getName()).add(amountadd);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        } else if (iConomyversion == 2) {
            try {
                economy.depositPlayer(name, amountadd);
            } catch (Exception e) {
                xpShop.Logger("Cant substract money! Does account exist?", "Error");
                xpShop.PlayerLogger(player, "Cant substract money! Does account exist?", "Error");
                e.printStackTrace();
            }
        }
    }
}