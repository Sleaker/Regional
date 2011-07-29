package com.sleaker.regional.flags;

/**
 * Defines the standard flags that a region can have
 * 
 * @author sleak
 *
 */
public enum StandardFlag {
    NO_BUILD(0, "no-build"),
    NO_PVP(1, "no-pvp"),
    NO_MOB_DAMAGE(2, "no-mob-damage"),
    NO_MONSTER_SPAWNING(3, "no-monster-spawn"),
    NO_CREATURE_SPAWNING(4, "no-creature-spawn"),
    NO_CREEPER_EXPLOSION(5, "no-creeper-explosion"),
    NO_GHAST_FIREBALL(6, "no-ghast-fireball"),
    NO_SLEEP(7, "no-sleep"),
    NO_TNT_EXPLOSION(8, "no-tnt-explosion"),
    NO_LIGHTER(9, "no-lighter"),
    NO_FIRE_SPREAD(10, "no-fire-spread"),
    NO_LIGHTNING(11, "no-lightning"),
    NO_CHEST_ACCESS(12, "no-chest-access"),
    NO_WATER_FLOW(13, "no-water-flow"),
    NO_LAVA_FLOW(14, "no-lava-flow"),
    NO_USE(15, "no-use"),
    NO_MUSHROOM_FORM(16, "no-mushroom-form"),
    NO_LEAF_DECAY(17, "no-leaf-decay"),
    NOTIFY_ENTER(18, "notify-enter"),
    NOTIFY_LEAVE(19, "notify-leave"),
    INVINCIBILITY(20, "invincibility"),
    NO_ICE_MELT(21, "no-ice-melt"),
    NO_SNOW_MELT(22, "no-snow-melt"),
    NO_SNOW_FORM(23, "no-snow-form"),
    NO_ICE_FORM(24, "no-ice-form"),
    NO_WEATHER(25, "no-weather"),
    LIST_ACCESS(26, "list-access");
    
    private final String name;
    private final int id;
    private static final StandardFlag[] lookupId;
    
    StandardFlag(int id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    /**
     * Returns the enum's Name
     * This is not to be confused with the Enum's name() method
     * 
     * @return String name
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Gets this Flags id
     * @return int id
     */
    public int getId() {
    	return this.id;
    }
    
    /**
     * Returns an immutable array of all values in this enum
     * This method does not clone the values like the values() method
     * 
     * @return
     */
    public static StandardFlag[] getValues() {
    	return lookupId;
    }
    
    /**
     * Returns the enum's Name
     * This is not to be confused with the Enum's name() method
     * 
     * @param int id
     * @return String name
     */
    public static String getName(int id) {
    	return lookupId[id].name;
    }
    
    /**
     * Gets the Flag associated with the 
     * 
     * @param id
     * @return
     */
    public static StandardFlag getFlag(int id) {
    	return lookupId[id];
    }
    
    static {
    	lookupId = StandardFlag.values();
    }
}
