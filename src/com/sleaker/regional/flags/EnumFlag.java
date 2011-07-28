package com.sleaker.regional.flags;

public enum EnumFlag {
    NO_BUILD("no-build"),
    NO_PVP("no-pvp"),
    NO_MOB_DAMAGE("no-mob-damage"),
    NO_MONSTER_SPAWNING("no-monster-spawn"),
    NO_CREATURE_SPAWNING("no-creature-spawn"),
    NO_CREEPER_EXPLOSION("no-creeper-explosion"),
    NO_GHAST_FIREBALL("no-ghast-fireball"),
    NO_SLEEP("no-sleep"),
    NO_TNT_EXPLOSION("no-tnt-explosion"),
    NO_LIGHTER("no-lighter"),
    NO_FIRE_SPREAD("no-fire-spread"),
    NO_LIGHTNING("no-lightning"),
    NO_CHEST_ACCESS("no-chest-access"),
    NO_WATER_FLOW("no-water-flow"),
    NO_LAVA_FLOW("no-lava-flow"),
    NO_USE("no-use"),
    NO_SNOW_FALL("no-snow-fall"),
    NO_LEAF_DECAY("no-leaf-decay"),
    NOTIFY_ENTER("notify-enter"),
    NOTIFY_LEAVE("notify-leave"),
    INVINCIBILITY("invincibility");
    
    public final String name;
    
    EnumFlag(String name) {
    	this.name = name;
    }
    
}
