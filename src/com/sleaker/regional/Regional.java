package com.sleaker.regional;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.sleaker.regional.managers.UniverseRegionManager;

public class Regional extends JavaPlugin {

	private String plugName;
	private Logger log = Logger.getLogger("Minecraft");
	private UniverseRegionManager universeRegionManager;
	
	private static short nextId = 0;
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		
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
}
