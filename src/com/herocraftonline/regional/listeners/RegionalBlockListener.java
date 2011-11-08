package com.herocraftonline.regional.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;

import com.herocraftonline.regional.Regional;
import com.herocraftonline.regional.flags.BuiltinFlag;
import com.herocraftonline.regional.flags.RegionFlagSet;
import com.herocraftonline.regional.managers.UniverseRegionManager;
import com.herocraftonline.regional.regions.Cube;

public class RegionalBlockListener extends BlockListener {

	private UniverseRegionManager uManager;

	public RegionalBlockListener(Regional plugin) {
		uManager = plugin.getUniverseRegionManager();
	}

	@Override
	public void onBlockFromTo(BlockFromToEvent event) {
		if (event.isCancelled())
			return;

		RegionFlagSet rfs = getRFS(event.getBlock());
		Material m = event.getToBlock().getType();
		if (m == Material.LAVA || m == Material.STATIONARY_LAVA)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_LAVA_FLOW));
		else if (m == Material.STATIONARY_WATER || m == Material.WATER)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_WATER_FLOW));
		else if (m == Material.AIR) {

		}
	}

	@Override
	public void onBlockBurn(BlockBurnEvent event) {
		if (event.isCancelled())
			return;

		event.setCancelled(getRFS(event.getBlock()).getFlag(BuiltinFlag.DENY_FIRE_SPREAD));
	}

	@Override
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (event.isCancelled())
			return;

		RegionFlagSet rfs = getRFS(event.getBlock());
		IgniteCause ic = event.getCause();

		if (ic == IgniteCause.SPREAD || ic == IgniteCause.LAVA || ic == IgniteCause.LIGHTNING)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_FIRE_SPREAD));
		else if (ic == IgniteCause.FLINT_AND_STEEL)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_LIGHTER));
	}

	@Override
	public void onBlockForm(BlockFormEvent event) {
		if (event.isCancelled())
			return;

		RegionFlagSet rfs = getRFS(event.getBlock());
		Material m = event.getNewState().getType();
		if (m == Material.ICE)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_ICE_FORM));
		else if (m == Material.SNOW)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_SNOW_FORM));
	}

	@Override
	public void onBlockFade(BlockFadeEvent event) {
		if (event.isCancelled())
			return;

		RegionFlagSet rfs = getRFS(event.getBlock());
		Material m = event.getBlock().getType();
		if (m == Material.ICE)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_ICE_MELT));
		else if (m == Material.SNOW)
			event.setCancelled(rfs.getFlag(BuiltinFlag.DENY_SNOW_MELT));
	}

	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		if (event.isCancelled())
			return;

		event.setCancelled(getRFS(event.getBlock()).getFlag(BuiltinFlag.DENY_LEAF_DECAY));
	}

	@Override
	public void onBlockSpread(BlockSpreadEvent event) {
		if (event.isCancelled())
			return;

		Material m = event.getSource().getType();
		if (m == Material.BROWN_MUSHROOM || m == Material.RED_MUSHROOM)
			event.setCancelled(getRFS(event.getBlock()).getFlag(BuiltinFlag.DENY_MUSHROOM_FORM));
	}

	/**
	 * Returns a region flag set at the block given
	 * @param block
	 * @return RegionFlagSet for the location
	 */
	private RegionFlagSet getRFS(Block block) {
		Cube cube = new Cube(block.getLocation());
		return new RegionFlagSet(uManager.getRegions(cube), uManager.getWorldRegionManager(cube.worldName).getWorldRegion());
	}
}
