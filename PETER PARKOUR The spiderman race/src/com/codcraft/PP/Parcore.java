package com.codcraft.PP;

import org.bukkit.Bukkit;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class Parcore extends CCGamePlugin {
	
	public void onEnable() {
		super.onEnable();
		getServer().getPluginManager().registerEvents(new GameListener(this), this);
		day();
	}

	private void day() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				Bukkit.getWorld("world").setTime(0);
				
			}
		}, 0, 20);
		
	}

	@Override
	public String getTag() {
		return "PP";
	}

	@Override
	public void makegame(String[] args) {
		PPGame game = new PPGame(this);
		game.setName(args[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}

}
