package com.codcraft.codcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.ScoreBoard;
import com.CodCraft.api.modules.Weapons;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.codcraft.game.states.LobbyState;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;

public class CodCraftListener implements Listener {
	
	private CodCraftPlugin plugin;

	public CodCraftListener(CodCraftPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onWin(GameWinEvent e) {
		if(e.getGame() instanceof CodCraftGame<?>) {
			CodCraftGame<?> game = (CodCraftGame<?>) e.getGame();
			game.setState(new LobbyState(0, game));
			CCPlayerModule ccplayerm = plugin.api.getModuleForClass(CCPlayerModule.class);
			ScoreBoard sb = plugin.api.getModuleForClass(ScoreBoard.class);
			for(Team t : e.getGame().getTeams()) {
				if(t.getName().equalsIgnoreCase(e.getTeam().getName())) {
					for(TeamPlayer tp : t.getPlayers()) {
						CCPlayer player1 = ccplayerm.getPlayer(tp.getName());
						player1.setWins(player1.getWins() + 1);
						tp.setDeaths(0);
						tp.setKills(0);
						Player p = Bukkit.getPlayer(tp.getName());
						p.teleport(new Location(Bukkit.getWorld(game.getName()), 30, 40, 195));
						p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
						p.sendMessage(ChatColor.BLUE+game.Map1.getName());
						p.sendMessage(ChatColor.BLUE+"or");
						p.sendMessage(ChatColor.BLUE+game.Map2.getName());
						sb.resetPlayerScore(game, Bukkit.getPlayer(tp.getName()));
					}
				} else {
					for(TeamPlayer tp : t.getPlayers()) {
						CCPlayer player1 = ccplayerm.getPlayer(tp.getName());
						player1.setDeaths(player1.getDeaths() + 1);
						tp.setDeaths(0);
						tp.setKills(0);
						Player p = Bukkit.getPlayer(tp.getName());
						p.teleport(new Location(Bukkit.getWorld(game.getName()), 30, 40, 195));
						p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
						p.sendMessage(ChatColor.BLUE+game.Map1.getName());
						p.sendMessage(ChatColor.BLUE+"or");
						p.sendMessage(ChatColor.BLUE+game.Map2.getName());
						sb.resetPlayerScore(game, Bukkit.getPlayer(tp.getName()));
					}
				}
				t.setScore(0);
			}
			Random rnd = new Random();
			game.Map1 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
			game.Map2 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
			while(game.Map1.getName().equalsIgnoreCase(game.Map2.getName())) {
				game.Map1 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
			}

			game.onWin(e);
			game.onGUIupdate();
			
		}
	}
	
	@EventHandler
	public void onChange(WeatherChangeEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		for(Game<?> g : gm.getAllGames()) {
			if(g instanceof CodCraftGame<?>) {
				CodCraftGame<?> game = (CodCraftGame<?>) g;
				if(g.getName().equalsIgnoreCase(e.getWorld().getName())) {
					if(!game.getCurrentMap().isWeather()) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)){
			if(g instanceof CodCraftGame<?>) {
				g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
				((CodCraftGame<?>) g).onGUIupdate();;
			      ScoreBoard SB = (ScoreBoard)this.plugin.api.getModuleForClass(ScoreBoard.class);
			      SB.removePlayerFromScoreBoard(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {
			if(g instanceof CodCraftGame<?>) {
				if(e.getLocation().getWorld().getName().equalsIgnoreCase(g.getName())) {
					if(!(((CodCraftGame<?>) g).getCurrentMap().isCreatureSpawn())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(PlayerDamgedByWeaponEvent e) {
		if(e.getGame() instanceof CodCraftGame<?>) {
			if(e.getGame().getCurrentState().getId().equalsIgnoreCase(new LobbyState(0, (CodCraftGame<?>) e.getGame()).getId())){
				if(((CodCraftGame<?>) e.getGame()).isFF()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g instanceof CodCraftGame<?>) {
				if(((CodCraftGame<?>) g).getCurrentMap().isBlockDamage()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler 
	public void onRespawn(final PlayerRespawnEvent e) {
		final GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());  
		if(g != null) {
			if(g instanceof CodCraftGame<?>) {
				CodCraftGame<?> game = (CodCraftGame<?>) g;
				final Player p = e.getPlayer();
				if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(0, game).getId())) {
					e.setRespawnLocation(new Location(Bukkit.getWorld(g.getName()), 30, 40, 195));
				} else {
					e.setRespawnLocation(game.getRespawnLocation(p));
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							
							
							Weapons weap = plugin.api.getModuleForClass(Weapons.class);
							List<String> w = new ArrayList<>();
							CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
							CCPlayer ccp = ccpm.getPlayer(p);
							w.add(ccp.getClass(ccp.getCurrentclass()).getGun());
							w.add(ccp.getClass(ccp.getCurrentclass()).getPerk1());
							w.add(ccp.getClass(ccp.getCurrentclass()).getPerk2());
							w.add(ccp.getClass(ccp.getCurrentclass()).getPerk3());
							w.add(ccp.getClass(ccp.getCurrentclass()).getEquipment());
							w.add(ccp.getClass(ccp.getCurrentclass()).getKillStreak());
							weap.requestWeapons(p, w);
							/*PlayerGetClassEvent event = new PlayerGetClassEvent(e.getPlayer(), gm.getGameWithPlayer(e.getPlayer()));
							Bukkit.getPluginManager().callEvent(event);
							for(ItemStack i : event.getItems()) {
								e.getPlayer().getInventory().addItem(i);
							}*/
							
						}
					}, 1);
				}

			}
		}
	}
	
	
	@EventHandler
	public void onExspotion(EntityExplodeEvent e) {
		if(e.getEntity() == null) {
			return;
		}
		for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {

			if(e.getEntity().getWorld().getName().equalsIgnoreCase(g.getName())) {
				if(g instanceof CodCraftGame<?>) {
					if(!(((CodCraftGame<?>) g).getCurrentMap().isBlockDamage())) {
						e.blockList().clear();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g instanceof CodCraftGame<?>) {
				if((((CodCraftGame<?>) g).getCurrentMap().isBlockDamage())) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onLose(TeamPlayerLostEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g instanceof CodCraftGame<?>) {
				ScoreBoard sb = plugin.api.getModuleForClass(ScoreBoard.class);
				sb.removePlayerFromScoreBoard(Bukkit.getPlayer(e.getPlayer().getName()));
			}
		}
	}
	
	@EventHandler
	public void chooseteam(RequestJoinGameEvent e) {
		if(!(e.getGame() instanceof CodCraftGame<?>)) {
			return;
		}
		
		CodCraftGame<?> game = (CodCraftGame<?>) e.getGame();
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
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(p);
		//System.out.println(g);
		if(g != null) {
			//System.out.println("1");
			if(g instanceof CodCraftGame<?>) {
				//System.out.println("2");
				CodCraftGame<?> game = (CodCraftGame<?>) g;
		    	e.getDrops().clear();
				Team team1 = g.findTeamWithPlayer(p);
				CCPlayerModule ccplayerm = plugin.api.getModuleForClass(CCPlayerModule.class);
				CCPlayer player1 = ccplayerm.getPlayer(p);
				team1.findPlayer(p).incrementDeaths(1);
				player1.setDeaths(player1.getDeaths() +1);
				ScoreBoard SB = plugin.api.getModuleForClass(ScoreBoard.class);
				if(e.getEntity().getKiller() instanceof Player) {
					Player k =(Player) e.getEntity().getKiller();
					Team team2 = g.findTeamWithPlayer(k);
					
					team2.setScore(team2.getScore() + 1);
					if(game.checkWin()) {
						//GameWinEvent event = new GameWinEvent(new ArrayList<>(team2.getPlayers()).get(0).getName()+" has won!", team2, game);
						GameWinEvent event = new GameWinEvent(team2.getName()+" has won!", team2, game);
						Bukkit.getPluginManager().callEvent(event);
						Bukkit.broadcastMessage(event.getWinMessage());
					}
					CCPlayer player2 = ccplayerm.getPlayer(k);
					player2.setKills(player2.getKills() + 1);
					team2.findPlayer(k).incrementKills(1);
					SB.updatePoint(k, g);
				}
				game.onGUIupdate();
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onGameJoin(TeamPlayerGainedEvent e) {
		final ScoreBoard SB = plugin.api.getModuleForClass(ScoreBoard.class);
		final GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(g != null) {
			if(g instanceof CodCraftGame<?>) {
				plugin.getLogger().info(e.getPlayer().getName()+" has join a CodCraft game named " + g.getName()+".");
				final CodCraftGame<?> game = (CodCraftGame<?> ) g;
				SB.addPlayerToScoreBoard(Bukkit.getPlayer(e.getPlayer().getName()), game);
				final Player p = Bukkit.getPlayer(e.getPlayer().getName());
				if(p.getGameMode() != GameMode.SURVIVAL) {
					p.setGameMode(GameMode.SURVIVAL);
				}
				p.getInventory().clear();
				if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(0, game).getId())) {
					p.sendMessage(ChatColor.BLUE+"Please vote for a map!");
					p.sendMessage(ChatColor.BLUE+game.Map1.getName());
					p.sendMessage(ChatColor.BLUE+"or");
					p.sendMessage(ChatColor.BLUE+game.Map2.getName());
					p.teleport(new Location(Bukkit.getWorld(g.getName()), 30, 40, 195));
				} else {
					p.teleport(game.getRespawnLocation(p));
				}
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						Weapons weap = plugin.api.getModuleForClass(Weapons.class);
						List<String> w = new ArrayList<>();
						CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
						CCPlayer ccp = ccpm.getPlayer(p);
						w.add(ccp.getClass(ccp.getCurrentclass()).getGun());
						w.add(ccp.getClass(ccp.getCurrentclass()).getPerk1());
						w.add(ccp.getClass(ccp.getCurrentclass()).getPerk2());
						w.add(ccp.getClass(ccp.getCurrentclass()).getPerk3());
						w.add(ccp.getClass(ccp.getCurrentclass()).getEquipment());
						w.add(ccp.getClass(ccp.getCurrentclass()).getKillStreak());
						weap.requestWeapons(p, w);
						game.onGUIupdate();
					}
				}, 3);
			}
		}

	}

}
