package com.herocraftonline.regional;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

	private static Logger log = Logger.getLogger("Minecraft");
	private static FileConfiguration config;
	private static Regional plugin;
	
	protected Settings(Regional plugin) {
		Settings.plugin = plugin;
		config = plugin.getConfig();
		config.options().copyDefaults(true);
		save();
	}

	public static String getStorageType() {
		return config.getString(Setting.STORAGE.node, (String) Setting.STORAGE.defaultVal);
	}
	
	public boolean isOnMove() {
		return config.getBoolean(Setting.ON_MOVE.node, (Boolean) Setting.ON_MOVE.defaultVal);
	}
	
	public static int getNextId() {
		int id = config.getInt(Setting.REGIONID.node, (Integer) Setting.REGIONID.defaultVal);
		config.set(Setting.REGIONID.node, id + 1);
		log.info(Regional.plugName + " - ID: " + id);
		save();
		return id;
	}
	
	public static void save() {
		plugin.saveConfig();
	}
	
}
