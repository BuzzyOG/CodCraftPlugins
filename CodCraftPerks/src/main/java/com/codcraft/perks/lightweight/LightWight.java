package com.codcraft.perks.lightweight;

import com.codcraft.perks.Perks;

public class LightWight {
	@SuppressWarnings("unused")
	private Perks plugin;
	public LightWight(Perks plugin) {
		this.plugin =plugin;
		plugin.getServer().getPluginManager().registerEvents(new LightWeightListener(plugin), plugin);
		
	}
	
	
	

}
