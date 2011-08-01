package com.sleaker.regional.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Regional;
import com.sleaker.regional.area.Cube;
import com.sleaker.regional.flags.Flag;
import com.sleaker.regional.flags.StateFlag;
import com.sleaker.regional.regions.CubeRegion;

public class YMLStorageHandler implements StorageHandler {

	Regional plugin;
	private Logger log;

	public YMLStorageHandler(Regional plugin) {
		this.plugin = plugin;
		this.log = Logger.getLogger("Minecraft");
	}

	@Override
	public boolean loadRegions(String worldName) {
		String regionDirName = plugin.getDataFolder() + File.separator + worldName + File.separator;
		File regionDir = new File(regionDirName);

		for (File file : regionDir.listFiles()) {
			loadRegion(file);
		}

		return true;
	}

	@Override
	public boolean loadRegion(File regionFile) {
		Configuration regionConfig = new Configuration(regionFile);
		regionConfig.load();
		String name = regionConfig.getString("name");
		short id = (short) regionConfig.getInt("id", Regional.getNextId());
		String worldName = regionConfig.getString("world");
		short parent = (short) regionConfig.getInt("parent", -1);
		List<String> namespaces = regionConfig.getStringList("namespaces", new ArrayList<String>());
		//TODO: priviliged list 
		PrivilegedList privs = null;
		byte weight = (byte) regionConfig.getInt("weight", 0);
		//TODO: Custom/Enum flags

		if (regionConfig.getString("type").equals("cube") ) {
			CubeRegion region = new CubeRegion(name, id, worldName, privs, weight, namespaces, parent);
			List<Cube> cubeList = new ArrayList<Cube>();
			for(String cube : regionConfig.getStringList("cubes", new ArrayList<String>())) {
				try {
					cubeList.add(new Cube(cube, worldName));
				} catch (NumberFormatException e) {
					log.severe("[Regional] - Error loading Cubes in RegionId: " + id + " skipping erroneous entry and continuing.");
					continue;
				}
			}
		} else if (regionConfig.getString("type").equals("chunk")) {

		} else if (regionConfig.getString("type").equals("world")) {

		}

		return true;
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
		regionConfig.setProperty("parent", region.getParentId());
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
