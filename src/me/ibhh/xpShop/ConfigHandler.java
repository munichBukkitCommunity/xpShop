/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ibhh.xpShop;

/**
 *
 * @author Simon
 */
public class ConfigHandler {
    private xpShop plugin;
    public String 
            language, 
            commanderrorinfo, 
            commanderrorbuyinfo,
            commanderrornotenoughmoney,
            commanderrornotenoughxp,
            commanderrorelse,
            commanderrornoint,
            commanderrornoargs0,
            commanderrortoomanyarguments,
            commanderrorfewargs,
            commanderrornoplayer,
            commandsuccessbuy,
            commandsuccesssell,
            commandsuccessbuylevel,
            commandsuccessselllevel,
            commandsuccesssentxp,
            commandsuccessrecievedxp,
            permissionserror,
            permissionsnotfound,
            iConomyerror,
            helpbuy,
            helpbuylevel,
            helpsell,
            helpselllevel,
            helpinfo,
            helpsend,
            helpinfoxp,
            helpinfolevel,
            infoownXP,
            infootherXP,
            infoownLevel,
            infootherLevel,
            infoPrefix,
            Shopsuccessbuy,
            Shopsuccesssell,
            Shopsuccesssellerselled,
            Shopsuccesssellerbuy,
            Shoperrornotenoughmoneyseller,
            Shoperrornotenoughmoneyconsumer,
            Shoperrornotenoughxpseller,
            Shoperrornotenoughxpconsumer,
            Shoperrorcantbuyhere,
            Shoperrorcantsellhere,
            playernotonline;
    
    public boolean autodownload, debug, firstRun;
    public double moneytoxp, xptomoney;
            
            
    public ConfigHandler(xpShop pl) {
        plugin = pl;
    }
    
    public void loadConfigonStart(){
        try {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
            plugin.reloadConfig();
            xpShop.Logger("Config file found!", "");
            reload();
        } catch (Exception e) {
            e.printStackTrace();
            plugin.onDisable();
        }
    }
    
    public void reload(){
            loadStrings();
            loadBooleans();
            loadDoubles();
    }
    
    public void loadDoubles(){
        moneytoxp = plugin.getConfig().getDouble("moneytoxp");
        xptomoney = plugin.getConfig().getDouble("xptomoney");
    }
    
    public void loadBooleans(){
        debug = plugin.getConfig().getBoolean("debug");
        autodownload = plugin.getConfig().getBoolean("autodownload");
        firstRun = plugin.getConfig().getBoolean("firstRun");
    }
    
    public void loadStrings(){
            language = plugin.getConfig().getString("language");
            playernotonline = plugin.getConfig().getString("playernotonline." + language);
            Shoperrornotenoughmoneyconsumer = plugin.getConfig().getString("Shop.error.notenoughmoneyconsumer." + language);
            Shoperrornotenoughmoneyseller = plugin.getConfig().getString("Shop.error.notenoughmoneyseller." + language);
            Shoperrorcantbuyhere = plugin.getConfig().getString("Shop.error.cantbuyhere." + language);
            Shoperrorcantsellhere = plugin.getConfig().getString("Shop.error.cantsellhere." + language);
            Shoperrornotenoughxpconsumer = plugin.getConfig().getString("Shop.error.notenoughxpconsumer." + language);
            Shoperrornotenoughxpseller = plugin.getConfig().getString("Shop.error.notenoughxpseller." + language);
            Shopsuccessbuy = plugin.getConfig().getString("Shop.success.buy." + language);
            Shopsuccesssell = plugin.getConfig().getString("Shop.success.sell." + language);
            Shopsuccesssellerbuy = plugin.getConfig().getString("Shop.success.sellerbuy." + language);
            Shopsuccesssellerselled = plugin.getConfig().getString("Shop.success.sellerselled." + language);
            commanderrorinfo = plugin.getConfig().getString("command.error.info." + language);
            commanderrorbuyinfo = plugin.getConfig().getString("command.error.buyinfo." + language);
            commanderrornotenoughmoney = plugin.getConfig().getString("command.error.notenoughmoney." + language);
            commanderrornotenoughxp = plugin.getConfig().getString("command.error.notenoughxp." + language);
            commanderrorelse = plugin.getConfig().getString("command.error.else." + language);
            commanderrornoint = plugin.getConfig().getString("command.error.noint." + language);
            commanderrornoargs0 = plugin.getConfig().getString("command.error.noargs0." + language);
            commanderrortoomanyarguments = plugin.getConfig().getString("command.error.toomanyarguments." + language);
            commanderrorfewargs = plugin.getConfig().getString("command.error.fewargs." + language);
            commanderrornoplayer = plugin.getConfig().getString("command.error.noplayer." + language);
            commandsuccessbuy = plugin.getConfig().getString("command.success.buy." + language);
            commandsuccesssell = plugin.getConfig().getString("command.success.sell." + language);
            commandsuccessbuylevel = plugin.getConfig().getString("command.success.buylevel." + language);
            commandsuccessselllevel = plugin.getConfig().getString("command.success.selllevel." + language);
            commandsuccesssentxp = plugin.getConfig().getString("command.success.sentxp." + language);
            commandsuccessrecievedxp = plugin.getConfig().getString("command.success.recievedxp." + language);
            permissionserror = plugin.getConfig().getString("permissions.error." + language);
            permissionsnotfound = plugin.getConfig().getString("permissions.notfound." + language);
            iConomyerror = plugin.getConfig().getString("iConomy.error." + language);
            helpbuy = plugin.getConfig().getString("help.buy." + language);
            helpbuylevel = plugin.getConfig().getString("help.buylevel." + language);
            helpsell = plugin.getConfig().getString("help.sell." + language);
            helpselllevel = plugin.getConfig().getString("help.selllevel." + language);
            helpinfo = plugin.getConfig().getString("help.info." + language);
            helpsend = plugin.getConfig().getString("help.send." + language);
            helpinfoxp = plugin.getConfig().getString("help.infoxp." + language);
            helpinfolevel = plugin.getConfig().getString("help.infolevel." + language);
            infoownXP = plugin.getConfig().getString("info.ownXP." + language);
            infootherXP = plugin.getConfig().getString("info.otherXP." + language);
            infoownLevel = plugin.getConfig().getString("info.ownLevel." + language);
            infootherLevel = plugin.getConfig().getString("info.otherLevel." + language);
            infoPrefix = plugin.getConfig().getString("info.prefix." + language);
    }
}
