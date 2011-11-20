package me.ibhh.xpShop;

import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.iCo6.*;

public class server implements Listener {
	    private xpShop plugin;

	    public server(xpShop plugin) {
	        this.plugin = plugin;
	    }


	    public void onPluginDisable(PluginDisableEvent event) {
	        if (plugin.iConomy != null) {
	            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
	                plugin.iConomy = null;
	                System.out.println("[xpShop] un-hooked from iConomy.");
	            }
	        }
	    }


	    public void onPluginEnable(PluginEnableEvent event) {
	        if (plugin.iConomy == null) {
	            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");

	            if (iConomy != null) {
	                if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iConomy.iConomy")) {
	                    plugin.iConomy = (iConomy)iConomy;
	                    System.out.println("[xpShop] hooked into iConomy.");
	                }
	            }
	        }
	    }
	}

