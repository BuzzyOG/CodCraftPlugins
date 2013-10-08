package com.codcraft.ffa;


import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

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
import com.CodCraft.api.modules.ScoreBoard;
import com.CodCraft.api.services.CCGameListener;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.ffa.states.LobbyState;

public class GameListener extends CCGameListener {

	private CodCraftFFA plugin;
	
	public GameListener(CodCraftFFA plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onChange(WeatherChangeEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		for(Game<?> g : gm.getAllGames()) {
			if(g.getName().equalsIgnoreCase(e.getWorld().getName())) {
				if(e.toWeatherState()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void chooseteam(RequestJoinGameEvent e) {
		if(!(e.getGame().getPlugin() == plugin)) {
			return;
		}
		@SuppressWarnings("unchecked")
		Game<CodCraftFFA> game = (Game<CodCraftFFA>) e.getGame();
		plugin.getLogger().info(e.getPlayer().getName()+" has requested to join a FFA game named " + game.getName()+".");
		if(game.findTeamWithPlayer(e.getPlayer()) != null) {
			return;
		}
		for(Team t : game.getTeams()) {
			if(t.getMaxPlayers() != t.getPlayers().size()) {
				t.addPlayer(e.getPlayer());
				return;
			}
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				if(e.getBlockPlaced().getType() != Material.LEVER) {
					e.setCancelled(true);
				}
			}
		}
		/*for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {
			if(g.getPlugin() == plugin) {
				if(e.getBlockPlaced().getLocation().getWorld().getName().equalsIgnoreCase(g.getName())) {
					if(e.getBlockPlaced().getType() != Material.LEVER) {
						if(e.getPlayer().hasPermission("CodCraft.Admin")) {
							e.setCancelled(true);
						}	
					}
				}
			}
		}*/
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {
			if(g.getPlugin() == plugin) {
				if(e.getLocation().getWorld().getName().equalsIgnoreCase(g.getName())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onGameJoin(TeamPlayerGainedEvent e) {
		  final ScoreBoard SB = plugin.api.getModuleForClass(ScoreBoard.class);
		  final GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  final Game<?> g = gm.getGameWithTeam(e.getTeam());
		  
		  if( g == null) {
			return;
		  }
		  
		if(g.getPlugin() != plugin) {
			return;
		}
		plugin.getLogger().info(e.getPlayer().getName()+" has join a FFA game named " + g.getName()+".");
		final FFAGame game = (FFAGame) g;
		SB.addPlayerToScoreBoard(Bukkit.getPlayer(e.getPlayer().getName()), game);
		final String currentmap = game.map;
		final Player p = Bukkit.getPlayer(e.getPlayer().getName());
		if(p.getGameMode() != GameMode.SURVIVAL) {
			p.setGameMode(GameMode.SURVIVAL);
		}
		p.getInventory().clear();
		if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
			p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
			p.sendMessage(ChatColor.BLUE+game.Map1);
			p.sendMessage(ChatColor.BLUE+"or");
			p.sendMessage(ChatColor.BLUE+game.Map2);
			p.teleport(new Location(Bukkit.getWorld(g.getName()), 30, 40, 195));
		} else {
			p.teleport(plugin.Respawn(p, Bukkit.getWorld(game.getName()), currentmap, g));
		}
		
		
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
	public void onDamage(PlayerDamgedByWeaponEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			if(e.getGame().getCurrentState().getId().equalsIgnoreCase(new LobbyState((FFAGame) e.getGame()).getId())){
				e.setCancelled(true);
			}
		}
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
		player1.setFFADeaths(player1.getFFADeaths() + 1);
		ScoreBoard SB = plugin.api.getModuleForClass(ScoreBoard.class);
		
		if(e.getEntity().getKiller() instanceof Player) {

			Player k =(Player) e.getEntity().getKiller();
			Team team2 = g.findTeamWithPlayer(k);
			SB.updatePoint(k, g);
			team2.setScore(team2.getScore() + 1);
			if(checkwin(team2)) {
				GameWinEvent event = new GameWinEvent(team2.getName()+" has won!", team2, g);
				Bukkit.getPluginManager().callEvent(event);
				Bukkit.broadcastMessage(event.getWinMessage());
			} else {
			}
			CCPlayer player2 = ccplayerm.getPlayer(k);
			if(player2.getDeaths() == null) {
				player2.setDeaths(0);
			}
			player2.setKills(player2.getKills() + 1);
			player2.setFFAKills(player2.getFFAKills() + 1);
			team2.findPlayer(k).incrementKills(1);
		}
		updateallgui(g);
	}
	
	@EventHandler
	public void onQui(PlayerQuitEvent e) {
		GameManager gm = (GameManager)this.plugin.api.getModuleForClass(GameManager.class);
	    Game<?> g = gm.getGameWithPlayer(e.getPlayer());
	    if ((g != null) && (g.getPlugin() == this.plugin)) {
	      ScoreBoard SB = (ScoreBoard)this.plugin.api.getModuleForClass(ScoreBoard.class);
	      SB.removePlayerFromScoreBoard(e.getPlayer());
	    }
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
		TreeMap<String, String> sorted = new TreeMap<>();
		for(Team t : gm.getGameWithPlayer(p).getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				if(sorted.containsKey(""+tp.getKills())) {
					String s = ""+tp.getKills() + "." + "0";
					while(sorted.containsKey(s)) {
						s = s + "0";
					} 
					sorted.put(""+s, tp.getName().substring(0, 4)+"K:"+tp.getKills()+"D:"+tp.getDeaths());
				} else {
					sorted.put(""+tp.getKills(), tp.getName().substring(0, 4)+"K:"+tp.getKills()+"D:"+tp.getDeaths());
				}
			}
		}
		NavigableMap<String, String> sorted2 = sorted.descendingMap();
		gui.updateplayerlist(p, sorted2);
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
	@EventHandler
	public void onWIn(GameWinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> game = gm.getGameWithTeam(e.getTeam());
		if(game == null) {
			return;
		}  
		if(game.getPlugin() != plugin) {
			return;
		}
		FFAGame g = (FFAGame) game;
		g.setState(new LobbyState((FFAGame) g));
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
			player.setFFAWins(player.getFFAWins() + 1);
		}
		Random rnd = new Random();
		g.Map1 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		g.Map2 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		while(g.Map1.equalsIgnoreCase(g.Map2)) {
			g.Map1 = plugin.maps.get(rnd.nextInt(plugin.maps.size()));
		}
		for(Team team : g.getTeams()) {
			for(TeamPlayer tp : team.getPlayers()) {
				tp.setDeaths(0);
				tp.setKills(0);
				Player p = Bukkit.getPlayer(tp.getName());
				p.teleport(new Location(Bukkit.getWorld(g.getName()), 30, 40, 195));
				p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
				p.sendMessage(ChatColor.BLUE+g.Map1);
				p.sendMessage(ChatColor.BLUE+"or");
				p.sendMessage(ChatColor.BLUE+g.Map2);
						
			}
			team.setScore(0);
		}
		ScoreBoard sb = plugin.api.getModuleForClass(ScoreBoard.class);
		for(Team team : g.getTeams()) {
			if(team != e.getTeam()) {
				for(TeamPlayer tp : team.getPlayers()) {
					CCPlayer player2 = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(tp.getName()));
					player2.setLosses(player2.getLosses() + 1);
					player2.setFFALosses(player2.getFFALosses() + 1);
					
					sb.resetPlayerScore(g, Bukkit.getPlayer(tp.getName()));
				}
			}
		}
		updateallgui(g);


	}
	
	  @EventHandler
	  public void interact(PlayerInteractEvent e)
	  {
	    GameManager gm = (GameManager)this.plugin.api.getModuleForClass(GameManager.class);
	    Game<?> game = gm.getGameWithPlayer(e.getPlayer());
	    if (game == null) {
	      return;
	    }
	    if (game.getPlugin() != this.plugin) {
	      return;
	    }
	    FFAGame g = (FFAGame)game;
	    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
	      Block clickedblock = e.getClickedBlock();
	      if ((clickedblock != null) && 
	        ((clickedblock.getState() instanceof Sign))) {
	        Sign sign = (Sign)clickedblock.getState();
	        String MAP = null;
	        if ((clickedblock.getLocation().getX() == 58.0D) && (clickedblock.getLocation().getY() == 41.0D) && (clickedblock.getLocation().getZ() == 195.0D)) {
	          if (sign.getLine(1).equalsIgnoreCase(g.Map1))
	            MAP = g.Map1;
	          else if (sign.getLine(1).equalsIgnoreCase(g.Map2))
	            MAP = g.Map2;
	        }
	        else if ((clickedblock.getLocation().getX() == 58.0D) && (clickedblock.getLocation().getY() == 42.0D) && (clickedblock.getLocation().getZ() == 195.0D)) {
	          if (sign.getLine(1).equalsIgnoreCase(g.Map1))
	            MAP = g.Map1;
	          else if (sign.getLine(1).equalsIgnoreCase(g.Map2)) {
	            MAP = g.Map2;
	          }
	        }
	        if (MAP != null)
	          e.getPlayer().performCommand("vote " + MAP);
	      }
	    }
	  }
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onRespawn(PlayerRespawnEvent e) {
		  GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		  Game<?> g = gm.getGameWithPlayer(e.getPlayer());  
		  if(g == null) {
			 return;
		  }
		if(!(g.getPlugin() == plugin)) {
			return;
		}
		FFAGame game = (FFAGame) g;
		Player p = e.getPlayer();
		final String currentmap = game.map;
		if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
			e.setRespawnLocation(new Location(Bukkit.getWorld(g.getName()), 30, 40, 195));
		} else {
			e.setRespawnLocation(plugin.Respawn(p, Bukkit.getWorld(g.getName()), currentmap, g));
		}
	}
	
	@EventHandler
	public void onLose(TeamPlayerLostEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				ScoreBoard sb = plugin.api.getModuleForClass(ScoreBoard.class);
				sb.removePlayerFromScoreBoard(Bukkit.getPlayer(e.getPlayer().getName()));
			}
		}
	}
	
	private boolean checkwin(Team t) {
		if(t.getScore() >= 25) return true;
		return false;
	}

}
