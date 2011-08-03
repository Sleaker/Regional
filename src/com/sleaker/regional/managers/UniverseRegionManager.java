package com.sleaker.regional.managers;

import java.util.HashMap;
import java.util.Map;

import com.sleaker.regional.Regional;
import com.sleaker.regional.persistence.StorageHandler;
import com.sleaker.regional.persistence.YMLStorageHandler;
import com.sleaker.regional.regions.WorldRegion;

/**
 * Manages the world-specific regions, and the managers for those worlds
 * @author sleak
 *
 */
public class UniverseRegionManager {
	
	/**
	 * Set of all World Managers currently loaded
	 */
	private Map<String, WorldRegionManager> worldManagers;
	
	/**
	 * Set of all World-Specific Regions
	 */
	private Map<String, WorldRegion> worldRegions;
	
	private StorageHandler regionStore;
	
	public UniverseRegionManager(Regional plugin) {
		worldManagers = new HashMap<String, WorldRegionManager>();
		worldRegions = new HashMap<String, WorldRegion>();
		loadStorage(plugin);
	}
	
	/**
	 * Get a worlds region manager
	 * 
	 * @param worldName
	 * @return
	 */
	public WorldRegionManager getWorldRegionManager(String worldName) {
		return worldManagers.get(worldName);
	}
	
	/**
	 * Get a worlds Region
	 * 
	 * @param worldName
	 * @return
	 */
	public WorldRegion getWorldRegion(String worldName) {
		return worldRegions.get(worldName);
	}
	
	/**
	 * Adds a worldregion to the worldRegions map
	 * 
	 * @param region
	 */
	public void loadWorldRegion(WorldRegion region) {
		worldRegions.put(region.getWorldName(), region);
	}
	
	/**
	 * Load a World's Regions from Storage
	 * 
	 * @param worldName
	 * @return
	 */
	public void loadWorldRegions(String worldName) {
		worldManagers.put(worldName, new WorldRegionManager());
		regionStore.loadRegions(worldName);
	}
	
	/**
	 * Clears the WorldRegion and WorldManagers mappings
	 */
	public void unloadAll() {
		worldManagers.clear();
		worldRegions.clear();
	}
	
	/**
	 * Unload a specific World's Region & Manager
	 * 
	 * @param worldName
	 */
	public void unload(String worldName) {
		worldManagers.remove(worldName);
		worldRegions.remove(worldName);
	}
	
	private void loadStorage(Regional plugin) {
		if (plugin.getSettings().getStorageType().contains("yml")) {
			regionStore = new YMLStorageHandler(plugin);
		} else if (plugin.getSettings().getStorageType().contains("sql")) {
			return;
		}
	}
}
