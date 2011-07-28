package com.sleaker.regional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.area.ChunkCube;
import com.sleaker.regional.area.Cube;

public class ChunkRegion extends Region {

	private Set<ChunkCube> chunkSet = Collections.synchronizedSet(new HashSet<ChunkCube>());

	protected ChunkRegion(String name, short id, PrivilegedList privs, Plugin plugin) {
		super(name, id, privs, plugin);
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
		return this.chunkSet.contains(new ChunkCube(loc));
	}

	public boolean containsAll(Set<?> objSet) {
		return this.chunkSet.containsAll(chunkSet);
	}

	public boolean containsCubes(Set<Cube> cubeSet) {
		for (Cube c : cubeSet)
			if (!chunkSet.contains(c))
				return false;

		return true;
	}

	@Override
	public int compare(Region o1, Region o2) {

		return 0;
	}

	@Override
	public int volume() {
		return chunkSet.size() * 8;
	}

}
