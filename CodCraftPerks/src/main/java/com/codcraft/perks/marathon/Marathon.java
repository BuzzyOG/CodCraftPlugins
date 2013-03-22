package com.codcraft.perks.marathon;

import com.codcraft.perks.Perks;

public class Marathon {
	private Perks plugin;
	public	Marathon(Perks plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(new MarathonListener(plugin), plugin);
		
	}
}
