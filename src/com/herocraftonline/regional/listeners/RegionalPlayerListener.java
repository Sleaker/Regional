package com.herocraftonline.regional.listeners;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.herocraftonline.regional.Regional;

public class RegionalPlayerListener extends PlayerListener {
	
	private final Regional plugin;

	public RegionalPlayerListener(Regional plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled()) 
			return;
		
		if ((int) event.getFrom().getX() >> 4 == (int) event.getTo().getX() >> 4 &&
			(int) event.getFrom().getY() >> 4 == (int) event.getTo().getY() >> 4 &&
			(int) event.getFrom().getZ() >> 4 == (int) event.getTo().getZ() >> 4)
			return;
		
		//TODO: test for entering a new region
	}
}
