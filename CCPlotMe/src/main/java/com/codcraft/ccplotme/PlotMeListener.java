package com.codcraft.ccplotme;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.worldcretornica.plotme.PlotManager;

public class PlotMeListener implements Listener {
	private PlotMe plugin;
	public PlotMeListener(PlotMe plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void teamrequest(RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			e.getGame().getTeams().get(0).addPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void playerjoin(final TeamPlayerGainedEvent e) {
		Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithTeam(e.getTeam());
		if(g.getPlugin() == plugin) {
			final Player p = Bukkit.getPlayer(e.getPlayer().getName());
			p.teleport(Bukkit.getWorld(g.getName()).getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					if(PlotManager.getFirstPlot(e.getPlayer().getName()) == null) {
						Bukkit.dispatchCommand(p, "plotme auto");
					} else {
						Bukkit.dispatchCommand(p, "plotme home");
					}

					
				}
			}, 5);
		}
	}
	
	
}
