package com.herocraftonline.regional.flags;


public class PlayerFlagSet {
	
	private boolean[] flags = new boolean[3];
	
	public PlayerFlagSet(boolean build, boolean use, boolean move) {
		flags[0] = build;
		flags[1] = use;
		flags[2] = move;
	}
	
	public PlayerFlagSet(PlayerFlagSet pfs) {
		flags[0] = pfs.canBuild();
		flags[1] = pfs.canUse();
		flags[2] = pfs.canMove();
	}
	
	public boolean canBuild() {
		return flags[0];
	}
	
	public boolean canUse() {
		return flags[1];
	}
	
	public boolean canMove() {
		return flags[2];
	}
	
	public void setBuild(boolean val) {
		flags[0] = val;
	}
	
	public void setUse(boolean val) {
		flags[1] = val;
	}
	
	public void setMove(boolean val) {
		flags[2] = val;
	}
}
