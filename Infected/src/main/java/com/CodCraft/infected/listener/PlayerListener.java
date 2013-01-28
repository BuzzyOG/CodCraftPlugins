package com.CodCraft.infected.listener;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.infected.CodCraft;
import com.CodCraft.infected.Users;
import com.CodCraft.infected.GM.Game;
import com.CodCraft.infected.api.GameState.Gamemodes;


public class PlayerListener implements Listener {
	   private CodCraft plugin;

	   public PlayerListener(CodCraft plugin) {
	      this.plugin = plugin;
	   }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	p.sendMessage("====================CodCraft===================");
    	p.sendMessage("==Welcome to CodCraft. Plugin made by Joby890==");
    	p.sendMessage("=====With help by fireblast709 and JamyDev=====");
    	p.sendMessage("====================Enjoy!=====================");
    	if(plugin.api.getGamemode().getGameMode() == Gamemodes.LOBBY) {
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "Please vote for the next map:");
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    Game.world1);
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    "or");
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    Game.world2);
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "using /vote <mapName>");
    	}
    	plugin.api.getTeams().setTeam(p, 1);
    	plugin.api.getPlayers().AddPlayer(p);
    	plugin.api.getPlayers().Addplaying(p);
    	plugin.getGame().Savedata();

    	if(Users.PlayerPerk1.get(p).equalsIgnoreCase("Marathon")) {
    		plugin.api.getPerks().addMarathon(p);
    	} else {
    		plugin.api.getPerks().removeMarathon(p);
    	}
		if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
			plugin.api.getPerks().AddLightuser(p);
    	} else {
    		plugin.api.getPerks().RemoveLightuser(p);
    	}
		plugin.api.getWeapons().GiveKnife(p);
		plugin.api.getWeapons().GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
    
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
    	e.getDrops().clear();
    	e.getDrops().add(new ItemStack(Material.getMaterial(54), 1));
    	if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
        	Player p = (Player) e.getEntity();
        	Player k = (Player) e.getEntity().getKiller();
        	plugin.api.getPlayers().AddKill(k);
        	plugin.api.getPlayers().AddDeath(p);
        	plugin.api.getGameManager().setTeamscore(plugin.api.getTeams().getTeam(k), plugin.api.getGameManager().getTeamScore(plugin.api.getTeams().getTeam(k))+1);
        	plugin.api.getGui().updatelist();
        	plugin.api.getWeapons().SetSniperStage(p, 1);
        	plugin.api.getGameManager().DetectWin(75);
        	plugin.api.getWeapons().RemoveC4(p);
        	plugin.api.getKillStreaks().setKills(k, plugin.api.getKillStreaks().getKills(k) + 1);
        	for (String s : plugin.api.getPlayers().Whoplaying()) {
        		Player all = Bukkit.getPlayer(s);
        		plugin.api.getKillStreaks().checkPlayerKillstreak(all);
        	}
        	plugin.api.getPerks().SetUnUsedLastStand(p);
        	if(plugin.api.getGamemode().getGameMode() == Gamemodes.INGAME) {
        		plugin.api.getTeams().setTeam(p, 2);
        	}
    	}
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
    	Player p = e.getPlayer();
    	e.setRespawnLocation(plugin.api.getTelport().Respawn(p, plugin.api.getGameManager().GetCurrentWorld()));
    }
    @EventHandler
    public void onDamage(PlayerDamgedByWeaponEvent e) {
 	   if(e.getCause() == DamageCause.ARROW) {
 		   if(!e.isSameteam()) {
 			   e.setDamage(weap.DetectArrowDamage(Users.PlayerPerk2.get(e.getDamagee()), Users.PlayerPerk2.get(e.getDamager()), Users.PlayerGun.get(e.getDamager())));
 		   }
 	   } else if (e.getCause() == DamageCause.EQUIPMENT) {
 		   if(!e.isSameteam()) {
 			   e.setDamage(20);
 		   }
 	   }
    }
    @EventHandler
    public void ShootBow(EntityShootBowEvent e) {
    	if(!(e.getEntity() instanceof Player)) {
    		return;
    	}
    	Player p = (Player) e.getEntity();
    	if(Users.PlayerGun.get(p).equalsIgnoreCase("Sniper")) {
			Vector v = e.getEntity().getLocation().getDirection();
    		plugin.api.getWeapons().ShootSniper(p, v);
    	}
    	e.setCancelled(true);
    }
    @EventHandler
    public void GunInteractEvent(PlayerInteractEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
    		if(e.getPlayer().getItemInHand().getType() == Material.GREEN_RECORD) {
    			plugin.api.getKillStreaks().ShootDeathMachine(p);
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.MAGMA_CREAM) {
    			plugin.api.getKillStreaks().lanuchnuke(p);
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.STICK) {
    			plugin.api.getWeapons().CreateC4Ex(p);
    			
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
    			plugin.api.getWeapons().ZoomUp1(p, Users.PlayerAttactment.get(p));
    		}
    	}
    	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
    			
    	    	if(Users.PlayerGun.get(p).equalsIgnoreCase("MachineGun")) {
    	    		plugin.api.getWeapons().ShootMachineGun(p);
    	    	}
    	    	if(Users.PlayerGun.get(p).equalsIgnoreCase("SubMachine")) {
    	    		plugin.api.getWeapons().ShootSubMachine(p);
    	    	}
    		}
    	}

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	plugin.api.getPlayers().RemovePlayer(p);
    	plugin.api.getPlayers().RemovePlaying(p);
    	plugin.api.getPlayers().RemoveSpectators(p);
    	plugin.api.getTeams().RemovePlayer(p);
    	plugin.api.getPerks().removeMarathon(p);
    	plugin.api.getPerks().RemoveLightuser(p);
    	p.getInventory().clear();
    }
    @EventHandler
    public void TagAPI(PlayerReceiveNameTagEvent e) {
    	Player p = e.getNamedPlayer();
			if (plugin.api.getTeams().getTeam(p) == 1) {
				e.setTag(ChatColor.RED + e.getNamedPlayer().getName());
			}
			if (plugin.api.getTeams().getTeam(p) == 2) {
				e.setTag(ChatColor.BLUE + e.getNamedPlayer().getName());
			}	
		
    }
    @EventHandler
    public void onResawn(PlayerRespawnEvent e) {
    	Player p = e.getPlayer();
    	plugin.api.getWeapons().GiveKnife(p);
    	plugin.api.getWeapons().GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
    }
	@EventHandler
	public void OnSignClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();


		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) e.getClickedBlock().getState();
				Integer j = plugin.api.getCac().GetBox(p);
				if (s.getLine(0).equals("Lobby" + j)) {
					if (s.getLine(1).equals("Class")) {
						if (s.getLine(3).equals("1"));
								s.setLine(3, "2");
								s.update();	
						//set Other to current class for 2
					}
					if (s.getLine(1).equals("Gun")) {
						if (s.getLine(3).equals("")) {
							s.setLine(3, "Sniper");
						}
						int index = plugin.api.getWeapons().getweapons("Guns").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("Guns").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("Guns").get(index);
						//String nextGun = CodCraftCaCOptions.Guns.get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerGun.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk1")) {
						int index = plugin.api.getWeapons().getweapons("Perk1").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("Perk1").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("Perk1").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk1.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk2")) {
						int index = plugin.api.getWeapons().getweapons("Perk2").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("Perk2").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("Perk2").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk2.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk3")) {
						int index = plugin.api.getWeapons().getweapons("Perk3").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("Perk3").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("Perk3").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk3.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Equipment")) {
						int index = plugin.api.getWeapons().getweapons("Equipment").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("Equipment").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("Equipment").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.Playerequipment.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("KillStreaks")) {
						int index = plugin.api.getWeapons().getweapons("KillStreaks").indexOf(s.getLine(3)) + 1;
						if(index >= plugin.api.getWeapons().getweapons("KillStreaks").size()){
						    index = 0;
						}
						String nextGun = plugin.api.getWeapons().getweapons("KillStreaks").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerKIllStreaks.put(p, s.getLine(3));
					}
				}
			}
		}
	}
	   @EventHandler
		public void Scavenger(PlayerPickupItemEvent e) {
			final Player p = e.getPlayer();
			plugin.getLogger().info(e.getItem().getItemStack().getType().toString());
			if(e.getItem().getItemStack().getType().equals(Material.CHEST)) {
				plugin.getLogger().info(e.getItem().getItemStack().getType().toString());
				if (Users.PlayerPerk2.get(p).equalsIgnoreCase("Scavenger")) {
					perk.Scavenger(p, Users.Playerequipment.get(p));
				}
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						p.getInventory().remove(new ItemStack(Material.CHEST));
						
					}
				}, 2);
				
			}
	 	}
	@EventHandler
	public void EggHit(ProjectileHitEvent e) {
		Entity entity = e.getEntity();
		if(e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			if(e.getEntityType() == EntityType.EGG) {
				plugin.api.getWeapons().EntityNearBy(entity, 3, p);
				entity.getLocation().getWorld().createExplosion(entity.getLocation(), 0);
			}
		}
	}
	@EventHandler
	public void onFakeDeath(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Users.PlayerPerk3.get(p).equalsIgnoreCase("Last Stand")) {
				if(p.getHealth() <= e.getDamage()) {
					if(plugin.api.getPerks().getlaststandstance(p) == 1) {
						return;
					}
					p.setHealth(1);
					if(plugin.api.getPerks().LastStand(p)){
					} else {
					}
				}
			}
		}
	}	
	@EventHandler
	public void NoMobSpawns(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}

}
