package com.codcraft.perks.eqip2x;

import com.codcraft.perks.Perks;

public class Eqip2x {

	@SuppressWarnings("unused")
	private Perks plugin;

	public Eqip2x(Perks plugin) {
		this.plugin =plugin;
		plugin.getServer().getPluginManager().registerEvents(new Eqip2xListener(plugin), plugin);
		
	}
}
