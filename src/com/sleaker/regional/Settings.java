package com.sleaker.regional;

import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class Settings {

	private static Configuration config;
	private static Logger log = Logger.getLogger("Minecraft");
	
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
	
	public boolean isOnMove() {
		return config.getBoolean(Setting.ON_MOVE.node, (Boolean) Setting.ON_MOVE.defaultVal);
	}
	
	public static int getNextId() {
		int id = config.getInt(Setting.REGIONID.node, (Integer) Setting.REGIONID.defaultVal);
		config.setProperty(Setting.REGIONID.node, id + 1);
		config.save();
		log.info(Regional.plugName + " - ID: " + id);
		return id;
	}
}
