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
 */
public class StringFlag extends Flag<String> {

    public StringFlag(String name) {
        super(name);
    }

    @Override
    public String parseInput(String input) throws InvalidFlagFormat {
        return input;
    }

    @Override
    public String unmarshal(Object o) {
        if (o instanceof String) {
            return (String) o;
        } else {
            return null;
        }
    }

    @Override
    public Object marshal(String o) {
        return o;
    }
    
}