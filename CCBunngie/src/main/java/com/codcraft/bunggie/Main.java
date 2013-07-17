package com.codcraft.bunggie;

import java.util.Timer;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin implements Listener {
	
	public Utilities utils;
	public Timer timer;

	public void onEnable() {
		BungeeCord.getInstance().pluginManager.registerListener(this, new com.codcraft.bunggie.Listener(this));
		BungeeCord.getInstance().registerChannel("CodCraft");
		BungeeCord.getInstance().getPluginManager().registerListener(this, new PluginMessageListener(this));
		utils = new Utilities(this);
		timer = new Timer();
	}
	



	


}
