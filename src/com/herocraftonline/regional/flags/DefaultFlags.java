package com.herocraftonline.regional.flags;

import java.util.HashMap;
import java.util.Map;

public class DefaultFlags {
	
	/**
	 * HashMap of flags
	 */
	public static final Map<Flag<?>, Object> flags;
	
	static {
		flags = new HashMap<Flag<?>, Object>();
		
		//Load default StateFlags
		flags.put(new BooleanFlag(BuiltinFlag.DENY_BUILD), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_CHEST_ACCESS), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_CREATURE_SPAWNING), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_CREEPER_EXPLOSION), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_FIRE_SPREAD), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_GHAST_FIREBALL), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_ICE_FORM), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_ICE_MELT), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_LAVA_FLOW), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_LEAF_DECAY), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_LIGHTER), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_LIGHTNING), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_MOB_DAMAGE), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_MONSTER_SPAWNING), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_MUSHROOM_FORM), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_PVP), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_SLEEP), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_SNOW_FORM), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_SNOW_MELT), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_TNT_EXPLOSION), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_USE), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_WATER_FLOW), false);
		flags.put(new BooleanFlag(BuiltinFlag.DENY_WEATHER), false);
		flags.put(new BooleanFlag(BuiltinFlag.INVINCIBILITY), false);
		flags.put(new BooleanFlag(BuiltinFlag.NOTIFY_ENTER), true);
		flags.put(new BooleanFlag(BuiltinFlag.NOTIFY_LEAVE), true);
		flags.put(new BooleanFlag(BuiltinFlag.LIST_ACCESS), true);
	}
}
