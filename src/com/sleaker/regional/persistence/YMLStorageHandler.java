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
import com.sleaker.regional.flags.BooleanFlag;
import com.sleaker.regional.flags.DoubleFlag;
import com.sleaker.regional.flags.Flag;
import com.sleaker.regional.flags.IntegerFlag;
import com.sleaker.regional.flags.StateFlag;
import com.sleaker.regional.flags.StringFlag;
import com.sleaker.regional.regions.ChunkRegion;
import com.sleaker.regional.regions.Cube;
import com.sleaker.regional.regions.CubeRegion;
import com.sleaker.regional.regions.Region;
import com.sleaker.regional.regions.WorldRegion;

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
	public Region loadRegion(File regionFile) {
		Configuration regionConfig = new Configuration(regionFile);
		regionConfig.load();
		String name = regionConfig.getString("name");
		short id = (short) regionConfig.getInt("id", Regional.getNextId());
		String worldName = regionConfig.getString("world");
		String type = regionConfig.getString("type");
		short parent = (short) regionConfig.getInt("parent", -1);
		List<String> namespaces = regionConfig.getStringList("namespaces", new ArrayList<String>());
		//TODO: priviliged list 
		PrivilegedList privs = null;
		byte weight = (byte) regionConfig.getInt("weight", 0);

		if (type.equals("cube") || type.equals("chunk")) {
			CubeRegion region;
			if (type.equals("cube"))
				region = new CubeRegion(name, id, worldName, privs, weight, namespaces, parent);
			else
				region = new ChunkRegion(name, id, worldName, privs, weight, namespaces, parent);

			loadFlags(region, regionConfig);
			List<Cube> cubeList = new ArrayList<Cube>();
			for(String cube : regionConfig.getStringList("cubes", new ArrayList<String>())) {
				try {
					cubeList.add(new Cube(cube, worldName));
				} catch (NumberFormatException e) {
					log.severe("[Regional] - Error loading Cubes in RegionId: " + id + " skipping erroneous entry and continuing.");
					continue;
				}
			}
			region.addCubes(cubeList);
			return region;
		} else if (type.equals("world")) {
			WorldRegion region = new WorldRegion(name, id, worldName, privs);
			loadFlags(region, regionConfig);
			return region;
		}

		return null;
	}

	private void loadFlags(Region region, Configuration regionConfig) {
		//Load the state flags
		for (int flagId : regionConfig.getIntList("flags.state", new ArrayList<Integer>())) {
			region.addFlag(StateFlag.getFlag(flagId));
		}

		//Load boolean flags
		if (regionConfig.getNodes("flags.boolean") != null) {
			Iterator<String> iter = regionConfig.getNodes("flags.boolean").keySet().iterator();
			while(iter.hasNext()) {
				String nodeName = iter.next();
				BooleanFlag flag = new BooleanFlag(nodeName);
				boolean val = Boolean.parseBoolean(regionConfig.getString("flags.boolean." + nodeName));
				region.addFlag(flag, val);
			}
		}
		//Load the integer flag
		if (regionConfig.getNodes("flags.integer") != null) {
			Iterator<String> iter = regionConfig.getNodes("flags.integer").keySet().iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				IntegerFlag flag = new IntegerFlag(nodeName);
				try {
					int val = Integer.parseInt(regionConfig.getString("flags.integer." + nodeName));
					region.addFlag(flag, val);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		//Load the double flags
		if (regionConfig.getNodes("flags.double") != null) {
			Iterator<String> iter = regionConfig.getNodes("flags.double").keySet().iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				DoubleFlag flag = new DoubleFlag(nodeName);
				try {
					double val = Double.parseDouble(regionConfig.getString("flags.double." + nodeName));
					region.addFlag(flag, val);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		//Load the String Flags
		if (regionConfig.getNodes("flags.string") != null) {
			Iterator<String> iter = regionConfig.getNodes("flags.string").keySet().iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				StringFlag flag = new StringFlag(nodeName);
				region.addFlag(flag, regionConfig.getString("flags.string." + nodeName));
			}
		}
	}


	@Override
	public boolean saveRegion(Region region) {
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

		if (region instanceof CubeRegion) {
			CubeRegion cRegion = (CubeRegion) region;
			List<String> cubeStrings = new ArrayList<String>();
			for (Cube cube : cRegion.getCubes())
				cubeStrings.add(cube.toString());

			regionConfig.setProperty("cubes", cubeStrings); 
		}

		//Dump all custom flags
		Iterator<Flag<?>> iter = region.getCustomFlags().keySet().iterator();
		while(iter.hasNext()) {
			Flag<?> flag = iter.next();
			regionConfig.setProperty("flags." + flag.getTypeName() + "." + flag.getName(), flag.objectToType(region.getCustomFlag(flag)));
		}

		//Dump all state flags
		List<Integer> flagList = new ArrayList<Integer>();
		for (StateFlag flag : region.getFlags()) {
			flagList.add(flag.getId());
		}
		regionConfig.setProperty("flags.state", flagList);

		regionConfig.save();
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
