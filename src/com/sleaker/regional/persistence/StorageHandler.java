package com.sleaker.regional.persistence;

import java.io.File;
import java.util.Set;

import com.sleaker.regional.regions.Region;

public interface StorageHandler {
	
	/**
	 * Load all of a worlds regions into memory
	 * 
	 * @param worldName
	 * @return Whether all regions were loaded successfully
	 */
	public Set<Region> loadRegions(String worldName);
	
	/**
	 * Load the Region from the specified filename
	 * 
	 * @param fileName
	 * @return the Region loaded
	 */
	public Region loadRegion(File regionFile);
	
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
