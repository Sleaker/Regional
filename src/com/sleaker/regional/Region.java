package com.sleaker.regional;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;
import com.sleaker.regional.flags.EnumFlag;

/**
 * Defines a Region object
 * 
 * @author sleak
 */
public abstract class Region implements Comparator<Region> {

	/**
	 * Non-Unique name for the Region
	 */
	public final String name;

	/**
	 * Unique id for the Region
	 */
	public final short id;

	/**
	 * Associated Priveledge Access list for this region
	 */
	private PrivilegedList privs;
	
	/**
	 * Namespaces define which plugins are associated with this region
	 */
	private Set<String> namespaces;
	
	/**
	 * EnumSet of standard flags that this region contains.
	 */
	private Set<EnumFlag> standardFlags = Collections.synchronizedSet(EnumSet.noneOf(EnumFlag.class));
	
	/**
	 * HashMap of custom flags for this region
	 */
	private Map<String, String> customFlags = Collections.synchronizedMap(new HashMap<String, String>());
	
	protected Region(String name, short id, PrivilegedList privs, Plugin plugin) {
		this.name = name;
		this.id = id;
		this.privs = privs;
		namespaces.add(plugin.getDescription().getName());
	}
	
	/**
	 * Add a flag to the Region
	 * Flags added to the set are active (true)
	 * 
	 */
	public void addFlag(EnumFlag flag) {
		standardFlags.add(flag);
	}
	/**
	 * Adds a string flag to the region
	 * 
	 * @param name
	 * @param value
	 */
	public void addFlag(String name, String value) {
		customFlags.put(name, value);
	}
	
	/**
	 * Remove a flag from the set
	 * Flags not contained in the set are 'inactive' (false)
	 * 
	 * @param flag
	 */
	public void removeFlag(EnumFlag flag) {
		standardFlags.remove(flag);
	}
	

	/**
	 * Returns the Set of all active flags on this region
	 * 
	 * @return
	 */
	public Set<EnumFlag> getFlags() {
		return this.standardFlags;
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
	public abstract int compare(Region o1, Region o2);
	
	public int hashCode() {
		return this.id;
	}
}
