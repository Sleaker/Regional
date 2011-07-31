package com.sleaker.regional.managers;

import java.util.HashMap;
import java.util.Map;

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
	
	public UniverseRegionManager() {
		worldManagers = new HashMap<String, WorldRegionManager>();
		worldRegions = new HashMap<String, WorldRegion>();
	}
	
	/**
	 * Load a World's Regions from Storage
	 * 
	 * @param worldName
	 * @return
	 */
	public WorldRegionManager load(String worldName) {
		return null;
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
}
