package com.sleaker.regional.regions;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class WorldRegion extends Region {

	protected WorldRegion(String name, short id, String worldName, Plugin plugin) {
		super(name, id, worldName, plugin);
	}

	@Override
	public boolean contains(Object obj) {
		return false;
	}

	@Override
	public boolean containsPoint(Location loc) {
		return loc.getWorld().getName().equals(worldName);
	}

	/**
	 * WorldRegions can only have a volume of 1 as they can only be on 1 world
	 */
	@Override
	public int volume() {
		return 1;
	}

	@Override
	public int compareTo(Region region) {
		if (this.worldName.equals(region.worldName))
			return 0;
		else
			return 1;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if(!(obj instanceof WorldRegion))
			return false;
		
		WorldRegion wR = (WorldRegion) obj;
		return wR.worldName.equals(this.worldName);
	}
}
