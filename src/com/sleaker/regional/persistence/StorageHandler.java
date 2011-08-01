package com.sleaker.regional.persistence;

import java.io.File;

import com.sleaker.regional.regions.CubeRegion;

public interface StorageHandler {
	
	/**
	 * Load all of a worlds regions into memory
	 * 
	 * @param worldName
	 * @return Whether all regions were loaded successfully
	 */
	public boolean loadRegions(String worldName);
	
	/**
	 * Load the Region from the specified filename
	 * 
	 * @param fileName
	 * @return Whether the region was loaded sucessfully
	 */
	public boolean loadRegion(File regionFile);
	
	/**
	 * Save one Region to storage
	 * 
	 * 
	 * @param region
	 * @return Whether saving was successfull
	 */
	public boolean saveRegion(CubeRegion region);
	
	/**
	 * save all regions in a world to file
	 * 
	 * @param worldName
	 * @return Whether saving was successfull
	 */
	public boolean saveRegions(String worldName);
}
