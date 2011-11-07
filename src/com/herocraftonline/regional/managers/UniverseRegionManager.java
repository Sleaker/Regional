package com.herocraftonline.regional.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.Settings;
import com.herocraftonline.regional.persistence.StorageHandler;
import com.herocraftonline.regional.persistence.YMLStorageHandler;
import com.herocraftonline.regional.regions.Region;

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
	 * Instance of the storage handler currently in use
	 */
	private StorageHandler regionStore;

	public UniverseRegionManager(Regional plugin) {
		worldManagers = new HashMap<String, WorldRegionManager>();
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
	 * Load a World's Regions from Storage
	 * 
	 * @param worldName
	 * @return
	 */
	public void loadWorldRegions(String worldName) {
		//Load the specific world region first
		WorldRegionManager worldManager = null;
		if (worldManagers.containsKey(worldName))
			worldManager = worldManagers.get(worldName);
		else {
			worldManager = new WorldRegionManager(this);
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
	}

	/**
	 * Unload a specific World's Region & Manager
	 * 
	 * @param worldName
	 */
	public void unload(String worldName) {
		worldManagers.remove(worldName);
	}

	/**
	 * Load the storage manager.
	 * 
	 * @param plugin
	 */
	private void loadStorage(Regional plugin) {
		if (Settings.getStorageType().contains("yml")) {
			regionStore = new YMLStorageHandler(plugin);
		} else if (Settings.getStorageType().contains("sql")) {
			return;
		}
	}
}
