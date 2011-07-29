package com.sleaker.regional.regions;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.flags.Flag;
import com.sleaker.regional.flags.StandardFlag;

/**
 * Defines a Region object
 * 
 * @author sleak
 */
public abstract class Region implements Comparable<Region> {

	/**
	 * Non-Unique name for the Region
	 */
	public final String name;

	/**
	 * Unique id for the Region
	 */
	public final short id;

	/**
	 * Associated Priviledge Access list for this region
	 */
	private PrivilegedList privs;

	/**
	 * Namespaces define which plugins are associated with this region
	 */
	private Set<String> namespaces;

	/**
	 * EnumSet of standard flags that this region contains.
	 */
	private Set<StandardFlag> standardFlags = Collections.synchronizedSet(EnumSet.noneOf(StandardFlag.class));

	/**
	 * HashMap of custom flags for this region
	 */
	private Map<Flag<?>, Object> customFlags = Collections.synchronizedMap(new HashMap<Flag<?>, Object>());


	protected Region(String name, short id, PrivilegedList privs, Plugin plugin) {
		this.name = name;
		this.id = id;
		this.privs = privs;
		namespaces.add(plugin.getDescription().getName());
	}

	protected Region(String name, short id, Plugin plugin) {
		this(name, id, null, plugin);
	}

	//--------------------------//
	//   Flag Methods
	//--------------------------//

	/**
	 * Add a flag to the Region
	 * Flags added to the set are active (true)
	 * 
	 */
	public void addFlag(StandardFlag flag) {
		synchronized (standardFlags) {
			standardFlags.add(flag);
		}
	}

	/**
	 * Remove a flag from the set
	 * Flags not contained in the set are 'inactive' (false)
	 * 
	 * @param flag
	 */
	public void removeFlag(StandardFlag flag) {
		synchronized(standardFlags) {
			standardFlags.remove(flag);
		}
	}

	/**
	 * Remove a custom flag from the set
	 * @param <T>
	 * 
	 * @param name
	 */
	public <T> void removeFlag(T flag) {
		synchronized(customFlags) {
			customFlags.remove(name);
		}
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
		Object obj;
		synchronized(customFlags) {
			obj = customFlags.get(flag);
		}
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
		synchronized(customFlags) {
			if (val == null) {
				customFlags.remove(flag);
			} else {
				customFlags.put(flag, val);
			}
		}
	}

	/**
	 * Returns the customFlags map.  Any access done on this map must be synchronized.
	 * @return
	 */
	public Map<Flag<?>, Object> getCustomFlags() {
		synchronized(customFlags) {
			return customFlags;
		}
	}

	/**
	 * Returns a snapshop Set of all active flags on this region
	 * 
	 * @return
	 */
	public Set<StandardFlag> getFlags() {
		synchronized(standardFlags) {
			return EnumSet.copyOf(standardFlags);
		}
	}

	//--------------------------//
	//  Namespace Methods
	//--------------------------//
	
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
	 * Tests if the object is contained within the region
	 * 
	 * @param cube
	 * @return
	 */
	public abstract boolean contains(Object obj);


	/**
	 * Tests if the Location is somewhere within this region
	 * 
	 * @param loc
	 * @return
	 */
	public abstract boolean containsPoint(Location loc);

	/**
	 * Gets the total volume in Cubes.
	 * 
	 * @return
	 */
	public abstract int volume();

	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (!(o instanceof Region))
			return false;

		return this.id == ((Region) o).id;
	}

	@Override
	public abstract int compareTo(Region region);

	public int hashCode() {
		return this.id;
	}
}
