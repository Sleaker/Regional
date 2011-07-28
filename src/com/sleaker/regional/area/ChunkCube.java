package com.sleaker.regional.area;

import org.bukkit.Location;

public class ChunkCube implements Comparable<ChunkCube> {
	/**
	 * Represents the Cubes Z-Chunk Value
	 */
	public final int z;

	/**
	 * Represents the Cubes X-Chunk Value
	 */
	public final int x;

	/**
	 * Constructs a new ChunkCube from the given X and Z values;
	 * 
	 * @param x
	 * @param z
	 */
	public ChunkCube(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	/**
	 * Constructs a new ChunkCube from the location object
	 * 
	 * @param loc
	 */
	public ChunkCube(Location loc) {
		this.x = (int) loc.getX() >> 4;
		this.z = (int) loc.getZ() >> 4;
	}

	public int hashCode() {
		int hash = 3;
		hash = 17 * hash + x;
		hash = 17 * hash + z;
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (!(obj instanceof ChunkCube))
			return false;

		ChunkCube that = (ChunkCube) obj;
		return (this.x == that.x && this.z == that.z);
	}

	@Override
	public int compareTo(ChunkCube o) {
		if (this.equals(o))
			return 0;
		else if (o instanceof Cube && this.x == o.x && this.z == o.z)
			return 0;
		else if (this.x > o.x)
			return 1;
		else
			return -1;
	}
	
	/**
	 * tests if this ChunkCube contains a Cube
	 * Always returns false if this is an instance 
	 * of a cube.  Cubes can never contain themselves.
	 * 
	 * @param c
	 * @return
	 */
	public boolean contains (Cube c) {
		if (this instanceof Cube)
			return false;
		return (this.compareTo(c) == 0);
	}

}
