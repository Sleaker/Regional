package com.sleaker.regional;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Regional extends JavaPlugin {

	private String plugName;
	private Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable() {
		log.info(plugName + " disabled");
	}

	@Override
	public void onEnable() {
		plugName = "[" + this.getDescription().getName() + "]";
		
		log.info(plugName + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors() + " enabled!");
	}

}
