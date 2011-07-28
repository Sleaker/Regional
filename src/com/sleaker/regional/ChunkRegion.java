package com.sleaker.regional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.area.ChunkArea;
import com.sleaker.regional.area.Cube;

public class ChunkRegion extends Region {

	/**
	 * Set of chunks contained within the region
	 */
	private Set<ChunkArea> chunkSet = Collections.synchronizedSet(new HashSet<ChunkArea>());

	protected ChunkRegion(String name, short id, PrivilegedList privs, Plugin plugin) {
		super(name, id, privs, plugin);
	}

	public ChunkRegion(String name, PrivilegedList privs, Plugin plugin) {
		super(name, Regional.getNextId(), privs, plugin);
	}

	@Override
	public boolean contains(Object obj) {
		if (obj instanceof Cube)
			return this.chunkSet.contains(obj);
		else
			return false;
	}

	@Override
	public boolean containsPoint(Location loc) {
		return this.chunkSet.contains(new ChunkArea(loc));
	}

	/**
	 * Tests if objSet is contained within this Region
	 * 
	 * @param objSet
	 * @return
	 */
	public boolean containsAll(Set<ChunkArea> objSet) {
		return this.chunkSet.containsAll(objSet);
	}

	public boolean containsCubes(Set<Cube> cubeSet) {
		for (Cube c : cubeSet)
			if (!chunkSet.contains(c))
				return false;

		return true;
	}

	@Override
	public int compareTo(Region region) {
		if (region instanceof ChunkRegion) {
			ChunkRegion chunkregion = (ChunkRegion) region;
			if (this == chunkregion)
				return 0;
			else if (this.containsAll(chunkregion.chunkSet) && this.volume() != chunkregion.volume())
				return 1;
			else if (chunkregion.containsAll(this.chunkSet) && this.volume() != chunkregion.volume())
				return -1;
			else
				return 0;
		} else if (region instanceof CubeRegion) {
			CubeRegion cubeRegion = (CubeRegion) region;
			if (this.containsCubes(cubeRegion.getCubeSet()))
				return 1;
			else
				return 0;
		}		
		return 0;
	}

	@Override
	public int volume() {
		return chunkSet.size() * 8;
	}

}
