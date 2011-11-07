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

	/**
	 * Returns the storage type loaded into the configuration, if none is found it will return the default value
	 * @return storage type selected for use
	 */
	public static String getStorageType() {
		return config.getString(Setting.STORAGE.node, (String) Setting.STORAGE.defaultVal);
	}
	
	/**
	 * determines if the server should be using onMove checks for various tasks
	 * @return boolean - if the plugin should use onMove checks
	 */
	public boolean isOnMove() {
		return config.getBoolean(Setting.ON_MOVE.node, (Boolean) Setting.ON_MOVE.defaultVal);
	}
	
	/**
	 * Returns the next Integer ID available for region identification
	 * @return int nextId
	 */
	public static int getNextId() {
		int id = config.getInt(Setting.REGIONID.node, (Integer) Setting.REGIONID.defaultVal);
		config.set(Setting.REGIONID.node, id + 1);
		log.info(Regional.plugName + " - ID: " + id);
		save();
		return id;
	}
	
	/**
	 * Saves the configuration back to file
	 */
	public static void save() {
		plugin.saveConfig();
	}
	
}
