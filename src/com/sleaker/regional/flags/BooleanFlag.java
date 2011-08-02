package com.sleaker.regional.flags;

public class BooleanFlag extends Flag<Boolean>{

	
    public BooleanFlag(String name) {
        super(name, FlagType.BOOLEAN);
    }

    @Override
    public Boolean objectToType(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else {
            return null;
        }
    }
}
