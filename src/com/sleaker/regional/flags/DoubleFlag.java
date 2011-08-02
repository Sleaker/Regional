package com.sleaker.regional.flags;

public class DoubleFlag extends Flag<Double> {
    
    public DoubleFlag(String name) {
        super(name, FlagType.DOUBLE);
    }

    @Override
    public Double parse(String input) throws InvalidFlagFormat {
        input = input.trim();
        
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InvalidFlagFormat("Not a number: " + input);
        }
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
