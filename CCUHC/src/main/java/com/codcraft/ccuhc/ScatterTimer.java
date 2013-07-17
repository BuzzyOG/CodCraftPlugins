package com.codcraft.ccuhc;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.ccuhc.states.LobbyState;

public class ScatterTimer implements Runnable {
	
	private UHCGame game;

	public ScatterTimer(Game<?> g) {
		this.game = (UHCGame) g;
	}

	@Override
	public void run() {
		Random rnd = new Random();
		if(!game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
			for(Team t : game.getTeams()) {
				if(t.getName().equalsIgnoreCase("main")) {
					for(TeamPlayer tp : t.getPlayers()) {
						Player p = Bukkit.getPlayer(tp.getName());
						if(p != null) {

							Location loc = game.getPlugin().scatterPlayerRandom(p, Bukkit.getWorld(game.getId()));
							p.teleport(loc);
						}
					}
				} else {
					TeamPlayer tp1 = null;
					for(TeamPlayer tp : t.getPlayers()) {
						tp1 = tp;
						break;
					}
					
					Location loc = game.getPlugin().scatterPlayerRandom(Bukkit.getPlayer(tp1.getName()), Bukkit.getWorld(game.getId()));
					for(TeamPlayer tp : t.getPlayers()) {
						Player p = Bukkit.getPlayer(tp.getName());
						if(p != null) {
							p.teleport(loc);
						}
					}
				}
				
			}
			Bukkit.getScheduler().runTaskLater(game.getPlugin(), new ScatterTimer(game), rnd.nextInt(24000));
		}
	}
}
