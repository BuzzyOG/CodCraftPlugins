package com.codcraft.CCSG;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.CCSG.states.DeathMatchState;
import com.codcraft.CCSG.states.LobbyState;

public class GameListner implements Listener {

	private SurvialGames plugin;
	private GameManager gm;

	public GameListner(SurvialGames survialGames) {
		this.plugin = survialGames;
		gm = survialGames.api.getModuleForClass(GameManager.class);
	}
	
	@EventHandler
	public void playerleave(TeamPlayerLostEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				CCSGGame game = (CCSGGame) g;
				if(game.map == null) {
					return;
				}
				for(Entry<Location, String> s : game.map.spawnblocks.entrySet()) {
					if(s.getValue() != null) {
						if(s.getValue().equalsIgnoreCase(e.getPlayer().getName())) {
							s.setValue(null);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onquit(PlayerQuitEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
			}
		}
	}
	
	
	@EventHandler
	public void onRequest(RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			Game<?> g = e.getGame();
			if(!g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
				e.setCancelled(true);
				return;
			}
			if(g.findTeamWithName("Team").getPlayers().size() >= 24) {
				e.setCancelled(true);
				return;
			}
			g.findTeamWithName("Team").addPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onJoin(TeamPlayerGainedEvent e) {
		Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				CCSGGame game = (CCSGGame) g;
				Player p = Bukkit.getPlayer(e.getPlayer().getName());
				p.setFlying(false);
				p.setAllowFlight(false);
				p.setLevel(0);
				p.setExp(0);
				p.setGameMode(GameMode.SURVIVAL);
				p.teleport(game.lobby);
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				if(e.getBlock().getType() == Material.LEAVES || 
						e.getBlock().getType() == Material.RED_MUSHROOM ||
						e.getBlock().getType() == Material.BROWN_MUSHROOM) {
					CCSGGame game = (CCSGGame) g;
					game.blocks.put(e.getBlock().getLocation(), e.getBlock().getType());
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onWin(GameWinEvent e) {
		Game<?> g = e.getGame();
		if(g != null) {
			if(g.getPlugin() == plugin) {
				CCSGGame game = (CCSGGame) g;
				for(Entry<Location, String> s : game.map.spawnblocks.entrySet()) {
					s.setValue(null);
				}
				for(Team t : game.getTeams()) {
					for(TeamPlayer tp : t.getPlayers()) {
						Player p = Bukkit.getPlayer(tp.getName());
						if(p != null) {
							p.teleport(game.lobby);
						}
					}
				}
				g.setState(new LobbyState(g));
				Bukkit.broadcastMessage(e.getWinMessage());
			}
		}
	} 
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getEntity());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				g.findTeamWithPlayer(e.getEntity()).removePlayer(e.getEntity());
				g.getSpectators().add(new TeamPlayer(e.getEntity().getName()));
				if(g.getTeams().get(0).getPlayers().size() == 1) {
					TeamPlayer tp = null;
					for(Team t : g.getTeams()) {
						for(TeamPlayer tp1 : t.getPlayers()) {
							tp = tp1;
						}
					}
					GameWinEvent event = new GameWinEvent(tp.getName() + "won the game!", g.findTeamWithPlayer(tp), g);
					Bukkit.getPluginManager().callEvent(event);
				} else if(g.getTeams().get(0).getPlayers().size() <= 2) {
					g.setState(new DeathMatchState(g));
				}
			}
		}
	}
	

}
