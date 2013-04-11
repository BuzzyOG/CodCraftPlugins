package com.codcraft.perks.scavenger;

import com.codcraft.perks.Perks;

public class Scavenger {

	@SuppressWarnings("unused")
	private Perks plugin;

	public Scavenger(Perks plugin) {
		this.plugin =plugin;
		plugin.getServer().getPluginManager().registerEvents(new ScavengerListener(plugin), plugin);
		
	}
	
}
