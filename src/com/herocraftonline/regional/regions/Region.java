package com.herocraftonline.regional.regions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.regional.flags.BooleanFlag;
import com.herocraftonline.regional.flags.BuiltinFlag;
import com.herocraftonline.regional.flags.Flag;
import com.herocraftonline.regional.flags.RegionFlagSet;
import com.herocraftonline.regional.managers.UniverseRegionManager;
import com.herocraftonline.regional.managers.WorldRegionManager;

/**
 * Defines a Generic Region object
 * 
 * @author sleak
 */
public abstract class Region {

	/**
	 * Non-Unique name for the Region
	 */
	final String name;

	/**
	 * Unique id for the Region
	 */
	final int id;

	/**
	 * What world this region is a part of
	 */
	final String worldName;

	/**
	 * The associated parent region for this Region
	 */
	private int parentId = -1;

	/**
	 * Integer list of all associated children this region has
	 */
	private Set<Region> children = new HashSet<Region>();

	/**
	 * The weight that this region has. Higher weighted regions have presedence over lower weighted regions
	 */
	byte weight = 0;

	/**
	 * Namespaces define which plugins are associated with this region
	 */
	private Set<String> namespaces = new HashSet<String>();

	/**
	 * HashMap of flags for this region
	 */
	private Map<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();

	/**
	 * HashMap of standard flags for the region
	 */
	private Map<Flag<?>, Boolean> standardFlags = new HashMap<Flag<?>, Boolean>();
	
	/**
	 * This regions resolved flags - should update whenever a flag is changed on a parent region, or on this region
	 */
	private RegionFlagSet resolvedFlags;
	
	//-------------------------//
	//    Constructors
	//-------------------------//

