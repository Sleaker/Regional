package com.herocraftonline.regional.listeners;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EndermanPickupEvent;
import org.bukkit.event.entity.EndermanPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.flags.BuiltinFlag;
import com.herocraftonline.regional.flags.RegionFlagSet;
import com.herocraftonline.regional.managers.UniverseRegionManager;
import com.herocraftonline.regional.regions.Cube;

public class RegionalEntityListener extends EntityListener {

	private UniverseRegionManager uManager;

	public RegionalEntityListener(Regional plugin) {
		uManager = plugin.getUniverseRegionManager();
	}

	@Override
	public void onEndermanPickup(EndermanPickupEvent event) {
		if (event.isCancelled())
			return;

		event.setCancelled(onEnderAction(event));
	}

	@Override
	public void onEndermanPlace(EndermanPlaceEvent event) {
		if (event.isCancelled())
			return;

		event.setCancelled(onEnderAction(event));
	}

	/**
	 * Tests if endermen are allowed to build and destroy in this regionset
	 * @param event
	 * @return
	 */
	private boolean onEnderAction(EntityEvent event) {
		Cube cube = new Cube(event.getEntity().getLocation());
		RegionFlagSet rfs = new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());
		return rfs.getFlag(BuiltinFlag.DENY_ENDERMAN_BUILD);
	}

	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled())
			return;

		Cube cube = new Cube(event.getEntity().getLocation());
		RegionFlagSet rfs = new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());
		if (event.getEntity() instanceof Monster && rfs.getFlag(BuiltinFlag.DENY_MONSTER_SPAWNING)) {
			event.setCancelled(true);
		} else if (event.getEntity() instanceof Creature && rfs.getFlag(BuiltinFlag.DENY_CREATURE_SPAWNING)) {
			event.setCancelled(true);
		}

	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		Cube cube = new Cube(event.getEntity().getLocation());
		RegionFlagSet rfs = new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());

		if (rfs.getFlag(BuiltinFlag.INVINCIBILITY)) {
			event.setCancelled(true);
			return;
		}
			
		if (event instanceof EntityDamageByEntityEvent) {
			if (onEntityDamageEntity((EntityDamageByEntityEvent) event, rfs)) {
				event.setCancelled(true);
				return;
			}
		}
	}

	public boolean onEntityDamageEntity(EntityDamageByEntityEvent event, RegionFlagSet rfs) {

		if (!(event.getDamager() instanceof Player) && (!(event.getDamager() instanceof Projectile) || !(((Projectile) event.getDamager()).getShooter() instanceof Player) ))
			return rfs.getFlag(BuiltinFlag.DENY_MOB_DAMAGE);
		else if (event.getEntity() instanceof Player && (event.getDamager() instanceof Player || 
				(event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player)))
			return rfs.getFlag(BuiltinFlag.DENY_PVP);

		return false;
	}

	@Override
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.isCancelled())
			return;

		Cube cube = new Cube(event.getEntity().getLocation());
		RegionFlagSet rfs = new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());
		if (event.getEntity() instanceof Creeper)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_CREEPER_EXPLOSION));
		else if (event.getEntity() instanceof Fireball) {
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_GHAST_FIREBALL));
		} else if (event.getEntity() instanceof TNTPrimed)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_TNT_EXPLOSION));
	}
}
