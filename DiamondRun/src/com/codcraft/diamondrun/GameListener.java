package com.codcraft.diamondrun;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;

public class GameListener implements Listener {
	
	private DiamondRun plugin;
	
	
	
	public GameListener(DiamondRun plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onRequest(RequestJoinGameEvent e) {
		Game<?> g = e.getGame();
		if(g != null) {
			if(g.getPlugin() == plugin) {
				g.findTeamWithName("Team").addPlayer(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onJoin(TeamPlayerGainedEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				DRGame game = (DRGame) g;
				if(game.map == null) {
					for(Entry<String, Location> en : game.spawns.entrySet()) {
						game.map = en.getKey();
						break;
					}
				}
				if(game.map != null) {
					Player p = Bukkit.getPlayer(e.getPlayer().getName());
					if(p != null) {
						ItemStack is = new ItemStack(Material.COOKIE, 1);
						ItemMeta im = is.getItemMeta();
						if(im != null) {
							im.addEnchant(Enchantment.KNOCKBACK, 10, true);
							is.setItemMeta(im);
						}
						p.getInventory().addItem(is);
						p.teleport(game.spawns.get(game.map));
					}
				}
			}
		} 
	}
	
	@EventHandler
	public void onEat(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					e.setFoodLevel(20);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onHurt(PlayerDamgedByWeaponEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			e.setCancelled(false);
			e.setSameteam(false);
		}
	}
	
	
	@EventHandler
	public void onDamge(EntityDamageEvent e) {
		
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(p != null) {
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					if(g.getPlugin() == plugin) {
						DRGame game = (DRGame) g;
						Double damage = e.getDamage();
						Double health = ((CraftPlayer)p).getHealth();
						if(damage >= health) {
							e.setCancelled(true);
							p.setHealth(20D);
							p.setExhaustion(1);
							p.setFoodLevel(20);
							p.teleport(game.spawns.get(game.map));
						}
					}
				}
				
			}
		}
	}
	
	@EventHandler
	public void onHit(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(e.getPlayer());
			if(g != null) {
				if(g.getPlugin() == plugin) {
					DRGame game = (DRGame) g;
					if(e.getClickedBlock() != null) {
						if(e.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {
							if(g.getRound() >= 3) {
								Random rnd = new Random();
								String currentmap = game.map;
								while(game.map.equals(currentmap)) {
									List<String> keys      = new ArrayList<String>(game.spawns.keySet());
									String       randomKey = keys.get(rnd.nextInt(keys.size()) );
									game.map = randomKey;
									game.getCurrentState().setTimeLeft(150);
								}
								g.setRound(0);
								Location loc = game.spawns.get(game.map);
								for(Team t : g.getTeams()) {
									for(TeamPlayer tp : t.getPlayers()) {
										Player p = Bukkit.getPlayer(tp.getName());
										if(p != null) {
											p.teleport(loc);
										}
									}
								}
							} else {
								g.setRound(g.getRound() + 1);
								for(Team t : g.getTeams()) {
									for(TeamPlayer tp : t.getPlayers()) {
										Player p = Bukkit.getPlayer(tp.getName());
										if(p != null) {
											p.teleport(game.spawns.get(game.map));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
