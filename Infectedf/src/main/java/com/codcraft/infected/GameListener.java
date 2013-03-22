package com.codcraft.infected;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.infected.CodCraftInfected.GameState;

public class GameListener extends CCGameListener {

	private CodCraftInfected plugin;
	
	public GameListener(CodCraftInfected plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void chooseteam(RequestJoinGameEvent e) {
		if(!(e.getGame().getPlugin() == plugin)) {
			return;
		}
		@SuppressWarnings("unchecked")
		Game<CodCraftInfected> game = (Game<CodCraftInfected>) e.getGame();
		plugin.getLogger().info(e.getPlayer().getName()+" has requested to join a TDM game named " + game.getName()+".");
		if(game.findTeamWithPlayer(e.getPlayer()) != null) {
			return;
		}
		Team team1 = game.getTeams().get(0);
		Team team2 = game.getTeams().get(1);
		if(team1.getPlayers().size() > team2.getPlayers().size()) {
			team2.addPlayer(e.getPlayer());
		} else {
			team1.addPlayer(e.getPlayer());
		}
	}
	
	
	/*@EventHandler (priority = EventPriority.LOWEST)
	public void onDagame(PlayerDamgedByWeaponEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getDamagee());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				Team t1 = g.findTeamWithPlayer(e.getDamagee());
				Team t2 = g.findTeamWithPlayer(e.getDamager());
				if(t1 == t2) {
					Bukkit.broadcastMessage("Same Team");
					e.setCancelled(true);
					e.setSameteam(true);
				} else {
					Bukkit.broadcastMessage("Not Same Team");
					e.setCancelled(false);
					e.setSameteam(false);
				}
			}
		}
	}*/
	
	
	
	@EventHandler
	public void onGameJoin(TeamPlayerGainedEvent e) {
		  final GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  final Game<?> g = gm.getGameWithTeam(e.getTeam());
		  
		  if( g == null) {
			  plugin.getLogger().info("null");
		  }
		  
		if(g.getPlugin() != plugin) {
			return;
		}
		plugin.getLogger().info(e.getPlayer().getName()+" has join a TDM game named " + g.getName()+".");
		@SuppressWarnings("unchecked")
		final Game<CodCraftInfected> game = (Game<CodCraftInfected>)g;
		final String currentmap = plugin.currentmap.get(game.getId()).map;
		final Player p = Bukkit.getPlayer(e.getPlayer().getName());
		InfectedModel model = plugin.currentmap.get(g.getId());
		if(model.state == GameState.LOBBY) {
			p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
			p.sendMessage(ChatColor.BLUE+model.Map1);
			p.sendMessage(ChatColor.BLUE+"or");
			p.sendMessage(ChatColor.BLUE+model.Map2);
		}

		
		p.teleport(plugin.Respawn(p, Bukkit.getWorld(game.getName()), currentmap, g));
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				PlayerGetClassEvent event = new PlayerGetClassEvent(p, gm.getGameWithPlayer(p));
				Bukkit.getPluginManager().callEvent(event);
				for(ItemStack i : event.getItems()) {
					p.getInventory().addItem(i);
				}
				updateallgui(g);
				
			}
		}, 3);
		

	}
	
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(p);

		if(g == null) {
			return;
		}
		if(!(g.getPlugin() == plugin)) {
			return;
		}
    	e.getDrops().clear();
		Team team1 = g.findTeamWithPlayer(p);
		 
		CCPlayerModule ccplayerm = plugin.api.getModuleForClass(CCPlayerModule.class);
		CCPlayer player1 = ccplayerm.getPlayer(p);
		team1.findPlayer(p).incrementDeaths(1);
		
		if(player1.getDeaths() == null) {
			player1.setDeaths(0);
		}
		player1.setDeaths(player1.getDeaths() +1);
		if(e.getEntity().getKiller() instanceof Player) {
			if(g.getTeams().get(1).getPlayers().size() == 1) {
				List<TeamPlayer> l = new ArrayList<>();
				for(TeamPlayer tp : g.getTeams().get(1).getPlayers()) {
					l.add(tp);
				}
				Player p1 = Bukkit.getPlayer(l.get(0).getName());
				for(ItemStack i : p1.getInventory()) {
					if(i.getType() != Material.BOW) {
						p.getInventory().remove(i);
					}
				}
			}
			Player k =(Player) e.getEntity().getKiller();
			Team team2 = g.findTeamWithPlayer(k);

			team2.setScore(team2.getScore() + 1);

			CCPlayer player2 = ccplayerm.getPlayer(k);
			if(player2.getDeaths() == null) {
				player2.setDeaths(0);
			}
			player2.setKills(player2.getKills() + 1);
			team2.findPlayer(k).incrementKills(1);
			TeamPlayer p5 = team1.findPlayer(p);
			team1.removePlayer(p);
			team2.addPlayer(p5);
			if(checkwin(team2)) {
				GameWinEvent event = new GameWinEvent(team2.getName()+" has won!", team2, g);
				Bukkit.getPluginManager().callEvent(event);
				Bukkit.broadcastMessage(event.getWinMessage());
			} else {
			}
		}
		updateallgui(g);
	}
	
	@EventHandler
	public void onExspotion(EntityExplodeEvent e) {
		if(e.getEntity() == null) {
			return;
		}
		for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {

			if(e.getEntity().getWorld().getName().equalsIgnoreCase(g.getName())) {
				if(g.getPlugin() == plugin) {
					e.blockList().clear();
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				e.setCancelled(true);
			}
		}
	}
	
	
	
	public void updateallgui(Game<?> g) {
		for(Team t : g.getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				Player p =Bukkit.getPlayer(tp.getName());
				if(p.isOnline()) {
					guiupdate(p);
				}
			}
		}
	}
	
	public void guiupdate(Player p) {
		GUI gui = plugin.api.getModuleForClass(GUI.class);
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(p);
		Team t1 = g.getTeams().get(0);
		Team t2 = g.getTeams().get(1);
		
		ArrayList<String> l1 = new ArrayList<>();
		l1.add(t1.getName()+t1.getScore());
		for(TeamPlayer tp1 : t1.getPlayers()) {
			l1.add(tp1.getName().substring(0, 4)+"K:"+tp1.getKills()+"D:"+tp1.getDeaths());
		}
		l1.add(t2.getName()+t2.getScore());
		for(TeamPlayer tp1 : t1.getPlayers()) {
			l1.add(tp1.getName().substring(0, 4)+"K:"+tp1.getKills()+"D:"+tp1.getDeaths());
		}
		gui.updateplayerlist(p, l1);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)){
			if(g.getPlugin() == plugin) {
				g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
				updateallgui(g);
			}
		}
		
	}
	
	@EventHandler
	public void onRespawn2(final PlayerRespawnEvent e) {
		final GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g == null) {
			return;
		}
		if(!(g.getPlugin() == plugin)) {
			return;
		}
		Player p = e.getPlayer();
		if(g.getTeams().get(1).containsPlayer(p)) {
			if(g.getTeams().get(1).getPlayers().size() == 1) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						PlayerGetClassEvent event = new PlayerGetClassEvent(e.getPlayer(), gm.getGameWithPlayer(e.getPlayer()));
						Bukkit.getPluginManager().callEvent(event);
						for(ItemStack i : event.getItems()) {
							e.getPlayer().getInventory().addItem(i);
						}
						
					}
				}, 1);
			} else {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						PlayerGetClassEvent event = new PlayerGetClassEvent(e.getPlayer(), gm.getGameWithPlayer(e.getPlayer()));
						Bukkit.getPluginManager().callEvent(event);
						for(ItemStack i : event.getItems()) {
							if(i.getType() == Material.DIAMOND_SWORD) {
								e.getPlayer().getInventory().addItem(i);
							}

						}
						
					}
				}, 1);
			}
		} else {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					PlayerGetClassEvent event = new PlayerGetClassEvent(e.getPlayer(), gm.getGameWithPlayer(e.getPlayer()));
					Bukkit.getPluginManager().callEvent(event);
					for(ItemStack i : event.getItems()) {
						e.getPlayer().getInventory().addItem(i);
					}
					
				}
			}, 1);
		}

	}
	@EventHandler
	public void onWIn(GameWinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(g == null) {
			plugin.getLogger().info("null");
		}  
		if(g.getPlugin() != plugin) {
			return;
		}
		
		plugin.currentmap.get(g.getId()).state = GameState.LOBBY;
		plugin.currentmap.get(g.getId()).gametime = 30;
		List<TeamPlayer> test = new ArrayList<>();
		for(TeamPlayer t : e.getTeam().getPlayers()) {
			test.add(t);
		}
		TeamPlayer t = null;
		
		if(test.size() != 0) {
			t = test.get(0);
		}

		if(!(t == null)) {
			e.setWinMessage(t.getName()+" has won the game");
			CCPlayer player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(t.getName()));
			player.setWins(player.getWins() + 1);
		}
		Random rnd = new Random();
		plugin.currentmap.get(g.getId()).Map1 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		plugin.currentmap.get(g.getId()).Map2 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		while(plugin.currentmap.get(g.getId()).Map1.equalsIgnoreCase(plugin.currentmap.get(g.getId()).Map2)) {
			plugin.currentmap.get(g.getId()).Map1 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		}
		for(Team team : g.getTeams()) {
			for(TeamPlayer tp : team.getPlayers()) {
				tp.setDeaths(0);
				tp.setKills(0);
				InfectedModel model = plugin.currentmap.get(g.getId());
				Player p = Bukkit.getPlayer(tp.getName());

				p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
				p.sendMessage(ChatColor.BLUE+model.Map1);
				p.sendMessage(ChatColor.BLUE+"or");
				p.sendMessage(ChatColor.BLUE+model.Map2);
						
			}
			team.setScore(0);
		}
		updateallgui(g);

	}
	@EventHandler (priority = EventPriority.LOWEST)
	public void onRespawn(PlayerRespawnEvent e) {
		  GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		  
		  if(g == null) {
			 return;
		  }
		  /*System.out.println(g);
		  System.out.println(g.getPlugin());
		  System.out.println(plugin);*/
		  
		if(!(g.getPlugin() == plugin)) {
			return;
		}
		Player p = e.getPlayer();
		final String currentmap = plugin.currentmap.get(g.getId()).map;
		e.setRespawnLocation(plugin.Respawn(p, Bukkit.getWorld(g.getName()), currentmap, g));
	}
	
	private boolean checkwin(Team t) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithTeam(t);
		if(g.getTeams().get(0).getPlayers().size() == 0) return true;
		return false;
	}

}
