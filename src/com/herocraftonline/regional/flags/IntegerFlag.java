package com.herocraftonline.regional.flags;

/**
 * Represents a flag that holds an integer value
 *
 */
public class IntegerFlag extends Flag<Integer> {

    public IntegerFlag(String name) {
        super(name, FlagType.INTEGER);
    }

    @Override
    public Integer objectToType(Object o) {
        if (o instanceof Integer) {
            return (Integer) o;
        } else {
            return null;
        }
    }
}
