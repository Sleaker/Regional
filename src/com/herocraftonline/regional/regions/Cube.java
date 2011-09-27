package com.herocraftonline.regional.regions;

import org.bukkit.Chunk;
import org.bukkit.Location;


public class Cube {
	/**
	 * Represents the Z-Chunk Value
	 */
	public final int z;

	/**
	 * Represents the X-Chunk Value
	 */
	public final int x;
	
	/**
	 * Represents the World that this Chunk is on
	 */
	public final String worldName;
	
	/**
	 * Represents the Cubes Y-value
	 * A Cube Y value comprises 16-Y blocks 
	 */
	public final int y;

	public Cube(int x, int y, int z, String worldName) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
	}

	public Cube(Location loc) {
		this.x = (int) loc.getX() >> 4;
		this.y = (int) loc.getY() >> 4;
		this.z = (int) loc.getZ() >> 4;
		this.worldName = loc.getWorld().getName();
	}

	public Cube(Chunk chunk, int y) {
		this.x = chunk.getX();
		this.y = y;
		this.z = chunk.getZ();
		this.worldName = chunk.getWorld().getName();
	}

	public Cube(String cube, String worldName) throws NumberFormatException {
		String[] vals = cube.split(",");
		this.x = Integer.parseInt(vals[0]);
		this.y = Integer.parseInt(vals[1]);
		this.z = Integer.parseInt(vals[2]);
		this.worldName = worldName;
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
	
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}
}
