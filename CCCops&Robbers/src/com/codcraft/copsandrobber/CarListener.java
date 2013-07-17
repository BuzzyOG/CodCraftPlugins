package com.codcraft.copsandrobber;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.ccommands.PlayerDoSpawnEvent;
import com.codcraft.copsandrobber.states.LobbyState;

/**
 * Listener for all events for Cops&Robber
 *
 */
public class CarListener implements Listener {

	/**
	 * Cops&Robbers instance
	 */
	private CopsARobber plugin;
	/**
	 * GameManger Instance from the api
	 */
	private GameManager gm;

	/**
	 * Default Constructor
	 * 
	 * @param plugin
	 * 			-The Cops&Robber Instance
	 */
	public CarListener(CopsARobber plugin) {
		this.plugin = plugin;
		gm = plugin.api.getModuleForClass(GameManager.class);
	}
	
	
	/**
	 * Removes anyone in lobby or in jail when that player does /spawn
	 * 
	 * @param e
	 * 		- PlayerDoSpawnEvent that is Called
	 */
	@EventHandler
	public void playerspawn(PlayerDoSpawnEvent e) {
		List<Game<?>> games = gm.getGamesForPlugin(plugin);
		for(Game<?> g : games) {
			CARGame game = (CARGame) g;
			if(game.inlobby.contains(e.getPlayer().getName())) {
				game.inlobby.remove(e.getPlayer().getName());
			}
			if(game.injail.contains(e.getPlayer().getName())) {
				game.injail.remove(e.getPlayer().getName());
			}
		}
	}
	
	
	/**
	 * Resets cops holding,
	 * Puts Everyone to Lobby
	 * BroadCasts Wins Message
	 * 
	 * @param e
	 * 		- GameWinEvent that is called.
	 */
	@EventHandler
	public void onWin(GameWinEvent e) {
		if(e.getGame().getPlugin() == plugin) { 
			CARGame game = (CARGame) e.getGame();
			Bukkit.broadcastMessage(e.getWinMessage());
			Location loc1 = new Location(game.jail.getWorld(), 135, 8, -262);
			Location loc2 = new Location(loc1.getWorld(), 134, 8, -262);
			Location loc3 = new Location(loc1.getWorld(), 133, 8, -262);
			loc1.getBlock().setTypeId(101);
			loc2.getBlock().setTypeId(101);
			loc3.getBlock().setTypeId(101);
			game.injail.clear();
			for(Team t : game.getTeams()) {
				for(TeamPlayer tp : t.getPlayers()) {
					t.removePlayer(tp);
					game.inlobby.add(tp.getName());
					Player p = Bukkit.getPlayer(tp.getName());
					if(p != null) {
						p.teleport(game.lobbyspawn);
					}
				}
			}
			Collections.shuffle(game.inlobby);
		}
	}
	
	/**
	 * Break event while in game is canceled
	 * 
	 * @param e
	 * 		- BlockBreakEvent that is called.
	 */
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				e.setCancelled(true);
			}
		}
	}
	
	/**
	 * On Death while a robber sent to jail
	 * 
	 * @param e
	 * 		- PlayerDeathEvent that is called.
	 */
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getEntity());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				if(g.findTeamWithPlayer(e.getEntity()).getName().equalsIgnoreCase("robbers")) {
					CARGame game = (CARGame) g;
					game.injail.add(e.getEntity().getName());
				}
			}
		}
	}
	
	/**
	 * When A Player responds telport them to their spawn points.
	 * 
	 * @param e
	 * 		- PlayerRespawnEvent that is called.
	 */
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				CARGame game = (CARGame) g;
				if(g.findTeamWithPlayer(e.getPlayer()).getName().equalsIgnoreCase("cops")) {
					e.setRespawnLocation(new Location(game.jail.getWorld(), 134, 10, -269));
				} else {
					e.setRespawnLocation(new Location(game.jail.getWorld(), 114, 10, -234));
				}
			}
		}
	}
	
	/**
	 * When a player requests to join make sure in lobby.
	 * make sure game is not full
	 * 
	 * @param e
	 * 		- RequestJoinGameEvent that is called.
	 */
	@EventHandler
	public void rj(RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			CARGame game = (CARGame) e.getGame();
			Player p = Bukkit.getPlayer(e.getPlayer().getName());
			if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
				if(game.getCurrentState().getTimeLeft() > 5) {
					if(!game.inlobby.contains(p.getName())) {
						if(game.inlobby.size() < 24) {
							game.inlobby.add(p.getName());
							p.teleport(game.lobbyspawn);
						}
					}
				}
			} else {
				e.setCancelled(true);
				Bukkit.dispatchCommand(p, "spawn");
			}
		}
	}
	
	/**
	 * Sets the color of the name above head.
	 * 
	 * @param e
	 * 		- PlayerReceivedNameTagEvent that is called.
	 */
	@EventHandler
	public void onrepresh(PlayerReceiveNameTagEvent e) {
		Player p = e.getNamedPlayer();
		Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin) {
				e.setTag(g.findTeamWithPlayer(p).getColor()+p.getName());
			}
		}
	}
	
	/**
	 * Makes sure everyone has full food level
	 * 
	 * @param e
	 * 		- FoodLevelChangeEvent that is called.
	 */
	@EventHandler
	public void ent(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					e.setFoodLevel(20);
				}
			}
		}
	}
	
	/**
	 * When player is hit check if he is a robber and hitter is a cop and send him to jail. 
	 * 
	 * @param e
	 * 		- EntityDamagedByEntityEvent that is called.
	 */
	@EventHandler
	public void dame(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					CARGame game = (CARGame) g;
					if(e.getDamager() instanceof Player) {
						Player d = (Player) e.getDamager();
						if(g.findTeamWithPlayer(p).getName().equalsIgnoreCase("robbers")) {
							if(g.findTeamWithPlayer(d).getName().equalsIgnoreCase("cops")) {
								if(!game.injail.contains(p.getName())) {
									game.injail.add(p.getName());
									p.teleport(game.jail);
								}
								if(g.findTeamWithName("robbers").getPlayers().size() == game.injail.size()) {
									game.DetectWin();
									game.setState(new LobbyState(game));
								}
							} else {
								if(game.injail.contains(p.getName())) {
									if(!game.injail.contains(d.getName())) {
										game.injail.remove(p.getName());
									}
								}
							}
						}
					}
					e.setCancelled(true);
				}
			}
			
		}
	}

}
