package com.sleaker.regional.persistence;

import com.sleaker.regional.regions.CubeRegion;

public interface StorageHandler {
	
	/**
	 * Load all of a worlds regions into memory
	 * 
	 * @param worldName
	 * @return
	 */
	public CubeRegion loadRegions(String worldName);
	
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
