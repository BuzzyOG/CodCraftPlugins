package com.codcraft.ccssbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.listener.EntityDamagedInGameEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.lobby.LobbyModule;

public class SSBlistener extends CCGameListener {
	private CCSSBB plugin;
	private GameManager gm;
	public SSBlistener(CCSSBB instance) {
		plugin = instance;
		gm = plugin.api.getModuleForClass(GameManager.class);
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
	
	
	List<String> endermanusers = new ArrayList<>();
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(final PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
				e.setCancelled(true);
				if(!endermanusers.contains(e.getPlayer().getName())) {
					List<Block> list = e.getPlayer().getLineOfSight(null, 100); 
					if(list != null) {
						Location loc = list.get(list.size() - 1).getLocation();
						loc.setY(loc.getY() + 1);
						e.getPlayer().teleport(loc);
						endermanusers.add(e.getPlayer().getName());
						e.getPlayer().updateInventory();
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								endermanusers.remove(e.getPlayer().getName());
							}
						}, 200);
					}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onFire(EntityShootBowEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					SSBB game = (SSBB) g;
					if(game.playerclass.get(p.getName()).equalsIgnoreCase("wither")) {
						e.setCancelled(true);
						Projectile wither = p.launchProjectile(WitherSkull.class);
						if(wither instanceof WitherSkull) {
							//WitherSkull skull = (WitherSkull) wither;
							
						}
						
					} else if(game.playerclass.get(p.getName()).equalsIgnoreCase("creeper")) {
						TNTPrimed tnt = (TNTPrimed) p.getPlayer().getWorld().spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
						tnt.setFuseTicks(5);
						if(e.getForce() <= .5) {
							Vector v1 = e.getProjectile().getVelocity();
							v1.multiply(.5);
							tnt.setVelocity(v1);
							
						} else {
							Vector v1 = e.getProjectile().getVelocity();
							v1.multiply(1);
							tnt.setVelocity(v1);
						}
						
						
						
						e.setCancelled(true);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onRequest(RequestJoinGameEvent e) {
		Game<?> g = e.getGame();
		if(g.getPlugin() == plugin) {
			if(!g.getCurrentState().getId().equalsIgnoreCase(new InGameState(g).getId())) {
				for(Team t : g.getTeams()) {
					if(t.containsPlayer(e.getPlayer())){
						return;
					}
					if(t.getPlayers().size() == 0) {
						t.addPlayer(e.getPlayer());
						plugin.getLogger().info(e.getPlayer().getName()+" has requested to join a SSB game named " + g.getName()+".");
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				g.findTeamWithPlayer(e.getPlayer()).removePlayer(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void teamjoin(TeamPlayerGainedEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithTeam(e.getTeam());
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				plugin.getLogger().info(e.getPlayer().getName()+" has join a SSB game named " + g.getName()+".");
				final Player p = Bukkit.getPlayer(e.getPlayer().getName());
				p.teleport(new Location(Bukkit.getWorld(g.getName()), 0, 65, -23));
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						onGUI(p);
						
					}
				}, 1);
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.getPlayer() instanceof Player) {
			Player p = e.getPlayer();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(!(g == null)) {
				if(g.getPlugin() == plugin) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				TeamPlayer p1 = g.findTeamWithPlayer(p).findPlayer(p);
				p1.incrementDeaths(1);
				CCPlayer player1 = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(p);
				player1.setDeaths(player1.getDeaths() + 1);
				player1.setSSBDeaths(player1.getSSBDeaths() + 1);
				if(e.getEntity().getKiller() instanceof Player) {
					CCPlayer player2 = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer((Player)e.getEntity().getKiller());
					player2.setSSBKills(player2.getSSBKills() + 1);
					player2.setKills(player2.getKills() + 1);
				}
				int i = 0;
				String player = null;
				for(Team t : g.getTeams()) {
					for(TeamPlayer p2 : t.getPlayers()) {
						if(p2.getDeaths() < 5) {
							i++;;
							player = p2.getName();
						}
					}
				}
				onGUI(p);
				if(p1.getDeaths() == 5) {
					p.getInventory().clear();
					plugin.api.getModuleForClass(Broadcast.class).BroadCastMessage(g, ChatColor.BLUE+p1.getName()+" is now out of the game");
				}
				if(i == 1) {
					GameWinEvent event = new GameWinEvent(player+"has won the "+g.getName() +" game!", g.findTeamWithPlayer(new TeamPlayer(player)), g);
					Bukkit.getPluginManager().callEvent(event);
					Bukkit.broadcastMessage(event.getWinMessage());
				}

			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		final Random rnd = new Random();
		final Player p = e.getPlayer();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				final SSBB game = (SSBB) g;
				final TeamPlayer p1 = g.findTeamWithPlayer(p).findPlayer(p);
				if(p1.getDeaths() >= 5) {
					p.getInventory().clear();
					p.setAllowFlight(true);
					Location loc = plugin.specspot.get(game.map);
					e.setRespawnLocation(new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ()));
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0));
							
						}
					}, 2);
				} else {
					p.getInventory().clear();
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							String s = game.playerclass.get(p.getName());
							if(s.equalsIgnoreCase("skeleton")) {
								skeleton(p);
							} else if (s.equalsIgnoreCase("zombie")) {
								zombie(p);
							} else if (s.equalsIgnoreCase("spider")) {
								spider(p);
							} else if (s.equalsIgnoreCase("creeper")) {
								creeper(p);
							} else if (s.equalsIgnoreCase("enderman")) {
								enderman(p);
							} else if (s.equalsIgnoreCase("blaze")) {
								blase(p);
							} else if (s.equalsIgnoreCase("cati")) {
								cati(p);
							} else if (s.equalsIgnoreCase("wither")) {
								wither(p);

							}
							
						}
					}, 20);
					
					Location loc1 = plugin.spawnpoints.get(game.map).get(rnd.nextInt(plugin.spawnpoints.get
									(game.map).size())); 
					
					e.setRespawnLocation(new Location(Bukkit.getWorld(g.getName()), loc1.getX(), loc1.getY(), loc1.getZ()));
				}
			}
		}
	}
	@EventHandler
	public void onWin(GameWinEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			TeamPlayer tplayer = null;
			for(TeamPlayer tp : e.getTeam().getPlayers()) {
				tplayer = tp;
			}
			CCPlayer player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(tplayer.getName()));
			player.setWins(player.getWins() + 1);
			player.setSSBWins(player.getSSBWins() + 1);
			for(Team t : e.getGame().getTeams()) {
				if(t != e.getTeam()) {
					for(TeamPlayer tp : t.getPlayers()) {
						CCPlayer player2 = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(tp.getName()));
						player2.setLosses(player2.getLosses() + 1);
						player2.setSSBLosses(player2.getSSBLosses() + 1);
					}
				}
				for(TeamPlayer p : t.getPlayers()) {
					t.removePlayer(p);
					Player p2 = Bukkit.getPlayer(p.getName());
					p2.getInventory().clear();

				    for (PotionEffect effect : p2.getActivePotionEffects()){
				        p2.removePotionEffect(effect.getType());
				    }
				    p2.setFlying(false);
					if(p2.getAllowFlight()) {
						p2.setAllowFlight(false);
						Bukkit.broadcastMessage(p2.getName()+" can fly =(");
					}
					if(!p2.isDead()) {
						Bukkit.dispatchCommand(p2, "spawn");
						//p2.teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
					}
					onGUI(p2);
					
				}
			}
			e.getGame().deinitialize();
			e.getGame().initialize();
			LobbyModule lm = plugin.api.getModuleForClass(LobbyModule.class);
			lm.UpdateSign(lm.getLobby(e.getGame().getName()));
			
		}
	}
	
	@EventHandler
	public void onpress(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(e.getClickedBlock().getType() == Material.STONE_BUTTON) {
						if(e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.SIGN ||
								e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.WALL_SIGN ||
								e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.SIGN_POST) {
							Sign s = (Sign) e.getClickedBlock().getRelative(BlockFace.UP).getState();
							if(s.getLine(0).equalsIgnoreCase("CCSSB")) {
								if(s.getLine(1).equalsIgnoreCase("skeleton")) {
									skeleton(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("zombie")) {
									zombie(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("spider")) {
									spider(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("creeper")) {
									creeper(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("enderman")) {
									enderman(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("blaze")) {
									blase(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("cati")) {
									cati(p);
									tomappick(p);
								} else if (s.getLine(1).equalsIgnoreCase("wither")) {
									wither(p);
									tomappick(p);
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	
	private void tomappick(Player p) {
		p.teleport(new Location(p.getWorld(), 57, 64, -45));
		
	}
	
	@EventHandler
	public void ongameDamage(EntityDamagedInGameEvent e) {
		
		if(e.getGame().getPlugin() == plugin) {
			e.setCancelled(true);
		}
	}


	@SuppressWarnings("deprecation")
	private void wither(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "wither");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			c.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(40, 40, 40));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(40, 40, 40));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(40, 40, 40));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
			ItemStack wither = new ItemStack(397, 1, (short)SkullType.WITHER.ordinal());
			p.getInventory().setHelmet(wither);
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			bow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			p.getInventory().addItem(bow);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 5));
			p.updateInventory();
		}
	}

	@SuppressWarnings("deprecation")
	private void cati(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "cati");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			c.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			c.addEnchantment(Enchantment.THORNS, 2);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(56, 161, 65));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(56, 161, 65));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(56, 161, 65));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			ItemStack wither = new ItemStack(Material.CACTUS);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 2));
			ItemStack sword = new ItemStack(Material.WOOD_SWORD);
			sword.addEnchantment(Enchantment.KNOCKBACK, 1);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			p.getInventory().addItem(sword);
			p.updateInventory();
		}

		
	}

	@SuppressWarnings("deprecation")
	private void blase(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "blaze");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(255, 255, 0));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(255, 255, 0));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(255, 255, 0));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			Enum<?> type = CustomSkullType.BLAZE;
			ItemStack wither = CCSSBB.Skull((CustomSkullType)type);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 5));
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
			p.getInventory().addItem(bow);
			ItemStack rod = new ItemStack(Material.BLAZE_ROD);
			rod.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			rod.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			p.getInventory().addItem(rod);
			p.getInventory().addItem(new ItemStack(Material.ARROW));
			p.updateInventory();
		}

	}

	@SuppressWarnings("deprecation")
	private void enderman(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "enderman");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(0, 0, 0));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(0, 0, 0));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(0, 0, 0));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			Enum<?> type = CustomSkullType.ENDERMAN;
			ItemStack wither = CCSSBB.Skull((CustomSkullType)type);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 2));
			ItemStack rod = new ItemStack(Material.EYE_OF_ENDER);
			rod.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			rod.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			p.getInventory().addItem(rod);
			ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 1);
			p.getInventory().addItem(pearl);
			p.updateInventory();
			
		}
		
		
	}

	@SuppressWarnings("deprecation")
	private void creeper(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "creeper");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(50, 255, 50));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(50, 255, 50));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(50, 255, 50));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			ItemStack wither = new ItemStack(397, 1, (short)SkullType.CREEPER.ordinal());
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 2));
			ItemStack rod = new ItemStack(Material.SULPHUR);
			rod.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			p.getInventory().addItem(rod);
			ItemStack damagep = new ItemStack(373, 64, (short) 16396);
			p.getInventory().addItem(damagep);
			p.getInventory().addItem(damagep);
			p.getInventory().addItem(damagep);
			p.updateInventory();
			
			
		}
		
	}

	@SuppressWarnings("deprecation")
	private void spider(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "spider");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(57, 49, 42));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(57, 49, 42));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(57, 49, 42));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			Enum<?> type = CustomSkullType.SPIDER;
			ItemStack wither = CCSSBB.Skull((CustomSkullType)type);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 4));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500000, 2));
			ItemStack rod = new ItemStack(Material.SPIDER_EYE);
			rod.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			rod.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().addItem(rod);
			p.updateInventory();
		}
		
	}

	@SuppressWarnings("deprecation")
	private void zombie(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "zombie");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(org.bukkit.Color.fromRGB(40, 0, 160));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(org.bukkit.Color.fromRGB(55, 136, 248));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(org.bukkit.Color.fromRGB(40, 0, 160));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			ItemStack wither = getSkullItemStack(1,(byte) 2);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 2));
			ItemStack rod = new ItemStack(Material.IRON_SPADE);
			rod.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			rod.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().addItem(rod);
			p.updateInventory();
		}
		
	}

	@SuppressWarnings("deprecation")
	private void skeleton(Player p) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);

		if(g.getPlugin() == plugin) {
			SSBB game = (SSBB) g;
			game.playerclass.put(p.getName(), "skeleton");
			ItemStack c = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta lam = (LeatherArmorMeta) c.getItemMeta();
			lam.setColor(Color.fromRGB(210, 210, 210));
			c.setItemMeta(lam);
			p.getInventory().setChestplate(c);
			ItemStack l = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta llam = (LeatherArmorMeta) l.getItemMeta();
			llam.setColor(Color.fromRGB(210, 210, 210));
			l.setItemMeta(llam);
			p.getInventory().setLeggings(l);
			ItemStack b = new ItemStack(Material.LEATHER_BOOTS, 1);
			b.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
			b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			LeatherArmorMeta ban = (LeatherArmorMeta) b.getItemMeta();
			ban.setColor(Color.fromRGB(210, 210, 210));
			b.setItemMeta(ban);
			p.getInventory().setBoots(b);
			ItemStack wither = getSkullItemStack(1, (byte) 0);
			p.getInventory().setHelmet(wither);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500000, 2));
			ItemStack rod = new ItemStack(Material.BOW);
			rod.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			rod.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			p.getInventory().addItem(rod);
			p.getInventory().addItem(new ItemStack(Material.ARROW));
			p.updateInventory();
		}
		
	}
	@SuppressWarnings("deprecation")
	public static ItemStack getSkullItemStack(int amount, byte data) {
		amount = Math.max(0, amount);
		return (new ItemStack(397, amount, (short) 0, data));
	}
	
	public void onGUI(Player p) {

		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				ArrayList<String> test = new ArrayList<>();
				for(Team t : g.getTeams()) {
					for(TeamPlayer p2 : t.getPlayers()) {
						if(p2.getName().length() > 10) {
							if(p2.getDeaths() == 0) {
								test.add(p2.getName().substring(0, 8) + ChatColor.GREEN+"5/5" );
							} else if(p2.getDeaths() == 1) {
								test.add(p2.getName().substring(0, 8) + ChatColor.GREEN+"4/5" );
							} else if(p2.getDeaths() == 2) {
								test.add(p2.getName().substring(0, 8) + ChatColor.YELLOW+"3/5" );
							} else if(p2.getDeaths() == 3) {
								test.add(p2.getName().substring(0, 8) + ChatColor.RED+"2/5" );
							} else if(p2.getDeaths() == 4) {
								test.add(p2.getName().substring(0, 8) + ChatColor.RED+"1/5" );
							} else if(p2.getDeaths() == 5) {
								test.add(p2.getName().substring(0, 8) + ChatColor.RED+"0/5" );
							}
						} else {
							if(p2.getDeaths() == 0) {
								test.add(p2.getName() + ChatColor.GREEN+"5/5" );
							} else if(p2.getDeaths() == 1) {
								test.add(p2.getName() + ChatColor.GREEN+"4/5" );
							} else if(p2.getDeaths() == 2) {
								test.add(p2.getName() + ChatColor.YELLOW+"3/5" );
							} else if(p2.getDeaths() == 3) {
								test.add(p2.getName() + ChatColor.RED+"2/5" );
							} else if(p2.getDeaths() == 4) {
								test.add(p2.getName() + ChatColor.RED+"1/5" );
							} else if(p2.getDeaths() == 5) {
								test.add(p2.getName() + ChatColor.RED+"0/5" );
							}
						}

					}
				}
				for(Team t : g.getTeams()) {
					for(TeamPlayer p2 : t.getPlayers()) {
						Player p3 = Bukkit.getPlayer(p2.getName());
						plugin.api.getModuleForClass(GUI.class).updateplayerlist(p3, test);
					}
				}
				
			}
		}
	}
	

	@EventHandler
	public void onButtonpress(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin) {
				SSBB game = (SSBB) g;
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(e.getClickedBlock().getType() == Material.STONE_BUTTON) {
						if(e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.SIGN ||
								e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.WALL_SIGN ||
								e.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.SIGN_POST) {
							Sign s = (Sign) e.getClickedBlock().getRelative(BlockFace.UP).getState();
							if(s.getLine(0).equalsIgnoreCase("CCSSB")) {
								if(plugin.spawnpoints.containsKey(s.getLine(1))) {
									List<TeamPlayer> teapPlayers = new ArrayList<>();
									for(Team t : g.getTeams()) {
										for(TeamPlayer tp : t.getPlayers()) {
											teapPlayers.add(tp);
										}
									}
									if(teapPlayers.size() <= 1) {
										e.getPlayer().sendMessage("Need to have more then one person!");
										return;
									}
									game.map = s.getLine(1);
									for(Team t : g.getTeams()) {
										Location loc = plugin.pregamespots.get(game.map).get(Integer.parseInt(t.getName())-1);
										Location loc2 = new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ());
										for(TeamPlayer p1 : t.getPlayers()) {
											Player p2 = Bukkit.getPlayer(p1.getName());
											p2.teleport(loc2);
											p2.setHealth(20D);
											p2.setFoodLevel(20);
										}
									}
									g.setState(new InGameState(g));
									runtask(g);
								}
							}
						}			
					}
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		if(!(g == null)) {
			if(g.getPlugin() == plugin) {
				SSBB game = (SSBB) g;
				if(game.playerclass.get(p.getName()) == null) {
					return;
				}
				if(game.playerclass.get(p.getName()).equalsIgnoreCase("spider")) {
					if(!p.hasPermission("codcraft.climb")) {
						return;
					}
					if(!e.getPlayer().isSneaking()) {
						return;
					}
					if(e.getPlayer().getTargetBlock(null, 1) == null) {
						return;
					}
					if(e.getPlayer().getTargetBlock(null, 1).getType() == Material.AIR) {
						return;
					}
				      Vector dir = p.getVelocity();
				      Vector vec = new Vector(dir.getX(), 0.40D, dir.getZ());
				      p.setVelocity(vec);
				}
			}
		}
	}

	private void runtask(final Game<?> g) {
		final Random rnd = new Random();
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
			@Override
			public void run() {
				for(Team t : g.getTeams()) {
					SSBB game = (SSBB) g;
					Location loc1 = plugin.spawnpoints.get(game.map).get(rnd.nextInt(plugin.spawnpoints.get(game.map).size())); 
					Location loc2 = new Location(Bukkit.getWorld(g.getName()), loc1.getX(), loc1.getY(), loc1.getZ());
					for(TeamPlayer p4 : t.getPlayers()) {
						Player p5 = Bukkit.getPlayer(p4.getName());
						p5.teleport(loc2);
					}
				}
			}
		}, 100);
	}

}
