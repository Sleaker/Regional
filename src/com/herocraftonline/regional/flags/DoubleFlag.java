package com.herocraftonline.regional.flags;

/**
 * Represents a flag that stores a Double
 * @author sleak
 *
 */
public class DoubleFlag extends Flag<Double> {
    
    public DoubleFlag(String name) {
        super(name, FlagType.DOUBLE);
    }

    @Override
    public Double objectToType(Object o) {
        if (o instanceof Double) {
            return (Double) o;
        } else {
            return null;
        }
    }
}
