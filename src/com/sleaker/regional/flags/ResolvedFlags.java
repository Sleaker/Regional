package com.sleaker.regional.flags;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.herocraftonline.dthielke.lists.PrivilegedList;

public class ResolvedFlags {
	
	/**
	 * Associated Privilege Access list for the intersecting regions
	 */
	public final PrivilegedList privs;
	
	/**
	 * EnumSet of standard flags that this region contains.
	 * Any flags in the Set are Enabled
	 */
	public final Set<StateFlag> standardFlags;

	/**
	 * HashMap of custom flags for this region
	 */
	public final Map<Flag<?>, Object> customFlags;
	
	public ResolvedFlags(Set<StateFlag> standardFlags, HashMap<Flag<?>, Object> customFlags, PrivilegedList privs) {
		this.standardFlags = standardFlags;
		this.customFlags = customFlags;
		this.privs = privs;
	}
	
}
