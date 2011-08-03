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
	
	private static short nextId = 0;
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		PluginManager pm = this.getServer().getPluginManager();
		
		if (!setupDependencies()) {
			pm.disablePlugin(this);
			return;
		}
		
		universeRegionManager = new UniverseRegionManager();
		
		log.info(plugName + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors() + " enabled!");
	}

	/**
	 * Return the next available id
	 * @return
	 */
	public static short getNextId() {
		return nextId++;
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

	private boolean setupDependencies() {
		Plugin listsPlugin = this.getServer().getPluginManager().getPlugin("Lists");
		if (listsPlugin != null) {
			lists = (Lists) listsPlugin;
		}
		
		return (lists != null);
	}
}
