package com.sleaker.regional.flags;

public class StringFlag extends Flag<String> {

    public StringFlag(String name) {
        super(name, FlagType.STRING);
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
