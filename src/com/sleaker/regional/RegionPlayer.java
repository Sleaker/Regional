package com.sleaker.regional;

import java.util.Set;

import com.sleaker.regional.regions.Region;

public class RegionPlayer {

	private Set<Region> regions;
	
	public RegionPlayer() {
	}
	
	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}
	
	public void addRegion(Region region) {
		this.regions.add(region);
	}
	
	public void clearRegions() {
		this.regions.clear();
	}
	
	public void removeRegion(Region region) {
		this.regions.remove(region);
	}
}
