package com.sleaker.regional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sleaker.regional.area.ChunkArea;
import com.sleaker.regional.area.Cube;
import com.sleaker.regional.regions.ChunkRegion;
import com.sleaker.regional.regions.CubeRegion;
import com.sleaker.regional.regions.Region;

public class RegionManager {

	private Regional plugin;

	private Map<Short, Region> regionMap;

	private Map<ChunkArea, Set<Region>> areaMap;

	public RegionManager(Regional plugin) {
		this.plugin = plugin;
		regionMap = new HashMap<Short, Region>();
		areaMap = new HashMap<ChunkArea, Set<Region>>();
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
	 * Get a Set of all Regions at the specified ChunkArea
	 * 
	 * @param cArea
	 * @return Set<Region>
	 */
	public Set<Region> getRegionAt(ChunkArea cArea) {
		return areaMap.get(cArea);
	}

	/**
	 * adds the specific region to the regionMap and areaMap
	 * 
	 * @param region
	 * @return
	 */
	public boolean addRegion(Region region) {
		if (!regionMap.containsKey(region.getId())) {
			regionMap.put(region.getId(), region);
			if (region instanceof CubeRegion) {
				CubeRegion reg = (CubeRegion) region;
				for (Cube cube : reg.getCubeSet()) {
					if (!areaMap.containsKey(cube))
						areaMap.put(cube, new HashSet<Region>());
					
					areaMap.get(cube).add(reg);
				}
			} else if (region instanceof ChunkRegion) {
				ChunkRegion reg = (ChunkRegion) region;
				//for (ChunkArea cArea : reg.getChunkSet())
				
			}
			return true;
		} else
			return false;
	}
}
