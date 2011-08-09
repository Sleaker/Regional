package com.sleaker.regional.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sleaker.regional.flags.ResolvedFlags;
import com.sleaker.regional.regions.Cube;
import com.sleaker.regional.regions.CubeRegion;
import com.sleaker.regional.regions.Region;
import com.sleaker.regional.regions.WorldRegion;

/**
 * Represents the RegionManager for a specific world
 * 
 * @author sleak
 */
public class WorldRegionManager {

	/**
	 * Map of all regionIds to their respective Regions
	 */
	private Map<Integer, CubeRegion> regionMap;

	/**
	 * Map of all cubes to the regions they contain
	 */
	private Map<Cube, Set<CubeRegion>> areaMap;
	
	/**
	 * Stores this worlds main region
	 */
	private WorldRegion worldRegion;

	public WorldRegionManager(WorldRegion worldRegion) {
		regionMap = new HashMap<Integer, CubeRegion>();
		areaMap = new HashMap<Cube, Set<CubeRegion>>();
		this.worldRegion = worldRegion;
	}

	public ResolvedFlags resolveFlags(Cube cube) {
		//TODO: Finish Flag resolution logic
		//Remove any parents from the set.
		for (CubeRegion cRegion : areaMap.get(cube)) {
			if (cRegion.getParentId() > -1) {
				
			}
				
		}
		return null;
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
