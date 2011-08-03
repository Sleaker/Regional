package com.sleaker.regional.regions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Settings;

public class ChunkRegion extends CubeRegion {

	public ChunkRegion(String name, short id, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, id, worldName, privs, plugin);
	}

	public ChunkRegion(String name, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, Settings.getNextId(), worldName, privs, plugin);
	}
	
	public ChunkRegion(String name, short id, String worldName, PrivilegedList privs, byte weight, List<String> namespaces, short parent) {
		super(name, id, worldName, privs, weight, namespaces, parent);
	}

	/**
	 * Add a single chunk to the region
	 * 
	 * @param chunk
	 */
	public void addChunk(Chunk chunk) {
		for(int i = 0; i < 8; i++) {
			this.cubeSet.add(new Cube(chunk, i));
		}
	}
	
	/**
	 * Add a set of Chunks to the Region
	 * 
	 * @param chunks
	 */
	public void addChunks(List<Chunk> chunks) {
		for (Chunk chunk : chunks)
			addChunk(chunk);
	}
	
	/**
	 * Test if the Chunk is within the region
	 * 
	 * @param chunk
	 * @return true/false
	 */
	public boolean containsChunk(Chunk chunk) {
		return this.cubeSet.contains(new Cube(chunk, 0));
	}
	
	/**
	 * Checks if the given chunks are contained in the Region
	 * 
	 * @param chunks
	 * @return
	 */
	public boolean containsChunks(List<Chunk> chunks) {
		Set<Cube> cubeSet = new HashSet<Cube>(chunks.size());
		for(Chunk chunk : chunks)
			cubeSet.add(new Cube(chunk, 0));
		
		return this.cubeSet.contains(cubeSet);
	}
	
	@Override
	public String getTypeName() {
		return "chunk";
	}
}
