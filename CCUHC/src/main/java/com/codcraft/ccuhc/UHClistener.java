package com.codcraft.ccuhc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;
import com.codcraft.ccuhc.states.LobbyState;

public class UHClistener extends CCGameListener {
	private UHC plugin; 
	
	public UHClistener(UHC plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onLeave(TeamPlayerLostEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				detectwin(g);
			}
		}
	}
	
	@EventHandler
	public void playerdam(EntityDamageEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final GUI gui = plugin.api.getModuleForClass(GUI.class);
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			final Game<?> g = gm.getGameWithPlayer(p);
			if(!(g == null)) {
				if(g.getPlugin() == plugin) {
					final ArrayList<String> l = new ArrayList<>();
					for(Team t : g.getTeams()) {
						for(TeamPlayer p1 : t.getPlayers()) {
							Player p2 = Bukkit.getPlayer(p1.getName());
							double heath = ((CraftPlayer)p2).getHealth()/2;
							l.add(p1.getName()+": " + heath);
						}
					}
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							for(Team t : g.getTeams()) {
								for(TeamPlayer p1 : t.getPlayers()) {
									Player p2 = Bukkit.getPlayer(p1.getName());
									gui.updateplayerlist(p2, l);
								}
							}
							
						}
					}, 2);

				}
			}
		}
	}
	
	@EventHandler
	public void onLeave(final PlayerQuitEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				final String name = e.getPlayer().getName();
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						Player p = Bukkit.getPlayer(name);
						if(p == null) {
							g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
						}
					}
				}, 600);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPortalEnter(EntityPortalEnterEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					World world = p.getWorld();
					if(world.getEnvironment() == Environment.NETHER) {
						double x = p.getLocation().getX() * 8;
						double z = p.getLocation().getZ() * 8;
						p.teleport(new Location(Bukkit.getWorld(g.getId()), x, p.getLocation().getY(), z));
						
					} else if (world.getEnvironment() == Environment.NORMAL) {
						String name = world.getName() + "_nether";
						World nether = Bukkit.getWorld(name);
						if(nether == null) {
							WorldCreator wc = new WorldCreator(name);
							wc.environment(Environment.NETHER);
							Bukkit.createWorld(wc);
						}
						double x = p.getLocation().getX() / 8;
						double z = p.getLocation().getZ() / 8;
						Location loc = new Location(nether, x, 65, z);
						p.teleport(loc);
					} else {
						World normal = Bukkit.getWorld(g.getId());
						p.teleport(normal.getSpawnLocation());
					}

				}
			}
		}
	}
	
	


	@EventHandler
	public void onDamege(PlayerDamgedByWeaponEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			if(e.getGame().getCurrentState().getId().equalsIgnoreCase(new LobbyState(e.getGame()).getId())) {
				
			} else {
				e.setCancelled(false);
				e.setSameteam(false);
			}

		}
	}
	@EventHandler
	public void onregen(EntityRegainHealthEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final GUI gui = plugin.api.getModuleForClass(GUI.class);
		if(e.getEntity() instanceof Player) {
			final Game<?> g = gm.getGameWithPlayer((Player) e.getEntity());
			if(g != null) {
				if(g.getPlugin() == plugin) {
					final ArrayList<String> l = new ArrayList<>();
					for(Team t : g.getTeams()) {
						for(TeamPlayer p1 : t.getPlayers()) {
							Player p2 = Bukkit.getPlayer(p1.getName());
							if(p2 != null) {
								double heath = ((CraftPlayer)p2).getHealth()/2;
								l.add(p1.getName()+": " + heath);
							}

						}
					}
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							for(Team t : g.getTeams()) {
								for(TeamPlayer p1 : t.getPlayers()) {
									Player p2 = Bukkit.getPlayer(p1.getName());
									if(p2 != null) {
										gui.updateplayerlist(p2, l);
									}
								}
							}
							
						}
					}, 2);
					if(e.getRegainReason() == RegainReason.SATIATED) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin) {
				g.findTeamWithPlayer(p).removePlayer(p);
				g.getSpectators().add(new TeamPlayer(p.getName()));
				
			}
		}
	}
	
	private void detectwin(Game<?> g) {
		if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
			return;
		}
		List<TeamPlayer> players = new ArrayList<>();
		for(Team t : g.getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				players.add(tp);
			}
		}
		if(players.size() == 1) {
			TeamPlayer p11 = null;
			for(Team t : g.getTeams()) {
				for(TeamPlayer p1 : t.getPlayers()) {
					p11 = p1;
				}
			}
			GameWinEvent event = new GameWinEvent(ChatColor.BLUE+p11.getName()+" has won the "+g.getName()+" game!", g.getTeams().get(0), g);
			Bukkit.getPluginManager().callEvent(event);
			Bukkit.broadcastMessage(event.getWinMessage());
		} else if (players.size() == 0) {
			g.deinitialize();
			g.initialize();	
			g.setState(new LobbyState(g));
		}
		
	}
	@EventHandler
	public void onWin(GameWinEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			UHCGame g = (UHCGame) e.getGame();
			g.setState(new LobbyState(g));
			for(Team t : g.getTeams()) {
				for(TeamPlayer p : t.getPlayers()) {
					Bukkit.dispatchCommand(Bukkit.getPlayer(p.getName()), "spawn");
				}
			}
			g.deinitialize();
			g.initialize();	
			
			
		}
	}
	
	@EventHandler
	public void onGameJoin(RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() != plugin) {
			return;
		}
		final UHCGame g = (UHCGame) e.getGame();
		if(!g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
			e.setCancelled(true);
			//TODO add to spectators
			Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getPlayer(e.getPlayer().getName()), "spawn");
		} else {
			e.setTeam(e.getGame().findTeamWithName("main"));
			plugin.getLogger().info(e.getPlayer().getName()+" has requested to join a SSB game named " + e.getGame().getName()+".");
			plugin.getLogger().info(e.getPlayer().getName()+" has join a UHC game named " + g.getName()+".");
			final Player p = Bukkit.getPlayer(e.getPlayer().getName());
			World w = Bukkit.getWorld(g.getId());
			final UHCGame game = (UHCGame) g;
			if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
				p.teleport(new Location(w, 0, w.getHighestBlockYAt(0, 0), 0));
			} else {
				p.teleport(plugin.scatterPlayerRandom(p, w));
				p.setHealth(20D);
				p.setFoodLevel(20);
			}

			
			
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
				
				@Override
				public void run() {
					if(g.findTeamWithName("main").getPlayers().size() == 1) {
						
						game.setLobbyLeader(p.getName());
						p.sendMessage("You are now the Game Leader!");
						

					}
					
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void onResawn(PlayerRespawnEvent e) {
		
	}
	
	
	@EventHandler
	public void joinGame(TeamPlayerGainedEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(!(g.getPlugin() == plugin)) {
			return;
		}
		

		
	}
	



	  /*@EventHandler
	  public void onMove(PlayerMoveEvent e) {
		  Player p = e.getPlayer();
		  GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  Game<?> g = gm.getGameWithPlayer(p);
		  if(!(g == null)) {
			  if(g.getPlugin() == plugin) {
				  if(!plugin.games.get(g.getId()).isRunning()) {
					  if(e.getTo().getWorld() != Bukkit.getWorld(g.getName())) {
						  return;
					  }
					  e.setTo(e.getFrom());
					  p.sendMessage(ChatColor.BLUE+"Please wait for the game to start!");
					  
					  return;
				  }
				  if(p.getLocation().getX() > plugin.games.get(g.getId()).getRadios()){
					  e.setTo(new Location(e.getTo().getWorld(), plugin.games.get(g.getId()).getRadios() - 5, getSafeY(p.getLocation()), e.getFrom().getZ()));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  } else if (p.getLocation().getX() < plugin.games.get(g.getId()).getRadios() * -1) {
					  e.setTo(new Location(e.getTo().getWorld(), plugin.games.get(g.getId()).getRadios() * -1 - 5, getSafeY(p.getLocation()), e.getFrom().getZ()));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  } else if(p.getLocation().getZ() > plugin.games.get(g.getId()).getRadios()){
					 e.setTo(new Location(e.getTo().getWorld(), e.getFrom().getX(), getSafeY(p.getLocation()), plugin.games.get(g.getId()).getRadios() - 5));
					 p.sendMessage(ChatColor.BLUE+"You have reached the border");
					 return;
				  } else if(p.getLocation().getZ() < plugin.games.get(g.getId()).getRadios() * -1) {
					  e.setTo(new Location(e.getTo().getWorld(), e.getFrom().getX(), getSafeY(p.getLocation()), plugin.games.get(g.getId()).getRadios() * -1 - 5));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  }
			  }

		  }
		  
	  }*/
	  

}
