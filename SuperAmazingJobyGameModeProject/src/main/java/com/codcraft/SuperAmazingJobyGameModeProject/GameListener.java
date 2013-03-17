package com.codcraft.SuperAmazingJobyGameModeProject;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.CCTagAPI;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class GameListener implements Listener {
	
	private SAJGMP plugin;
	private GameManager gm;
	private CCTagAPI tagapi;
	private CCPlayerModule ccpm;
	private GUI gui;
	private Broadcast broadcast;
	private ArrayList<String> list5 = new ArrayList<>();
	
	
	public GameListener(SAJGMP plugin) {
		this.plugin = plugin;
		gm = this.plugin.api.getModuleForClass(GameManager.class);
		tagapi = this.plugin.api.getModuleForClass(CCTagAPI.class);
		ccpm = this.plugin.api.getModuleForClass(CCPlayerModule.class);
		gui = this.plugin.api.getModuleForClass(GUI.class);
		broadcast = this.plugin.api.getModuleForClass(Broadcast.class);
	}
	
	@EventHandler
	public void onReq(RequestJoinGameEvent e) {
		if(!(e.getGame().getPlugin() == plugin)) {
			return;
		}
		int players = 5;
		Team team = null;
		for(Team t : e.getGame().getTeams()) {
			if(players > t.getPlayers().size()) {
				players = t.getPlayers().size();
				team = t;
			}
		}
		if(players == 5) {
			e.setCancelled(true);
		} else {
			team.addPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onJoin(TeamPlayerGainedEvent e) {
		final Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				Bukkit.getPlayer(e.getPlayer().getName()).sendMessage("You are on " +e.getTeam().getColor() + e.getTeam().getColor().name() +ChatColor.WHITE + " Team!");
				
				int i = Integer.parseInt(e.getTeam().getName());
				Location loc = plugin.spawnpoint.get(plugin.games.get(g.getId()).map).get(i);
				Location loc1 = new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ());
				broadcast.BroadCastMessage(e.getPlayer(), ""+loc1.getX());
				broadcast.BroadCastMessage(e.getPlayer(), ""+loc1.getY());
				broadcast.BroadCastMessage(e.getPlayer(), ""+loc1.getZ());
				Bukkit.getPlayer(e.getPlayer().getName()).teleport(loc1);
				giveiteams(Bukkit.getPlayer(e.getPlayer().getName()));
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						tagapi.updateGameColor(g);
						onUpdate(g);
					}
				}, 5);
			}
		}
	}
	
	public void onUpdate(Game<?> g) {
		checkwin(g);
		guiupdate(g);
	}
	
	private void guiupdate(Game<?> g) {
		ArrayList<String> string = new ArrayList<>();
		for(Team t : g.getTeams()) {
			string.add(t.getColor()+t.getColor().name());
			for(TeamPlayer tp : t.getPlayers()) {
				string.add(t.getColor()+tp.getName());
			}
		}
		for(Team t : g.getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				gui.updateplayerlist(Bukkit.getPlayer(tp.getName()), string);
			}
		}
		
	}

	private void checkwin(Game<?> g) {
		for(Team t : g.getTeams()) {
			if(t.getScore() > 49) {
				Win(t);
			}
		}
		
	}

	private void Win(Team t) {
		GameWinEvent event = new GameWinEvent(t.getColor()+t.getName() + ChatColor.WHITE+" has won the joby game", t, gm.getGameWithTeam(t));
		Bukkit.getPluginManager().callEvent(event);
		broadcast.BroadCastMessage(event.getWinMessage());
	}

	private void giveiteams(Player p) {
		p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		if(e.getEntity().getKiller() instanceof Player) {
			
			Player p = e.getEntity();
			Game<?> g = gm.getGameWithPlayer(p);
			if(!(g == null)) {
				if(g.getPlugin() == plugin) {
					Player k = e.getEntity().getKiller();
					Team tk = gm.getGameWithPlayer(p).findTeamWithPlayer(k);
					TeamPlayer tpp = gm.getGameWithPlayer(p).findTeamWithPlayer(p).findPlayer(p);
					TeamPlayer tpk = tk.findPlayer(k);
					CCPlayer cp = ccpm.getPlayer(p);
					CCPlayer ck = ccpm.getPlayer(k);
					tk.setScore(tk.getScore() + 1);
					tpp.incrementDeaths(1);
					tpk.incrementKills(1);
					cp.setKills(cp.getKills() + 1);
					ck.setKills(ck.getKills() + 1);
					onUpdate(g);
				}
			}

		}
	}
	
	
	@EventHandler
	public void repsawn(PlayerRespawnEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				giveiteams(e.getPlayer());
				int i = Integer.parseInt(g.findTeamWithPlayer(e.getPlayer()).getName());
				Location loc = plugin.spawnpoint.get(plugin.games.get(g.getId()).map).get(i);
				Location loc1 = new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ());
				e.setRespawnLocation(loc1);
			}
		}
	}
	
	@EventHandler
	public void onlaunch(ProjectileLaunchEvent e) {
		
		if(e.getEntity() instanceof Snowball) {
			if(e.getEntity().getShooter() instanceof Player) {
				if(gm.getGameWithPlayer((Player) e.getEntity().getShooter()) == null) {
					return;
				}
				if(gm.getGameWithPlayer((Player) e.getEntity().getShooter()).getPlugin() != plugin) {
					return;
				}
				((Player)e.getEntity().getShooter()).getInventory().setItemInHand(new ItemStack(Material.SNOW_BALL));
			}
		}
	}
	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		
		
		if(gm.getGameWithPlayer((Player) e.getPlayer()) == null) {
			return;
		}
		if(gm.getGameWithPlayer((Player) e.getPlayer()).getPlugin() != plugin) {
			return;
		}
		
		if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
			ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
			if(meta.getDisplayName() != null) {
				if(meta.getDisplayName().equalsIgnoreCase("CrossBow")) {
					if(list5.contains(e.getPlayer().getName())) {
						e.setCancelled(true);
					} else {
						Arrow v = e.getPlayer().launchProjectile(Arrow.class);
						Vector v1 = v.getVelocity();
						v1.multiply(5);
						v.setVelocity(v1);
						list5.add(e.getPlayer().getName());
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								list5.remove(e.getPlayer().getName());
								
							}
						}, 45);
					}
				}
			}
		}
		
		if(e.getPlayer().getItemInHand().getType() == Material.FIREBALL) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR ) {
				e.getPlayer().launchProjectile(SmallFireball.class);
			}
		}
	}
	
	@EventHandler
	public void entitysbow(EntityShootBowEvent e) {
		if(e.getEntity() instanceof Player) {
			ItemMeta meta = e.getBow().getItemMeta();
			if(meta.getDisplayName() != null) {
				if(meta.getDisplayName().equalsIgnoreCase("CrossBow")) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler (priority =EventPriority.LOWEST)
	public void onDamage(PlayerDamgedByWeaponEvent e) {
		if(e.getCause() == DamageCause.ARROW) {
			Player p = e.getDamager();
			ItemStack bow = null;
			if(p.getItemInHand().getType() == Material.BOW) {
				bow = p.getItemInHand();
			} else {
				for(ItemStack i : p.getInventory()) {
					if(i != null) {
						if(i.getType() == Material.BOW) {
							bow = i;
							break;
						}
					}
				}
			}
			if(bow != null) {
				ItemMeta meta = bow.getItemMeta();
				if(meta.getDisplayName().equalsIgnoreCase("CrossBow")) {
					e.setDamage(5);
				} 
			}	
		}
	}
	
	
	
	
	

}
