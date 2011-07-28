package com.sleaker.regional.area;

import org.bukkit.Location;

public class ChunkArea implements Comparable<ChunkArea> {
	/**
	 * Represents the Z-Chunk Value
	 */
	public final int z;

	/**
	 * Represents the X-Chunk Value
	 */
	public final int x;

	/**
	 * Constructs a new ChunkArea from the given X and Z values;
	 * 
	 * @param x
	 * @param z
	 */
	public ChunkArea(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	/**
	 * Constructs a new ChunkArea from the location object
	 * 
	 * @param loc
	 */
	public ChunkArea(Location loc) {
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
		else if (!(obj instanceof ChunkArea))
			return false;

		ChunkArea that = (ChunkArea) obj;
		return (this.x == that.x && this.z == that.z);
	}

	/**
	 * Compares this chunkArea (x) with chunkArea o.
	 * If x.equals(o) returns 0
	 * When o is an instance of a cube and is contained within x - returns 0
	 * Otherwise if x.x is greater than or equal to o.x - returns 1
	 * Finally if no other conditions are met returns -1
	 * 
	 */
	@Override
	public int compareTo(ChunkArea o) {
		if (this.equals(o))
			return 0;
		else if (o instanceof Cube && this.x == o.x && this.z == o.z)
			return 0;
		else if (this.x >= o.x)
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
