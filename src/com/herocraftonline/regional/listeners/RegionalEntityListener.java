package com.herocraftonline.regional.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.flags.BuiltinFlag;
import com.herocraftonline.regional.managers.UniverseRegionManager;
import com.herocraftonline.regional.managers.WorldRegionManager;
import com.herocraftonline.regional.regions.Cube;
import com.herocraftonline.regional.regions.Region;

public class RegionalEntityListener extends EntityListener {

	private final Regional plugin;
	private UniverseRegionManager uManager;

	public RegionalEntityListener(Regional plugin) {
		this.plugin = plugin;
		uManager = plugin.getUniverseRegionManager();
	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		Cube cube = new Cube(event.getEntity().getLocation());
		if (event instanceof EntityDamageByEntityEvent) {
			if (onEntityDamageEntity((EntityDamageByEntityEvent) event, cube)) {
				event.setCancelled(true);
				return;
			}
		}
	}

	public boolean onEntityDamageEntity(EntityDamageByEntityEvent event, Cube cube) {
		WorldRegionManager wManager = uManager.getWorldRegionManager(cube.worldName);
		List<Region> regions = uManager.getRegions(cube);
		if (regions.isEmpty()) {
			if (!(event.getDamager() instanceof Player) && (!(event.getDamager() instanceof Projectile) || !(((Projectile) event.getDamager()).getShooter() instanceof Player) ))
				return wManager.getWorldRegion().getFlag(BuiltinFlag.DENY_MOB_DAMAGE);
			if (event.getEntity() instanceof Player && (event.getDamager() instanceof Player || 
					(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player)))
				return wManager.getWorldRegion().getFlag(BuiltinFlag.DENY_PVP);
		}
		return false;
	}
	
}
