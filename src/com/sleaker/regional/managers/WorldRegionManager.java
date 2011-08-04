package com.sleaker.regional.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sleaker.regional.regions.Cube;
import com.sleaker.regional.regions.CubeRegion;
import com.sleaker.regional.regions.Region;

/**
 * Represents the RegionManager for a specific world
 * 
 * @author sleak
 */
public class WorldRegionManager {

	private Map<Integer, CubeRegion> regionMap;

	private Map<Cube, Set<CubeRegion>> areaMap;

	public WorldRegionManager() {
		regionMap = new HashMap<Integer, CubeRegion>();
		areaMap = new HashMap<Cube, Set<CubeRegion>>();
	}

	/**
	 * Gets the region from id
	 * 
	 * @param p
	 * @return Region
	 */
	public Region getRegion(int p) {
		return regionMap.get(p);
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
	 * Adds all regions from the collection to the region maps
	 * 
	 * @param regions
	 */
	public void addRegions(Collection<Region> regions) {
		for (Region region : regions) 
			if (region instanceof CubeRegion)
				addRegion((CubeRegion) region);
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