	Region(String name, int id, String worldName, Plugin plugin) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
		this.weight = 0;
		namespaces.add(plugin.getDescription().getName());
	}

	Region(String name, int id, String worldName, byte weight, List<String> namespaces, int parentId) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
		this.weight = weight;
		this.namespaces.addAll(namespaces);
		this.parentId = parentId;
	}

	Region(String name, int id, String worldName) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
	}

	//----------------------//
	//  Basic Data Getters
	//----------------------//

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the worldName
	 */
	public String getWorldName() {
		return worldName;
	}

	//--------------------------//
	//   Flag Methods
	//--------------------------//

	/**
	 * Add a specific stateflag to the standard flag map
	 */
	public void addFlag(BuiltinFlag flag, boolean val) {
		standardFlags.put(new BooleanFlag(flag), val);
	}
	
	/**
	 * Removes a specific Stateflag from the standard flag map
	 * 
	 * @param flag
	 */
	public void removeFlag(BuiltinFlag flag) {
		standardFlags.remove(new BooleanFlag(flag));
	}
	
	/**
	 * Adds a flag of type T to the flag map
	 * 
	 * @param <T>
	 * @param flag
	 * @param obj
	 */
	public <T> void addFlag(Flag<T> flag, Object obj) {
		flags.put(flag, obj);
	}

	/**
	 * Remove a flag from the set
	 * @param <T>
	 * 
	 * @param name
	 */
	public <T> void removeFlag(Flag<T> flag) {
		flags.remove(flag.getName());
	}

	/**
	 * Returns the value for this flag
	 * 
	 * @param <T>
	 * @param <V>
	 * @param flag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Flag<V>, V> V getFlag(T flag) {
		Object obj = flags.get(flag);
		V val;
		if (obj != null) {
			val = (V) obj;
		} else {
			return null;
		}
		return val;
	}

	/**
	 * Set a flag's value.
	 * Setting a flag to a null value will instead remove that flag
	 * 
	 * @param <T>
	 * @param <V>
	 * @param flag
	 * @param val
	 */
	public <T extends Flag<V>, V> void setFlag(T flag, V val) {
		if (val == null) {
			flags.remove(flag);
		} else {
			flags.put(flag, val);
		}
	}

	/**
	 * Returns a snapshot Mapping of the flags for this region
	 * 
	 * @return
	 */
	public Map<Flag<?>, Object> getFlags() {
		return new HashMap<Flag<?>, Object>(flags);
	}

	/**
	 * Returns a snapshot set of all active flags on this region - including inheritence
	 * 
	 * @param uManager
	 * @return
	 */
	public Map<Flag<?>, Object> getInheritedFlags(UniverseRegionManager uManager) {
		if (this.parentId > -1) {
			Map<Flag<?>, Object> resolvedFlags = new HashMap<Flag<?>, Object>();
			//Dumping all parent flags into the map first allows child flags to overwrite parent flags
			resolvedFlags.putAll(uManager.getRegion(this.worldName, this.parentId).getInheritedFlags(uManager));
			resolvedFlags.putAll(flags);
			return resolvedFlags;
		}
		return flags;
	}

	/**
	 * Gets the type of this region.
	 * @return string type
	 */
	public abstract String getTypeName();

	/**
	 * Attempts to set the parent of this Region
	 * 
	 * @param parent
	 * @throws CircularInheritenceException
	 */
	public void setParent(int parentId, WorldRegionManager wrm) throws CircularInheritenceException {
		if (parentId == -1) {
			this.parentId = -1;
		} 

		int p = parentId;
		while (p != -1) {
			if (p == this.id)
				throw new CircularInheritenceException();

			p = wrm.getRegion(p).getParentId();
		}
		this.parentId = parentId;
		wrm.getRegion(parentId).addChild(this);
	}

	/**
	 * @return id of the parent region
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @return Set view of Child Regions
	 */
	public Set<Region> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	/**
	 * Add the Specified region as a child region
	 * This should only be called from the setParent method
	 * 
	 * @param region
	 */
	private void addChild(Region region) {
		if (region.getParentId() == this.id)
			children.add(region);
	}

	//--------------------------//
	//  Namespace Methods
	//--------------------------//

	/**
	 * Sets the weight of this Region
	 */
	public void setWeight(byte weight) {
		this.weight = weight;
	}

	/**
	 * Gets the weight of this Region
	 * 
	 * @return
	 */
	public byte getWeight() {
		return weight;
	}

	/**
	 * Gets the namespaces associated with this Region
	 * 
	 * @return
	 */
	public List<String> getNamespaces() {
		List<String> namespaces = new ArrayList<String>();
		namespaces.addAll(this.namespaces);
		return namespaces;
	}

	/**
	 * Tests if this region is in the specified namespace 
	 * 
	 * @param pluginName
	 * @return
	 */
	public boolean isInNamespace(String pluginName) {
		return namespaces.contains(pluginName);
	}

	/**
	 * Adds a namespace to the namespaces set
	 * 
	 * @param plugin
	 * @return
	 */
	public void addNamespace(String pluginName) {
		this.namespaces.add(pluginName);
	}

	/**
	 * Removes a namespace from the namespaces set
	 * 
	 * @param plugin
	 * @return
	 */
	public boolean removeNamespace(Plugin plugin) {
		return this.namespaces.remove(plugin.getDescription().getName());
	}

	//----------------------------//
	//  Region Test Methods
	//----------------------------//

	/**
	 * Tests if the Region contains all of the cubes
	 * 
	 * @param cubes
	 * @return true/false
	 */
	public abstract boolean containsAll(Collection<Cube> cubes);

	/**
	 * Tests if the Region contains the cube 
	 *
	 * @param cube
	 * @return true/false
	 */
	public abstract boolean containsCube(Cube cube);

	/**
	 * Tests if the Location is somewhere within this region
	 * 
	 * @param loc
	 * @return true/false
	 */
	public abstract boolean containsPoint(Location loc);

	/**
	 * Gets the total volume in Cubes.
	 * 
	 * @return int
	 */
	public abstract int volume();

	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Region))
			return false;

		return this.id == ((Region) o).id;
	}

	public int hashCode() {
		return this.id;
	}

	public static class CircularInheritenceException extends Exception {
		private static final long serialVersionUID = -3626449351263374520L;
	}
}
