package com.codcraft.bunggie;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;




public class Main extends Plugin implements Listener {
	

	public void onEnable() {
		BungeeCord.getInstance().pluginManager.registerListener(this, new com.codcraft.bunggie.Listener(this));
	}
	
	


	


}
