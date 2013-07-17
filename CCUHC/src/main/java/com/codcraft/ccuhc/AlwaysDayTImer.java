package com.codcraft.ccuhc;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.CodCraft.api.model.Game;
import com.codcraft.ccuhc.states.LobbyState;

public class AlwaysDayTImer implements Runnable {
	
	private Game<?> game;
	private int time;

	public AlwaysDayTImer(Game<?> g, int time) {
		this.game = g;
		this.time = time;
	}
	
	@Override
	public void run() {
		if(!game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
			World world = Bukkit.getWorld(game.getId());
			if(world != null) {
				world.setTime(6000);
			}
			Bukkit.getScheduler().runTaskLater(game.getPlugin(), new AlwaysDayTImer(game, time), time);
		}
	}

}
