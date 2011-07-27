package com.sleaker.regional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;

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

	/*
	 * Gets the total number of cubes within the region
	 */
	public int volume() {
		return cubeSet.size();
	}

	@Override
	public int compare(Region o1, Region o2) {
		//If these are the same region then return 0
		if (o1.equals(o2))
			return 0;
		//If these Regions are not CubeRegions they don't follow inheritance rules so don't bother checking
		else if (!(o1 instanceof CubeRegion) || !(o2 instanceof CubeRegion))
			return 0;

		CubeRegion cr1 = (CubeRegion) o1;
		CubeRegion cr2 = (CubeRegion) o2;
		if (cr1.containsAll(cr2.cubeSet))
			return 1;
		else if (cr2.containsAll(cr1.cubeSet))
			return -1;
		else
			return 0;
	}
}
