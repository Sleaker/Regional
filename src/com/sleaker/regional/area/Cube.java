package com.sleaker.regional.area;

import org.bukkit.Location;


public class Cube extends ChunkArea {

	/**
	 * Represents the Cubes Y-value
	 * A Cube Y value comprises 16-Y blocks 
	 */
	public final int y;

	public Cube(int x, int y, int z, String worldName) {
		super(x, z, worldName);
		this.y = y;
	}

	public Cube(Location loc) {
		super(loc);
		this.y = (int) loc.getY() >> 4;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 17 * hash + worldName.hashCode();
		hash = 17 * hash + x;
		hash = 17 * hash + y;
		hash = 17 * hash + z;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (!(obj instanceof Cube))
			return false;

		Cube that = (Cube) obj;
		return (this.x == that.x && this.y == that.y && this.z == that.z && this.worldName.equals(that.worldName));
	}
}
