package com.sleaker.regional;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dthielke.lists.Lists;
import com.sleaker.regional.managers.UniverseRegionManager;

public class Regional extends JavaPlugin {

	private String plugName;
	private Logger log = Logger.getLogger("Minecraft");
	private UniverseRegionManager universeRegionManager;
	private Lists lists = null;
	private Settings settings;
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		PluginManager pm = this.getServer().getPluginManager();
		
		//Setup dependencies - disable the plugin if these fail
		if (!setupDependencies()) {
			pm.disablePlugin(this);
			log.severe(plugName + " Unable to load dependencies, disabling!");
			return;
		}
		
		//Load our settings file
		settings = new Settings(this);
		
		//Instantiate our universe manager
		universeRegionManager = new UniverseRegionManager(this);
		
		log.info(plugName + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors() + " enabled!");
	}

	/**
	 * @return the UniverseRegionManager
	 */
	public UniverseRegionManager getUniverseRegionManager() {
		return universeRegionManager;
	}
	
	/**
	 * @return the lists
	 */
	public Lists getLists() {
		return lists;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	private boolean setupDependencies() {
		Plugin listsPlugin = this.getServer().getPluginManager().getPlugin("Lists");
		if (listsPlugin != null) {
			lists = (Lists) listsPlugin;
		}
		
		return (lists != null);
	}
}
