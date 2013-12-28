package com.codcraft.CCKOTL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class KOTL extends CCGamePlugin {
	
	private CCAPI api;

	public void onEnable() {
		Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi != null) {
			api = (CCAPI) ccapi;
		}
		StartTIMER();
	}
	
	private void StartTIMER() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			

			@Override
			public void run() {
				for(Game<?> g : api.getModuleForClass(GameManager.class).getAllGames()) {
					Player p = null;
					int height = 0;
					for(Team t : g.getTeams()) {
						Player p2 = null;
						for(TeamPlayer tp : t.getPlayers()) {
							p2 = Bukkit.getPlayer(tp.getName());
						}
						if(p2 != null) {
							if(p2.getLocation().getY() > height) {
								p = p2;
								p.getName();
							}
						}
					}
				}
				
			}
		}, 20, 20);
		
	}

	@Override
	public String getTag() {
		return "CCKOTL";
	}

	@Override
	public void makeGame(String[] args) {
	}

}