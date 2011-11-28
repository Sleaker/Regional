package com.herocraftonline.regional.flags;

/**
 * Represents a type of flag
 *
 */
public enum FlagType {
	BOOLEAN("boolean"),
	DOUBLE("double"),
	INTEGER("integer"),
	STRING("string"),
	STATE("state");
	
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
