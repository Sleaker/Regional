package com.sleaker.regional.regions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Regional;
import com.sleaker.regional.area.ChunkArea;
import com.sleaker.regional.area.Cube;

public class ChunkRegion extends CubeRegion {

	protected ChunkRegion(String name, short id, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, id, worldName, privs, plugin);
	}

	public ChunkRegion(String name, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, Regional.getNextId(), worldName, privs, plugin);
	}
	
	/**
	 * Adds a chunk to this area
	 * 
	 * @param chunkArea
	 */
	public void addChunk(ChunkArea chunkArea) {
		Set<Cube> cubeSet = new HashSet<Cube>(9);
		for (int i = 0; i < 8; i++) {
			cubeSet.add(new Cube(chunkArea, i));
		}
		this.addCubes(cubeSet);
	}
	
	@Override
	public String getTypeName() {
		return "chunk";
	}
}
