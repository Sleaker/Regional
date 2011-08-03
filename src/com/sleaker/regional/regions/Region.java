package com.sleaker.regional.regions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.flags.Flag;
import com.sleaker.regional.flags.StateFlag;
import com.sleaker.regional.managers.WorldRegionManager;

/**
 * Defines a Generic Region object
 * 
 * @author sleak
 */
public abstract class Region implements Comparable<Region> {

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
	 * Associated Privilege Access list for this region
	 */
	private PrivilegedList privs;

	/**
	 * The associated parent region for this Region
	 */
	private int parentId = -1;

	/**
	 * The weight that this region has. Higher weighted regions have presedence over lower weighted regions
	 */
	byte weight = 0;
	
	/**
	 * Namespaces define which plugins are associated with this region
	 */
	private Set<String> namespaces;

	/**
	 * EnumSet of standard flags that this region contains.
	 * Any flags in the Set are Enabled
	 */
	private Set<StateFlag> standardFlags = EnumSet.noneOf(StateFlag.class);

	/**
	 * HashMap of custom flags for this region
	 */
	private Map<Flag<?>, Object> customFlags = new HashMap<Flag<?>, Object>();


	//-------------------------//
	//    Constructors
	//-------------------------//
	
	Region(String name, int id, String worldName, PrivilegedList privs, Plugin plugin) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
		this.privs = privs;
		this.weight = 0;
		namespaces.add(plugin.getDescription().getName());
	}

	Region(String name, int id, String worldName, Plugin plugin) {
		this(name, id, worldName, null, plugin);
	}
	
	Region(String name, int id, String worldName, PrivilegedList privs, byte weight, List<String> namespaces, int parentId) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
		this.privs = privs;
		this.weight = weight;
		this.namespaces.addAll(namespaces);
		this.parentId = parentId;
	}
	
	Region(String name, int id, String worldName, PrivilegedList privs) {
		this.name = name;
		this.id = id;
		this.worldName = worldName;
		this.privs = privs;
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
	 * Adds all flags from another collection
	 * 
	 */
	public void addFlags(Collection<StateFlag> flags) {
		standardFlags.addAll(flags);
	}
	
	/**
	 * Adds a custom flag of type T to the custom flag map
	 * 
	 * @param <T>
	 * @param flag
	 * @param obj
	 */
	public <T> void addFlag(Flag<T> flag, Object obj) {
		customFlags.put(flag, obj);
	}
	
	/**
	 * Add a flag to the Region
	 * Flags added to the set are active (true)
	 * 
	 */
	public void addFlag(StateFlag flag) {
		standardFlags.add(flag);
	}

	/**
	 * Remove a flag from the set
	 * Flags not contained in the set are 'inactive' (false)
	 * 
	 * @param flag
	 */
	public void removeFlag(StateFlag flag) {
		standardFlags.remove(flag);
	}

	/**
	 * Remove a custom flag from the set
	 * @param <T>
	 * 
	 * @param name
	 */
	public <T> void removeFlag(Flag<T> flag) {
		customFlags.remove(flag.getName());
	}

	/**
	 * Returns the value for this custom flag
	 * 
	 * @param <T>
	 * @param <V>
	 * @param flag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Flag<V>, V> V getCustomFlag(T flag) {
		Object obj = customFlags.get(flag);
		V val;
		if (obj != null) {
			val = (V) obj;
		} else {
			return null;
		}
		return val;
	}
	
	/**
	 * Set a custom flag's value.
	 * Setting a flag to a null value will instead remove that flag
	 * 
	 * @param <T>
	 * @param <V>
	 * @param flag
	 * @param val
	 */
	public <T extends Flag<V>, V> void setFlag(T flag, V val) {
		if (val == null) {
			customFlags.remove(flag);
		} else {
			customFlags.put(flag, val);
		}
	}
	
	/**
	 * Returns the customFlags map.
	 * @return
	 */
	public Map<Flag<?>, Object> getCustomFlags() {
		return customFlags;
	}

	/**
	 * Returns a snapshop Set of all active flags on this region
	 * 
	 * @return
	 */
	public Set<StateFlag> getFlags() {
		return EnumSet.copyOf(standardFlags);
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
	}

	public int getParentId() {
		return parentId;
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
	public boolean addNamespace(Plugin plugin) {
		return this.namespaces.add(plugin.getDescription().getName());
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

	//--------------------//
	//  Privilege Methods
	//--------------------//
	/**
	 * gets the Priviliged user list associated with the region
	 * 
	 * @return
	 */
	public PrivilegedList getPrivs() {
		return this.privs;
	}

	/**
	 * Sets the privilegedlist for this region
	 * 
	 * @param privs
	 */
	public void setPrivs(PrivilegedList privs) {
		this.privs = privs;
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

	/**
	 * Identical to WGs priority weighting
	 * @author sk89q
	 */
	@Override
	public int compareTo(Region region) {
		if (this.id == region.id)
			return 0;
		else if (this.weight == region.weight)
			return 1;
		else if (this.weight > region.weight)
			return -1;
		else
			return 1;
	}

	public int hashCode() {
		return this.id;
	}
	
	public static class CircularInheritenceException extends Exception {
		private static final long serialVersionUID = -3626449351263374520L;
	}
}
