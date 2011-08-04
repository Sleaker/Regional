package com.sleaker.regional.listeners;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

import com.sleaker.regional.Regional;
import com.sleaker.regional.managers.UniverseRegionManager;

public class RegionalWorldListener extends WorldListener {

	private UniverseRegionManager uManager;

	public RegionalWorldListener(Regional plugin) {
		this.uManager = plugin.getUniverseRegionManager();
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.world.WorldListener#onWorldLoad(org.bukkit.event.world.WorldLoadEvent)
	 */
	@Override
	public void onWorldLoad(WorldLoadEvent event) {
		//if we don't have the loaded worlds right now, then lets load them.
		String worldName = event.getWorld().getName();
		if (uManager.getWorldRegionManager(worldName) == null) {
			uManager.loadWorldRegion(worldName);
			uManager.loadWorldRegions(worldName);
		}
	}
}
