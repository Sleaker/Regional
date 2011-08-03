package com.sleaker.regional;

import org.bukkit.util.config.Configuration;

public class Settings {

	private static Configuration config;
	private static int nextId = 0;
	
	public Settings(Regional plugin) {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
			
		}
		
		config = plugin.getConfiguration();
		if (config.getKeys().isEmpty()) {
			loadDefaults();
		}
	}
	
	private void loadDefaults() {
		for (Setting setting : Setting.values())
			config.setProperty(setting.node, setting.defaultVal);
		
		config.save();
	}

	public String getStorageType() {
		return config.getString(Setting.STORAGE.node, (String) Setting.STORAGE.defaultVal);
	}
	
	public static int getNextId() {
		if (nextId < 1)
			nextId = config.getInt(Setting.REGIONID.node, (Integer) Setting.REGIONID.defaultVal);
		else 
			nextId++;
		
		config.setProperty(Setting.REGIONID.node, nextId + 1);
		return nextId;
	}
}
