package com.sleaker.regional.flags;

public class BooleanFlag extends Flag<Boolean>{

	
    public BooleanFlag(String name) {
        super(name, FlagType.BOOLEAN);
    }

    public BooleanFlag(StateFlag flag) {
    	super(flag.getName(), FlagType.STATE);
    }
    
    public BooleanFlag(String name, FlagType type) {
    	super(name, type);
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
