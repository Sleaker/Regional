package com.herocraftonline.regional.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.herocraftonline.regional.flags.Flag;
import com.herocraftonline.regional.flags.ResolvedFlags;
import com.herocraftonline.regional.regions.Cube;
import com.herocraftonline.regional.regions.CubeRegion;
import com.herocraftonline.regional.regions.Region;
import com.herocraftonline.regional.regions.WorldRegion;

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
	private Map<Cube, List<CubeRegion>> areaMap;

	/**
	 * Map of cached resolved flags, used so cube flag resolution can be cached to increase performance 
	 */
	private Map<Cube, ResolvedFlags> flagCache = new LinkedHashMap<Cube, ResolvedFlags>();

	private final UniverseRegionManager uManager;

	public WorldRegionManager(UniverseRegionManager uManager) {
		regionMap = new HashMap<Integer, CubeRegion>();
		areaMap = new HashMap<Cube, List<CubeRegion>>();
		this.uManager = uManager;
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
					areaMap.put(cube, new ArrayList<CubeRegion>());

				areaMap.get(cube).add(region);
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * Gets the resolved flags at a given cube
	 * 
	 * @param cube
	 * @return
	 */
	public ResolvedFlags getResolvedFlags(Cube cube) {
		if (flagCache.containsKey(cube))
			return flagCache.get(cube);

		ResolvedFlags resolvedFlags = null;
		Set<Region> regions = new HashSet<Region>(areaMap.get(cube));
		for (Region region : areaMap.get(cube)) {
			Region parent = regionMap.get(region.getParentId());
			if (parent != null)
				regions.remove(parent);
		}
		Map<Flag<?>, Object> resolved = new HashMap<Flag<?>, Object>();
		for (Region region : regions) {
			if (resolved.isEmpty()) {
				resolved.putAll(region.getInheritedFlags(uManager));
				continue;
			}
			resolved.keySet().retainAll(region.getInheritedFlags(uManager).keySet());
		}

		return resolvedFlags;
	}
}
