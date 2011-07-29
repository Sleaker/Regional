package com.sleaker.regional.regions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Regional;
import com.sleaker.regional.area.Cube;

/**
 * Defines a CubeRegion which is made up of 16x16x16 cubes (Partial chunks)
 * 
 * @author sleak
 *
 */
public class CubeRegion extends Region {

	/**
	 * Set of all Cubes contained within the region
	 */
	private Set<Cube> cubeSet = Collections.synchronizedSet(new HashSet<Cube>());

	protected CubeRegion(String name, short id, PrivilegedList privs, Plugin plugin) {
		super(name, id, privs, plugin);
	}

	public CubeRegion(String name, PrivilegedList privs, Plugin plugin, Set<Cube> cubeSet) {
		super(name, Regional.getNextId(), privs, plugin);
		this.cubeSet = cubeSet;
	}
	
	public CubeRegion(String name, Plugin plugin) {
		super(name, Regional.getNextId(), plugin);
	}
	
	/**
	 * CubeRegions can only contain other Cubes or CubeRegions
	 * 
	 */
	public boolean contains(Object obj) {
		if (obj instanceof Cube)
			return this.cubeSet.contains(obj);
		else
			return false;
	}

	@Override
	public boolean containsPoint(Location loc) {
		return contains(new Cube(loc));
	}

	/**
	 * Tests if the cubeSet is wholly contained within this region.
	 * If it is, the contained region must be considered a sub-region.
	 * 
	 * @param cubes
	 * @return
	 */
	public boolean containsAll(Set<Cube> cubes) {
		return cubeSet.containsAll(cubes);
	}

	/**
	 * Adds a cube to this regions area.
	 * 
	 * @param cube
	 */
	public void addCube(Cube cube) {
		this.cubeSet.add(cube);
	}

	/**
	 * Adds a set of cubes to the regions area.
	 * 
	 * @param cubes
	 */
	public void addCubes(Set<Cube> cubes) {
		this.cubeSet.addAll(cubes);
	}

	public Set<Cube> getCubeSet() {
		return cubeSet;
	}

	/*
	 * Gets the total number of cubes within the region
	 */
	public int volume() {
		return cubeSet.size();
	}

	@Override
	public int compareTo(Region region) {
		//If these are the same region then return 0
		if (this.equals(region))
			return 0;
		if (region instanceof ChunkRegion) {
			ChunkRegion chunkRegion = (ChunkRegion) region;
			if (chunkRegion.containsCubes(this.cubeSet))
				return -1;
			else
				return 0;
		}
		
		if (!(region instanceof CubeRegion))
			return 0;
		
		CubeRegion cubeRegion = (CubeRegion) region;
		if (this.containsAll(cubeRegion.cubeSet))
			return 1;
		else if (cubeRegion.containsAll(this.cubeSet))
			return -1;
		else
			return 0;
	}
}
