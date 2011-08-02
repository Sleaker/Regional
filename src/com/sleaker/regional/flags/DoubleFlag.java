package com.sleaker.regional.flags;

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
