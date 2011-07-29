// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.sleaker.regional.flags;

/**
 *
 * @author sk89q
 * @param <T> 
 */
public abstract class Flag<T> {

	private final String name;

	public Flag(String name) {
		this.name = name;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Converts string input into a valid flag value for the given flag Type T.
	 * 
	 * @param input
	 * @return
	 * @throws InvalidFlagFormat
	 */
	public abstract T parseInput(String input) throws InvalidFlagFormat;

	/**
	 * Returns a Type casted variable of object o
	 * 
	 * @param o
	 * @return
	 */
	public abstract T unmarshal(Object o);

	/**
	 * Returns a generic object of the flags value ready to be inserted into 
	 * the Flag set
	 * @param o
	 * @return
	 */
	public abstract Object marshal(T o);
}
