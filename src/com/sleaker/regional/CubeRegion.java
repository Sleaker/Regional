package com.sleaker.regional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

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

	protected CubeRegion(String name, short id, PrivilegedList privs) {
		super(name, id, privs);
	}

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
		/*
		 * TODO: Fix comparison
		 *
		if (o1.equals(o2))
			return 0;
		else if (o1.containsAll(o2.cubeSet))
			return 1;
		else if (o2.containsAll(o1.cubeSet))
			return -1;
		else
		 */
		return 0;
	}
}
