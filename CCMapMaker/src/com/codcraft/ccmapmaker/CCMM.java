package com.codcraft.ccmapmaker;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CCMM extends CCGamePlugin {
	
	
	public CCAPI api;

	public void onEnable() {
		Plugin plug = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(plug != null && plug instanceof CCAPI) {
			api = (CCAPI) plug;
		}
		getServer().getPluginManager().registerEvents(new GameListener(this), this);
		daytimer();
		savetimer();
		getCommand("mm").setExecutor(new MMAdd());
	}

	private void savetimer() {
		final CCMM plugin = this;
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage("Saving Worlds");
				for(Game<?> g : api.getModuleForClass(GameManager.class).getAllGames()) {
					if(g.getPlugin() == plugin) {
						World w = Bukkit.getWorld(g.getName());
						if(w != null) {
							Broadcast b = api.getModuleForClass(Broadcast.class);
							b.BroadCastMessage(g, "Saving world your on!");
							w.save();
							b.BroadCastMessage(g, "Saving ended on world your on!");
						}
					}

				}
				Bukkit.broadcastMessage("Ended Saving Worlds");
			}
		}, 0, 6000);
		
	}

	private void daytimer() {
		
		final CCMM plugin = this;
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {	
				for(Game<?> g : api.getModuleForClass(GameManager.class).getGamesForPlugin(plugin)) {
					World w = Bukkit.getWorld(g.getName());
					if(w != null) {
						w.setTime(6000);
					}
				}
			}
		}, 0, 200);
		
	}

	@Override
	public String getTag() {
		return "[CCMM]";
	}

	@Override
	public void makegame(String[] args) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		MapMaker mm = new MapMaker(this);
		mm.setName(args[0]);
		gm.registerGame(mm);
		
		
	}

}
