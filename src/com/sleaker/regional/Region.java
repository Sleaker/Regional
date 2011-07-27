package com.sleaker.regional;

import java.util.Comparator;

import org.bukkit.Location;

import com.herocraftonline.dthielke.lists.PrivilegedList;

/**
 * Defines a Region object
 * 
 * @author sleak
 */
public abstract class Region implements Comparator<Region> {

	/**
	 * Represents a Non-Unique name for the Region
	 */
	public final String name;

	/**
	 * Represents a Unique id for the Region
	 */
	public final short id;

	private PrivilegedList privs;
		
	protected Region(String name, short id, PrivilegedList privs) {
		this.name = name;
		this.id = id;
		this.privs = privs;
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
