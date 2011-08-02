package com.sleaker.regional.flags;

/**
 *
 * @param <T> 
 */
public abstract class Flag<T> {

	private final String name;

	private final FlagType type;
	
	public Flag(String name, FlagType type) {
		this.name = name;
		this.type = type;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * Gets the flags name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the name of this flag type
	 * 
	 * @return
	 */
	public String getTypeName() {
		return this.type.getName();
	}

	/**
	 * Returns a Type casted variable of object o
	 * 
	 * @param o
	 * @return
	 */
	public abstract T objectToType(Object o);

}
