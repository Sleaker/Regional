package com.herocraftonline.regional.flags;


public class PlayerFlagSet {
	
	private boolean[] flags = new boolean[3];
	
	public PlayerFlagSet(boolean build, boolean use, boolean destroy) {
		flags[0] = build;
		flags[1] = use;
		flags[2] = destroy;
	}
	
	public boolean canBuild() {
		return flags[0];
	}
	
	public boolean canUse() {
		return flags[1];
	}
	
	public boolean canDestroy() {
		return flags[2];
	}
	
	public void setBuild(boolean val) {
		flags[0] = val;
	}
	
	public void setUse(boolean val) {
		flags[1] = val;
	}
	
	public void setDestroy(boolean val) {
		flags[2] = val;
	}
}
