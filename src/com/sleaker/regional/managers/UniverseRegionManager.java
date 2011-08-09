package com.sleaker.regional.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sleaker.regional.Regional;
import com.sleaker.regional.Settings;
import com.sleaker.regional.persistence.StorageHandler;
import com.sleaker.regional.persistence.YMLStorageHandler;
import com.sleaker.regional.regions.Region;
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

	/**
	 * Instance of the storage handler currently in use
	 */
	private StorageHandler regionStore;

	private Regional plugin;

	public UniverseRegionManager(Regional plugin) {
		this.plugin = plugin;
		worldManagers = new HashMap<String, WorldRegionManager>();
		worldRegions = new HashMap<String, WorldRegion>();
		loadStorage(plugin);
	}

	/**
	 * Gets a region from the specified world with the given Id
	 * 
	 * @param worldName
	 * @param id
	 * @return
	 */
	public Region getRegion(String worldName, int id) {
		//If we have a null region we should try to load them first
		if (worldRegions.get(worldName) == null) {
			loadWorldRegions(worldName);
		}
		
		if (worldRegions.get(worldName).getId() == id)
			return worldRegions.get(worldName);

		return worldManagers.get(worldName).getRegion(id);
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
	 * Get a Worlds Global Region
	 * 
	 * @param worldName
	 * @return
	 */
	public WorldRegion getWorldRegion(String worldName) {
		if (worldRegions.get(worldName) == null)
			loadWorldRegion(worldName);

		return worldRegions.get(worldName);
	}

	/**
	 * Loads a Global worldregion to the worldRegions map - if it does not exist - then it creates the worldregion
	 * 
	 * @param region
	 */
	public void loadWorldRegion(String worldName) {
		WorldRegion region = (WorldRegion) regionStore.loadRegion(worldName, worldName);
		if (region == null) {
			region = new WorldRegion(Settings.getNextId(), worldName, null, plugin.getDescription().getName());
			regionStore.saveRegion(region);
		}
		worldRegions.put(region.getWorldName(), region);
	}

	/**
	 * Load a World's Regions from Storage
	 * 
	 * @param worldName
	 * @return
	 */
	public void loadWorldRegions(String worldName) {
		//Load the specific world region first
		loadWorldRegion(worldName);
		WorldRegionManager worldManager = null;
		if (worldManagers.containsKey(worldName))
			worldManager = worldManagers.get(worldName);
		else {
			worldManager = new WorldRegionManager(worldRegions.get(worldName));
			worldManagers.put(worldName, worldManager);
		}
		Set<Region> regions = regionStore.loadRegions(worldName);
		worldManager.addRegions(regions);
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
