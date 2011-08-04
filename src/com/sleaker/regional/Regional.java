package com.sleaker.regional;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dthielke.lists.Lists;
import com.sleaker.regional.listeners.RegionalBlockListener;
import com.sleaker.regional.listeners.RegionalPlayerListener;
import com.sleaker.regional.listeners.RegionalWorldListener;
import com.sleaker.regional.managers.UniverseRegionManager;

public class Regional extends JavaPlugin {

	private String plugName;
	private Logger log = Logger.getLogger("Minecraft");
	private UniverseRegionManager uManager;
	private Lists lists = null;
	private Settings settings;
	private RegionalWorldListener wListener;
	private RegionalPlayerListener pListener;
	private RegionalBlockListener bListener;
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		PluginManager pm = this.getServer().getPluginManager();
		
		//Setup dependencies - disable the plugin if these fail
		if (!setupDependencies()) {
			pm.disablePlugin(this);
			log.severe(plugName + " Unable to load dependencies, disabling!");
			return;
		}
		
		//Load our settings file
		settings = new Settings(this);
		
		//Instantiate our universe manager
		uManager = new UniverseRegionManager(this);
		
		//Load in regions for worlds that are loaded
		for (World world : this.getServer().getWorlds()) {
			if (uManager.getWorldRegionManager(world.getName()) == null) {
				uManager.loadWorldRegion(world.getName());
				uManager.loadWorldRegions(world.getName());
			}
		}
		
		//World Event
		wListener = new RegionalWorldListener(this);
		pm.registerEvent(Event.Type.WORLD_LOAD, wListener, Priority.Monitor, this);
		
		//Block Events
		bListener = new RegionalBlockListener();
		//pm.registerEvent(Event.Type.BLOCK_BREAK, bListener, Priority.High, this);
		//pm.registerEvent(Event.Type.BLOCK_DAMAGE, bListener, Priority.High, this);
		
		
		//If the settings are set to monitor on-move then lets go ahead and enable it
		if (settings.isOnMove()) {
			pListener = new RegionalPlayerListener();
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
	 * @return the lists plugin
	 */
	public Lists getLists() {
		return lists;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Sets up the dependencies for Regional
	 * 
	 * @return Whether dependencies were found!
	 */
	private boolean setupDependencies() {
		Plugin listsPlugin = this.getServer().getPluginManager().getPlugin("Lists");
		if (listsPlugin != null) {
			lists = (Lists) listsPlugin;
		}
		
		return (lists != null);
	}
}
