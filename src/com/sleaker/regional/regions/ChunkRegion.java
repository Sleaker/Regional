package com.sleaker.regional.regions;

import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.Regional;

public class ChunkRegion extends CubeRegion {

	protected ChunkRegion(String name, short id, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, id, worldName, privs, plugin);
	}

	public ChunkRegion(String name, String worldName, PrivilegedList privs, Plugin plugin) {
		super(name, Regional.getNextId(), worldName, privs, plugin);
	}
	
	@Override
	public String getTypeName() {
		return "chunk";
	}
}
