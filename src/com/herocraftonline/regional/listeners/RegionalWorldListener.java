package com.herocraftonline.regional.listeners;

import java.util.logging.Logger;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.managers.UniverseRegionManager;

public class RegionalWorldListener extends WorldListener {

	private UniverseRegionManager uManager;
	private Logger log = Logger.getLogger("Minecraft");
	
	public RegionalWorldListener(Regional plugin) {
		this.uManager = plugin.getUniverseRegionManager();
	}
	
	@Override
	public void onWorldLoad(WorldLoadEvent event) {
		//if we don't have the loaded worlds right now, then lets load them.
		String worldName = event.getWorld().getName();
		if (uManager.getWorldRegionManager(worldName) == null) {
			log.info(Regional.plugName + " - Loading world Regions for " + worldName);
			uManager.loadWorldRegions(worldName);
		}
	}
}
