package com.herocraftonline.regional.listeners;

import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.flags.BuiltinFlag;
import com.herocraftonline.regional.flags.RegionFlagSet;
import com.herocraftonline.regional.managers.UniverseRegionManager;
import com.herocraftonline.regional.regions.Cube;

public class RegionalWeatherListener extends WeatherListener {
	
	private UniverseRegionManager uManager;
	
	public RegionalWeatherListener(Regional plugin) {
		uManager = plugin.getUniverseRegionManager();
	}

	@Override
	public void onLightningStrike(LightningStrikeEvent event) {
		if (event.isCancelled())
			return;
		
		Cube cube = new Cube(event.getLightning().getLocation());
		RegionFlagSet rfs = new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());
		event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_LIGHTNING));
	}

	@Override
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.isCancelled())
			return;
		String world = event.getWorld().getName();
		
		event.setCancelled(uManager.getWorldRegionManager(world).getWorldRegion().getFlag(BuiltinFlag.DENY_WEATHER));
	}
	
}
