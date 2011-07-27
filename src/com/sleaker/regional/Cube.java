package com.sleaker.regional;

import org.bukkit.Location;

public class Cube {

	/**
	 * Represents the Cubes X-Chunk Value
	 */
	public final int x;
	
	/**
	 * Represents the Cubes Y-value
	 * A Cube Y value comprises 16-Y blocks 
	 */
	public final int y;
	
	/**
	 * Represents the Cubes Z-Chunk Value
	 */
	public final int z;
	
	public Cube(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Cube(Location loc) {
		this.x = (int) loc.getX() >> 4;
		this.y = (int) loc.getY() >> 4;
		this.z = (int) loc.getZ() >> 4;
	}
	
	public int hashCode() {
		int hash = 3;
		hash = 17 * hash + x;
		hash = 17 * hash + y;
		hash = 17 * hash + z;
		return hash;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (!(obj instanceof Cube))
			return false;
		
		Cube that = (Cube) obj;
		return (this.x == that.x && this.y == that.y && this.z == that.z);
	}
}
