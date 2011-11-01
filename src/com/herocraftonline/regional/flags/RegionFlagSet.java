package com.herocraftonline.regional.flags;

import java.util.Iterator;
import java.util.Set;

import com.herocraftonline.regional.regions.CubeRegion;
import com.herocraftonline.regional.regions.WorldRegion;

public class RegionFlagSet implements Iterable<CubeRegion> {

	final Set<CubeRegion> regions;
	final WorldRegion wRegion;
	
	public RegionFlagSet(Set<CubeRegion> regions, WorldRegion wRegion) {
		this.regions = regions;
		this.wRegion = wRegion;
	}

	public void addRegion(CubeRegion cRegion) {
		regions.add(cRegion);
	}
	
	public void removeRegion(CubeRegion cRegion) {
		regions.remove(cRegion);
	}
	
	@Override
	public Iterator<CubeRegion> iterator() {
		return regions.iterator();
	}
}
