package com.sleaker.regional.flags;

public enum EnumFlag {
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
    NO_SNOW_FALL(16, "no-snow-fall"),
    NO_LEAF_DECAY(17, "no-leaf-decay"),
    NOTIFY_ENTER(18, "notify-enter"),
    NOTIFY_LEAVE(19, "notify-leave"),
    INVINCIBILITY(20, "invincibility");
    
    private final String name;
    private final int id;
    private static final EnumFlag[] lookupId;
    
    EnumFlag(int id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    /**
     * Returns the enum's Name
     * This is not to be confused with the Enum's name() method
     * 
     * @param e
     * @return String name
     */
    public String getName(EnumFlag e) {
    	return e.name;
    }
    public String getName(int id) {
    	return lookupId[id].name;
    }
    
    /**
     * Gets this Flags id
     * @param e
     * @return int id
     */
    public int getId(EnumFlag e) {
    	return e.id;
    }
    
    /**
     * Gets the Flag associated with the 
     * 
     * @param id
     * @return
     */
    public static EnumFlag getFlag(int id) {
    	return lookupId[id];
    }
    
    static {
    	lookupId = EnumFlag.values();
    }
}
