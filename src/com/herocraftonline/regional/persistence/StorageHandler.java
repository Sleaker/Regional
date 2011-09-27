package com.herocraftonline.regional.persistence;

import java.util.Set;

import com.herocraftonline.regional.regions.Region;

public interface StorageHandler {
	
	/**
	 * Load all of a worlds regions into memory
	 * 
	 * @param worldName
	 * @return Set of the regions loaded
	 */
	public Set<Region> loadRegions(String worldName);
	
	/**
	 * Load the specific region
	 * 
	 * @param fileName
	 * @return the Region loaded
	 */
	public Region loadRegion(String worldName, String region);
	
	/**
	 * Save one Region to storage
	 * 
	 * 
	 * @param region
	 * @return Whether saving was successfull
	 */
	public boolean saveRegion(Region region);
	
	/**
	 * save all regions in a world to file
	 * 
	 * @param worldName
	 * @return Whether saving was successfull
	 */
	public boolean saveRegions(String worldName);
}
