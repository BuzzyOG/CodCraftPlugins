package com.codcraft.ccuhc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;

public class UHClistener extends CCGameListener {
	private UHC plugin; 
	
	public UHClistener(UHC plugin) {
		this.plugin = plugin;
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
							int heath = p2.getHealth()/2;
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
	
	private Map<String, Long> leavemap = new HashMap<String, Long>();
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				leavemap.put(e.getPlayer().getName(), System.currentTimeMillis());
			}
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				plugin.getLogger().info(String.valueOf(System.currentTimeMillis() - leavemap.get(e.getPlayer().getName())));
				if(System.currentTimeMillis() - leavemap.get(e.getPlayer().getName()) >= 5000) {
					g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
				}
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
							int heath = p2.getHealth()/2;
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
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						if(g.getTeams().get(0).getPlayers().size() == 1) {
							TeamPlayer p11 = null;
							for(TeamPlayer p1 : g.getTeams().get(0).getPlayers()) {
								p11 = p1;
							}
							GameWinEvent event = new GameWinEvent(ChatColor.BLUE+p11.getName()+" has won the "+g.getName()+" game!", g.getTeams().get(0), g);
							Bukkit.getPluginManager().callEvent(event);
							Bukkit.broadcastMessage(event.getWinMessage());
						}
						
					}
				}, 2);
				
			}
		}
	}
	@EventHandler
	public void onWin(GameWinEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			Game<?> g = e.getGame();
			for(Team t : g.getTeams()) {
				for(TeamPlayer p : t.getPlayers()) {
					t.removePlayer(p);
					Bukkit.getPlayer(p.getName()).teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
				}
			}
			g.deinitialize();
			g.initialize();	
			plugin.running = false;
			
		}
	}
	
	@EventHandler
	public void onGameJoin(RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() != plugin) {
			return;
		}
		if(plugin.running){
			e.setCancelled(true);
			//TODO add to spectators
			
			Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getPlayer(e.getPlayer().getName()), "spawn");
		} else {
			e.setTeam(e.getGame().getTeams().get(0));
			plugin.getLogger().info(e.getPlayer().getName()+" has requested to join a SSB game named " + e.getGame().getName()+".");
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
		plugin.getLogger().info(e.getPlayer().getName()+" has join a UHC game named " + g.getName()+".");
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		World w = Bukkit.getWorld(g.getName()+plugin.i);
		p.teleport(scatterPlayerRandom(p, w));
		p.setHealth(20);
		p.setFoodLevel(20);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
		
			
			@Override
			public void run() {
				if(g.getTeams().get(0).getPlayers().size() == 1) {
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							plugin.running = true;
							for(TeamPlayer p1 : g.getTeams().get(0).getPlayers()) {
								
								Player p5 = Bukkit.getPlayer(p1.getName());
								if(!(p5 == null)){
									p5.sendMessage(ChatColor.BLUE+"Game has started!");
								}
							}
						}
					}, 600);
				}
				
			}
		}, 2);
		
	}
	private Location scatterPlayerRandom(Player p, World w) {
	    Random random = new Random();
	   

	    Location finalTeleport = new Location(w, 0.0D, 0.0D, 0.0D);

	      double randomAngle = random.nextDouble() * 3.141592653589793D * 2.0D;
	      double newradius = 500 * Math.sqrt(random.nextDouble());
	      double[] coords = convertFromRadiansToBlock(newradius, randomAngle);

	      finalTeleport.setX(coords[0] + 0 + 0.5D);
	      finalTeleport.setZ(coords[1] + 0 + 0.5D);

	      if (!w.getChunkAt(finalTeleport).isLoaded()) {
	        w.getChunkAt(finalTeleport).load(true);
	      }

	      finalTeleport.setY(getSafeY(finalTeleport));
	    if(finalTeleport.getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER) {
	    	scatterPlayerRandom(p, w);
	    }
	    

	    finalTeleport.setY(finalTeleport.getY() + 1.0D);
	    return finalTeleport;
	  }

	  private int getSafeY(Location loc) {
	    return loc.getWorld().getHighestBlockAt(loc).getY() - 1;
	  }
	  private double[] convertFromRadiansToBlock(double radius, double angle) {
		    return new double[] { Math.round(radius * Math.cos(angle)), Math.round(radius * Math.sin(angle)) };
		  }
	  @EventHandler
	  public void onMove(PlayerMoveEvent e) {
		  Player p = e.getPlayer();
		  GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  Game<?> g = gm.getGameWithPlayer(p);
		  if(!(g == null)) {
			  if(g.getPlugin() == plugin) {
				  if(!plugin.running) {
					  if(e.getTo().getWorld() != Bukkit.getWorld(g.getName()+plugin.i)) {
						  return;
					  }
					  e.setTo(e.getFrom());
					  p.sendMessage(ChatColor.BLUE+"Please wait for the game to start!");
					  
					  return;
				  }
				  if(p.getLocation().getX() > 500){
					  e.setTo(new Location(e.getTo().getWorld(), 495, getSafeY(p.getLocation()), e.getFrom().getZ()));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  } else if (p.getLocation().getX() < -500) {
					  e.setTo(new Location(e.getTo().getWorld(), -495, getSafeY(p.getLocation()), e.getFrom().getZ()));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  } else if(p.getLocation().getZ() > 500){
					 e.setTo(new Location(e.getTo().getWorld(), e.getFrom().getX(), getSafeY(p.getLocation()), 495));
					 p.sendMessage(ChatColor.BLUE+"You have reached the border");
					 return;
				  } else if(p.getLocation().getZ() < -500) {
					  e.setTo(new Location(e.getTo().getWorld(), e.getFrom().getX(), getSafeY(p.getLocation()), -495));
					  p.sendMessage(ChatColor.BLUE+"You have reached the border");
					  return;
				  }
			  }

		  }
		  
	  }
	  

}
