package com.sleaker.regional.regions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Regional;

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
	Set<Cube> cubeSet = new HashSet<Cube>();

	public CubeRegion(String name, short id, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, id, worldName, privs, plugin);
	}

	public CubeRegion(String name, String worldName, PrivilegedList privs, Plugin plugin, Set<Cube> cubeSet) {
		super(name, Regional.getNextId(), worldName, privs, plugin);
		this.cubeSet = cubeSet;
	}
	
	public CubeRegion(String name, String worldName, Plugin plugin) {
		super(name, Regional.getNextId(), worldName, plugin);
	}
	
	public CubeRegion(String name, short id, String worldName, PrivilegedList privs, byte weight, List<String> namespaces, short parentId) {
		super(name, id, worldName, privs, weight, namespaces, parentId);
	}

	@Override
	public boolean containsPoint(Location loc) {
		return containsCube(new Cube(loc));
	}

	/**
	 * Tests if this cube is contained within the region
	 * 
	 * @param cube
	 * @return
	 */
	@Override
	public boolean containsCube(Cube cube) {
		return cubeSet.contains(cube);
	}
	
	/**
	 * Tests if the cubeSet is wholly contained within this region.
	 * If it is, the contained region must be considered a sub-region.
	 * 
	 * @param cubes
	 * @return
	 */
	public boolean containsAll(Collection<Cube> cubes) {
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
	public void addCubes(Collection<Cube> cubes) {
		this.cubeSet.addAll(cubes);
	}
	
	/**
	 * Gets a list collection of all cubes contained in the region
	 * 
	 * @return
	 */
	public List<Cube> getCubes() {
		List<Cube> cubeList = new ArrayList<Cube>();
		cubeList.addAll(cubeSet);
		return cubeList;
	}

	/*
	 * Gets the total number of cubes within the region
	 */
	public int volume() {
		return cubeSet.size();
	}

	@Override
	public String getTypeName() {
		return "cube";
	}
}
