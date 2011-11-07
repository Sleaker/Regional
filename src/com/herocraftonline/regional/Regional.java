package com.herocraftonline.regional;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.regional.listeners.RegionalPlayerListener;
import com.herocraftonline.regional.listeners.RegionalWorldListener;
import com.herocraftonline.regional.managers.UniverseRegionManager;

public class Regional extends JavaPlugin {

	public static String plugName;
	private Logger log = Logger.getLogger("Minecraft");
	private UniverseRegionManager uManager;
	private Settings settings;
	private RegionalWorldListener wListener;
	private RegionalPlayerListener pListener;
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		PluginManager pm = this.getServer().getPluginManager();
		
		//Load our settings file
		settings = new Settings(this);
		
		//Instantiate our universe manager
		uManager = new UniverseRegionManager(this);
		
		//Load in regions for worlds that are loaded
		for (World world : this.getServer().getWorlds()) {
			if (uManager.getWorldRegionManager(world.getName()) == null) {
				log.info(plugName + " - Loading world Regions for " + world.getName());
				uManager.loadWorldRegions(world.getName());
			}
		}
		
		//World Event
		wListener = new RegionalWorldListener(this);
		pm.registerEvent(Event.Type.WORLD_LOAD, wListener, Priority.Monitor, this);
		
		
		//If the settings are set to monitor on-move then lets go ahead and enable it
		if (settings.isOnMove()) {
			pListener = new RegionalPlayerListener(this);
			pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, Priority.Monitor, this);
		}
		
		log.info(plugName + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors() + " enabled!");
	}

	/**
	 * @return the UniverseRegionManager
	 */
	public UniverseRegionManager getUniverseRegionManager() {
		return uManager;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}
}