package com.herocraftonline.regional.flags;

/**
 * Represents a flag that holds a boolean value
 * The flag type can either be a State Flag or a Custom boolean Flag
 *
 */
public class BooleanFlag extends Flag<Boolean>{
	
    public BooleanFlag(String name) {
        super(name, FlagType.BOOLEAN);
    }

    public BooleanFlag(BuiltinFlag flag) {
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
