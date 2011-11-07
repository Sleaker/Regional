package com.herocraftonline.regional.flags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.herocraftonline.regional.regions.CubeRegion;
import com.herocraftonline.regional.regions.Region;
import com.herocraftonline.regional.regions.WorldRegion;

/**
 * Holds the data for resolving the flags currently at work on a region - this is used for 
 * determining a flag-value at any given point in-game
 * @author sleak
 *
 */
public class RegionFlagSet implements Iterable<Region> {

	final List<Region> regions;
	final WorldRegion wRegion;

	public RegionFlagSet(Set<CubeRegion> regions, WorldRegion wRegion) {
		this.regions = new ArrayList<Region>(regions);
		Collections.sort(this.regions);
		this.wRegion = wRegion;
	}

	public void addRegion(CubeRegion cRegion) {
		regions.add(cRegion);
	}

	public void removeRegion(CubeRegion cRegion) {
		regions.remove(cRegion);
	}

	@Override
	public Iterator<Region> iterator() {
		return regions.iterator();
	}

	public <T> Object getFlag(Flag<T> flag) {
		Object o = null;

		Object def = DefaultFlags.getInstance().get(flag);
		if (wRegion.getFlags().containsKey(flag))
			def = wRegion.getFlags().get(flag);
		
		Iterator<Region> iter = regions.iterator();
		if (flag instanceof BooleanFlag) {
			int lastWeight = Integer.MIN_VALUE;
			boolean defined = false;

			while(iter.hasNext()) {
				Region region = iter.next();

				if (defined && region.getWeight() < lastWeight)
					break;

				lastWeight = region.getWeight();
				
				Boolean val = (Boolean) region.getFlag(flag);
				if (val == false)
					return false;
				else if (val == null)
					continue;
				else
					o = true;
			} 
		} else {
			while (iter.hasNext()) {
				Region region = iter.next();
				
				if (region.hasFlag(flag))
					o = region.getFlag(flag);
			}
		}

		return o == null ? def : o;
	}
}
