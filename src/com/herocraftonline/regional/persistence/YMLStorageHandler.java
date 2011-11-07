package com.herocraftonline.regional.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.flags.BooleanFlag;
import com.herocraftonline.regional.flags.DoubleFlag;
import com.herocraftonline.regional.flags.Flag;
import com.herocraftonline.regional.flags.FlagType;
import com.herocraftonline.regional.flags.IntegerFlag;
import com.herocraftonline.regional.flags.StringFlag;
import com.herocraftonline.regional.regions.ChunkRegion;
import com.herocraftonline.regional.regions.Cube;
import com.herocraftonline.regional.regions.CubeRegion;
import com.herocraftonline.regional.regions.Region;
import com.herocraftonline.regional.regions.WorldRegion;

public class YMLStorageHandler implements StorageHandler {

	Regional plugin;
	private static Logger log = Logger.getLogger("Minecraft");

	public YMLStorageHandler(Regional plugin) {
		this.plugin = plugin;
	}

	@Override
	public Set<Region> loadRegions(String worldName) {
		String regionDirName = plugin.getDataFolder() + File.separator + worldName + File.separator;
		File regionDir = new File(regionDirName);
		Set<Region> regions = new HashSet<Region>();

		for (File file : regionDir.listFiles()) {
			Region region = loadRegion(file);
			if (region instanceof WorldRegion)
				continue;
			else
				regions.add(region);
		}
		return regions;
	}

	public Region loadRegion(String worldName, String region) {
		File regionFile = new File(plugin.getDataFolder() + File.separator + worldName + File.separator + region + ".yml");
		return loadRegion(regionFile);	
	}
	
	@SuppressWarnings("unchecked")
	private Region loadRegion(File regionFile) {
		FileConfiguration regionConfig = YamlConfiguration.loadConfiguration(regionFile);

		//If the region configuration doesn't exist for this region exit immediately
		if (regionConfig.getKeys(false).isEmpty())
			return null;

		String name = regionConfig.getString("name");
		short id = (short) regionConfig.getInt("id", -1);
		String worldName = regionConfig.getString("world");
		String type = regionConfig.getString("type");
		short parent = (short) regionConfig.getInt("parent", -1);
		List<String> namespaces = regionConfig.getStringList("namespaces");
		byte weight = (byte) regionConfig.getInt("weight", 0);

		if (id == -1)
			return null;
		
		if (type.equals("cube") || type.equals("chunk")) {
			CubeRegion region;
			if (type.equals("cube"))
				region = new CubeRegion(name, id, worldName, weight, namespaces, parent);
			else
				region = new ChunkRegion(name, id, worldName, weight, namespaces, parent);

			loadFlags(region, regionConfig.getConfigurationSection("flags"));
			List<Cube> cubeList = new ArrayList<Cube>();
			for(String cube : (List<String>) regionConfig.getStringList("cubes")) {
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
			WorldRegion region = new WorldRegion(name, id, worldName, plugin.getDescription().getName());
			region.setWeight(weight);
			loadFlags(region, regionConfig);
			return region;
		}

		return null;
	}

	private void loadFlags(Region region, ConfigurationSection flagSection) {
		if (flagSection == null)
			return;
		//Load the state flags
		ConfigurationSection ss = flagSection.getConfigurationSection("state");
		if (ss != null && ss.getKeys(false) != null) {
			Iterator<String> iter = ss.getKeys(false).iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				BooleanFlag flag = new BooleanFlag(nodeName, FlagType.STATE);
				boolean val = Boolean.parseBoolean(ss.getString(nodeName));
				region.addFlag(flag, val);
			}
		}

		//Load boolean flags
		ConfigurationSection bs = flagSection.getConfigurationSection("boolean");
		if (bs != null && bs.getKeys(false) != null) {
			Iterator<String> iter = bs.getKeys(false).iterator();
			while(iter.hasNext()) {
				String nodeName = iter.next();
				BooleanFlag flag = new BooleanFlag(nodeName);
				boolean val = Boolean.parseBoolean(bs.getString(nodeName));
				region.addFlag(flag, val);
			}
		}
		//Load the integer flag
		ConfigurationSection is = flagSection.getConfigurationSection("integer");
		if (is != null && is.getKeys(false) != null) {
			Iterator<String> iter = is.getKeys(false).iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				IntegerFlag flag = new IntegerFlag(nodeName);
				try {
					int val = Integer.parseInt(is.getString(nodeName));
					region.addFlag(flag, val);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		//Load the double flags
		ConfigurationSection ds = flagSection.getConfigurationSection("double");
		if (ds != null && ds.getKeys(false) != null) {
			Iterator<String> iter = ds.getKeys(false).iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				DoubleFlag flag = new DoubleFlag(nodeName);
				try {
					double val = Double.parseDouble(ds.getString(nodeName));
					region.addFlag(flag, val);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		//Load the String Flags
		ConfigurationSection sts = flagSection.getConfigurationSection("string");
		if (sts != null && sts.getKeys(false) != null) {
			Iterator<String> iter = sts.getKeys(false).iterator();
			while (iter.hasNext()) {
				String nodeName = iter.next();
				StringFlag flag = new StringFlag(nodeName);
				region.addFlag(flag, sts.getString(nodeName));
			}
		}
	}


	@Override
	public boolean saveRegion(Region region) {
		String regionDir = plugin.getDataFolder() + File.separator + region.getWorldName() + File.separator;
		File regionFile = null;
		regionFile = new File(regionDir + region.getId() + ".yml");
		FileConfiguration regionConfig = new YamlConfiguration();
		
		//Make our Directories and Files
		try {
			new File(regionDir).mkdirs();
			regionFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		regionConfig.set("name", region.getName());
		regionConfig.set("id", region.getId());
		regionConfig.set("parent", region.getParentId());
		regionConfig.set("namespaces", region.getNamespaces());
		regionConfig.set("world", region.getWorldName());
		regionConfig.set("type", region.getTypeName());
		regionConfig.set("weight", region.getWeight());

		if (region instanceof CubeRegion) {
			CubeRegion cRegion = (CubeRegion) region;
			List<String> cubeStrings = new ArrayList<String>();
			for (Cube cube : cRegion.getCubes())
				cubeStrings.add(cube.toString());

			regionConfig.set("cubes", cubeStrings); 
		}

		//Dump all flags
		ConfigurationSection fs = regionConfig.createSection("flags");
		Iterator<Flag<?>> iter = region.getFlags().keySet().iterator();
		while(iter.hasNext()) {
			Flag<?> flag = iter.next();
			fs.set(flag.getTypeName() + "." + flag.getName(), flag.objectToType(region.getFlag(flag)));
		}

		try {
			regionConfig.save(regionFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
