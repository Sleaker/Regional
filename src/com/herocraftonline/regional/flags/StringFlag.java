package com.herocraftonline.regional.flags;

/**
 * Represents a flag that holds a string value
 * @author sleak
 *
 */
public class StringFlag extends Flag<String> {

    public StringFlag(String name) {
        super(name, FlagType.STRING);
    }

    public StringFlag(BuiltinFlag flag) {
    	super(flag.getName(), FlagType.STRING);
    }
    
    @Override
    public String objectToType(Object o) {
        if (o instanceof String) {
            return (String) o;
        } else {
            return null;
        }
    }
}
