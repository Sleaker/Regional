package com.sleaker.regional.flags;

public enum FlagType {
	BOOLEAN("boolean"),
	DOUBLE("double"),
	INTEGER("integer"),
	STRING("string");
	
	private final String name;
	
	FlagType(String name) {
		this.name = name;
	}
	
	/**
	 * Get the type name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}
