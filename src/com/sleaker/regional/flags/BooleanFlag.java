package com.sleaker.regional.flags;

public class BooleanFlag extends Flag<Boolean>{

	
    public BooleanFlag(String name) {
        super(name, FlagType.BOOLEAN);
    }

    @Override
    public Boolean parse(String input) throws InvalidFlagFormat {
        input = input.trim();
        
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes")
                || input.equalsIgnoreCase("on")
                || input.equalsIgnoreCase("1")) {
            return true;
        } else if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no")
                || input.equalsIgnoreCase("off")
                || input.equalsIgnoreCase("0")) {
            return false;
        } else {
            throw new InvalidFlagFormat("Not a yes/no value: " + input);
        }
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
