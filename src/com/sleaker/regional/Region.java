package com.sleaker.regional;

import java.util.Comparator;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dthielke.lists.PrivilegedList;

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
		
	protected Region(String name, short id, PrivilegedList privs, Plugin plugin) {
		this.name = name;
		this.id = id;
		this.privs = privs;
		namespaces.add(plugin.getDescription().getName());
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
