package com.herocraftonline.regional.flags;

import java.util.Map;

public class ResolvedFlags {

	/**
	 * HashMap of custom flags for this region
	 */
	public final Map<Flag<?>, Object> customFlags;
	public final Map<Flag<?>, Object> standardFlags;
	
	public ResolvedFlags(Map<Flag<?>, Object> customFlags, Map<Flag<?>, Object> standardFlags) {
		this.customFlags = customFlags;
		this.standardFlags = standardFlags;
	}
	
	
}
