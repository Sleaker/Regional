package com.sleaker.regional.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sleaker.regional.area.Cube;
import com.sleaker.regional.regions.CubeRegion;
import com.sleaker.regional.regions.Region;

/**
 * Represents the RegionManager for a specific world
 * 
 * @author sleak
 */
public class WorldRegionManager {

	private Map<Short, CubeRegion> regionMap;

	private Map<Cube, Set<CubeRegion>> areaMap;

	public WorldRegionManager() {
		regionMap = new HashMap<Short, CubeRegion>();
		areaMap = new HashMap<Cube, Set<CubeRegion>>();
	}

	/**
	 * Gets the region from id
	 * 
	 * @param id
	 * @return Region
	 */
	public Region getRegion(short id) {
		return regionMap.get(id);
	}

	/**
	 * Returns a collection of all Regions in the Region Manager
	 * 
	 * @return
	 */
	public Collection<CubeRegion> getRegions() {
		return regionMap.values();
	}
	
	/**
	 * adds the specific region to the regionMap and areaMap
	 * 
	 * Returns whether the region was added to the map successfully.
	 * @param region
	 * @return boolean
	 */
	public boolean addRegion(CubeRegion region) {
		if (!regionMap.containsKey(region.getId())) {
			//Check if this region should have a parent even though one was not defined
			if (region.getParent() == null)
				//TODO: Check if this region should have any parents
			
			regionMap.put(region.getId(), region);
			for (Cube cube : region.getCubes()) {
				if (!areaMap.containsKey(cube))
					areaMap.put(cube, new HashSet<CubeRegion>());

				areaMap.get(cube).add(region);
			}
			return true;
		}
		else
			return false;
	} 
}
