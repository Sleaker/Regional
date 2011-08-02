package com.sleaker.regional.flags;

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
