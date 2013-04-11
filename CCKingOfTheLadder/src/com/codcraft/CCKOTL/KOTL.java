package com.codcraft.CCKOTL;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.CCGamePlugin;

public class KOTL extends CCGamePlugin {
	
	private List<Game> games = new ArrayList<>();

	public void onEnable() {
		StartTIMER();
	}
	
	private void StartTIMER() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			

			@Override
			public void run() {
				for(Game<?> g : games) {
					Player p = null;
					int height = 0;
					for(Team t : g.getTeams()) {
						Player p2 = null;
						for(TeamPlayer tp : t.getPlayers()) {
							p2 = Bukkit.getPlayer(tp.getName());
						}
						if(p2 != null) {
							if(p2.getHealth() > height) {
								
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
	public void makegame(String name) {
	}

}