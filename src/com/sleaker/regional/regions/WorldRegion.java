package com.sleaker.regional.regions;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.sleaker.regional.area.Cube;

/**
 * Defines a Region for a specific World
 * 
 * @author sleak
 *
 */
public class WorldRegion extends Region {

	protected WorldRegion(String name, short id, String worldName, Plugin plugin) {
		super(name, id, worldName, plugin);
		this.weight = -1;
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
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if(!(obj instanceof WorldRegion))
			return false;
		
		WorldRegion wR = (WorldRegion) obj;
		return wR.worldName.equals(this.worldName);
	}

	@Override
	public String getTypeName() {
		return "world";
	}


	@Override
	public boolean containsAll(Set<Cube> cubes) {
		for (Cube cube : cubes)
			return cube.worldName == this.worldName;
		
		return false;
	}

	@Override
	public boolean containsCube(Cube cube) {
		return (cube.worldName == this.worldName);	
	}
}
