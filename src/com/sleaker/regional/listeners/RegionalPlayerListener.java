package com.sleaker.regional.listeners;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegionalPlayerListener extends PlayerListener {

	public RegionalPlayerListener() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.event.player.PlayerListener#onPlayerMove(org.bukkit.event.player.PlayerMoveEvent)
	 */
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
