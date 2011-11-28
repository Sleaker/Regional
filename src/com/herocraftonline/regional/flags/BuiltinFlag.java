package com.herocraftonline.regional.flags;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the standard flags that a region can have
 * 
 */
public enum BuiltinFlag {
    DENY_BUILD(0, "no-build"),
    DENY_PVP(1, "no-pvp"),
    DENY_MOB_DAMAGE(2, "no-mob-damage"),
    DENY_MONSTER_SPAWNING(3, "no-monsters"),
    DENY_CREATURE_SPAWNING(4, "no-creatures"),
    DENY_CREEPER_EXPLOSION(5, "no-creeper-explosion"),
    DENY_GHAST_FIREBALL(6, "no-ghast-fireball"),
    DENY_SLEEP(7, "no-sleep"),
    DENY_TNT_EXPLOSION(8, "no-tnt-explosion"),
    DENY_LIGHTER(9, "no-lighter"),
    DENY_FIRE_SPREAD(10, "no-fire-spread"),
    DENY_LIGHTNING(11, "no-lightning"),
    DENY_CHEST_ACCESS(12, "no-chest-access"),
    DENY_WATER_FLOW(13, "no-water-flow"),
    DENY_LAVA_FLOW(14, "no-lava-flow"),
    DENY_USE(15, "no-use"),
    DENY_MUSHROOM_FORM(16, "no-mushrooms"),
    DENY_LEAF_DECAY(17, "no-leaf-decay"),
    NOTIFY_ENTER(18, "notify-enter"),
    NOTIFY_LEAVE(19, "notify-leave"),
    INVINCIBILITY(20, "invincibility"),
    DENY_ICE_MELT(21, "no-ice-melt"),
    DENY_SNOW_MELT(22, "no-snow-melt"),
    DENY_SNOW_FORM(23, "no-snow-form"),
    DENY_ICE_FORM(24, "no-ice-form"),
    DENY_WEATHER(25, "no-weather"),
    DENY_ENDERMAN_BUILD(26, "no-enderman-blocks"),
    DENY_NPC_SPAWN(27, "no-villager-spawn"),
    PASSTHROUGH(28, "pass-through"),
    ENTER_TEXT(29, "enter-text"),
    LEAVE_TEXT(30, "leave-text");
    
    private final String name;
    private final int id;
    private static final BuiltinFlag[] lookupId;
    private static Map<String, BuiltinFlag> nameMap = new HashMap<String, BuiltinFlag>();
    
    BuiltinFlag(int id, String name) {
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
    public static BuiltinFlag[] getValues() {
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
     * Gets the Stateflag associated with the name
     * 
     * @param name
     * @return
     */
    public static BuiltinFlag getFlag(String name) {
    	return nameMap.get(name);
    }
    
    /**
     * Gets the Flag associated with the 
     * 
     * @param id
     * @return
     */
    public static BuiltinFlag getFlag(int id) {
    	return lookupId[id];
    }
    
    static {
    	lookupId = BuiltinFlag.values();
    	
    	for (BuiltinFlag flag : values()) {
    		nameMap.put(flag.getName(), flag);
    	}
    }
}
