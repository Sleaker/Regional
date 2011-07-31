package com.sleaker.regional.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.util.config.Configuration;

import com.sleaker.regional.Regional;
import com.sleaker.regional.area.Cube;
import com.sleaker.regional.flags.Flag;
import com.sleaker.regional.flags.StateFlag;
import com.sleaker.regional.regions.CubeRegion;

public class YMLStorageHandler implements StorageHandler {

	Regional plugin;
	
	public YMLStorageHandler(Regional plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public CubeRegion loadRegions(String worldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveRegion(CubeRegion region) {
		String regionDir = plugin.getDataFolder() + File.separator + region.getWorldName() + File.separator;
		File regionFile = new File(regionDir + region.getId() + ".yml");
		Configuration regionConfig;
		//Make our Directories and Files
		try {
			new File(regionDir).mkdirs();
			regionFile.createNewFile();
			regionConfig = new Configuration(regionFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		regionConfig.load();
		
		regionConfig.setProperty("name", region.getName());
		regionConfig.setProperty("id", region.getId());
		regionConfig.setProperty("parent", region.getParent());
		regionConfig.setProperty("namespaces", region.getNamespaces());
		regionConfig.setProperty("priviliged-list", region.getPrivs().getName());
		regionConfig.setProperty("world", region.getWorldName());
		regionConfig.setProperty("type", region.getTypeName());
		regionConfig.setProperty("weight", region.getWeight());
		
		List<String> cubeStrings = new ArrayList<String>();
		for (Cube cube : region.getCubes())
			cubeStrings.add(cube.toString());
		
		regionConfig.setProperty("cubes", cubeStrings);
		
		//Dump all custom flags
		Iterator<Flag<?>> iter = region.getCustomFlags().keySet().iterator();
		while(iter.hasNext()) {
			Flag<?> flag = iter.next();
			regionConfig.setProperty("flags.custom." + flag.getName(), flag.unmarshal(region.getCustomFlag(flag)));
		}
		
		//Dump all state flags
		for (StateFlag flag : region.getFlags()) {
			regionConfig.setProperty("flags.standard." + flag.getName(), true);
		}
		
		return true;
	}

	@Override
	public boolean saveRegions(String worldName) {
		Collection<CubeRegion> regionList = plugin.getUniverseRegionManager().getWorldRegionManager(worldName).getRegions();
		boolean success = true;
		for (CubeRegion region : regionList) {
			success = saveRegion(region);
		}
		return success;
	}
}
