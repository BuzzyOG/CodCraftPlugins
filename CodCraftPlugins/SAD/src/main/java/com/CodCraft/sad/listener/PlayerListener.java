package com.CodCraft.sad.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.api.modules.Cac;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.KillStreaks;
import com.CodCraft.api.modules.MYSQL;
import com.CodCraft.api.modules.Perks;
import com.CodCraft.api.modules.TeamPlayer;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.sad.CodCraft;
import com.CodCraft.sad.Users;
import com.CodCraft.sad.GM.SAD;


public class PlayerListener implements Listener {
	   private CodCraft plugin;
	   private GameManager gm;
	   private Weapons weap; 
	   private TeamPlayer player; 
	   private Perks perk;
	   private Teleport t; 
	   private Teams team;
	   private KillStreaks killStreaks; 

	   public PlayerListener(CodCraft plugin) {
	      this.plugin = plugin;
	      gm = plugin.getApi().getModuleForClass(GameManager.class);
	      weap = plugin.getApi().getModuleForClass(Weapons.class);
	      player = plugin.getApi().getModuleForClass(TeamPlayer.class);
	      perk = plugin.getApi().getModuleForClass(Perks.class);
	      t = plugin.getApi().getModuleForClass(Teleport.class);
	      team = plugin.getApi().getModuleForClass(Teams.class);
	      killStreaks = plugin.getApi().getModuleForClass(KillStreaks.class);
	      
	   }

    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	p.sendMessage("====================CodCraft===================");
    	p.sendMessage("==Welcome to CodCraft. Plugin made by Joby890==");
    	p.sendMessage("=====With help by fireblast709 and JamyDev=====");
    	p.sendMessage("====================Enjoy!=====================");
    	/*if(plugin.getApi().getGamemode().getGameMode() == Gamemodes.LOBBY) {
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "Please vote for the next map:");
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    plugin.getGame().world1);
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    "or");
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    plugin.getGame().world2);
    	    p.sendMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "using /vote <mapName>");
    	}*/
    	team.Eventeams(p);
    	player.AddPlayer(p);
    	player.Addplaying(p);
		@SuppressWarnings("unchecked")
		HashMap<String, String> hm = (HashMap<String, String>) MYSQL.SinglePlayerALLData(p).clone();
		plugin.getGame().PlayerKills.put(p.getName(), Integer.parseInt(hm.get("PlayerKills")));
		plugin.getGame().PlayerDeaths.put(p.getName(), Integer.parseInt(hm.get("PlayerDeaths")));
		plugin.getGame().PlayerWins.put(p.getName(), Integer.parseInt(hm.get("PlayerWins")));
		plugin.getGame().PlayerLoses.put(p.getName(), Integer.parseInt(hm.get("Playerlosses")));
		Users.PlayerGun.put(p, hm.get("PlayerGun"));
		Users.PlayerAttactment.put(p, hm.get("PlayerAttactment"));
		Users.PlayerPerk1.put(p, hm.get("PlayerPerk1"));
		Users.PlayerPerk2.put(p, hm.get("PlayerPerk2"));
		Users.PlayerPerk3.put(p, hm.get("PlayerPerk3"));
		Users.PlayerKIllStreaks.put(p, hm.get("PlayerKIllStreaks"));
		Users.Playerequipment.put(p, hm.get("Playerequipment"));
    	
    	if(Users.PlayerPerk1.get(p).equalsIgnoreCase("Marathon")) {
    		perk.addMarathon(p);
    	} else {
    		perk.removeMarathon(p);
    	}
		if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
    		perk.AddLightuser(p);
    	} else {
    		perk.RemoveLightuser(p);
    	}
		player.ClearInventory(p);
		weap.GiveKnife(p);
    	weap.GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
    
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
    	e.getDrops().clear();
    	e.getDrops().add(new ItemStack(Material.getMaterial(54), 1));
    	if(SAD.bombholder.get(gm.GetCurrentWorld()).contains(e.getEntity().getName())) {
    		e.getEntity().getLocation().getBlock().setType(Material.STONE_BUTTON);
    		SAD.bombholder.remove(gm.GetCurrentWorld());
    		SAD.bombholder.put(gm.GetCurrentWorld(), "droped");
    		SAD.bombdroped = e.getEntity().getLocation();
    	}
    	if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
        	Player p = (Player) e.getEntity();
        	Player k = (Player) e.getEntity().getKiller();
        	player.AddKill(k);
        	player.AddDeath(p);
        	player.AddSpectators(p);
        	plugin.getApi().getModuleForClass(GUI.class).updatelist();
        	weap.SetSniperStage(p, 1);
        	weap.RemoveC4(p);
        	killStreaks.setKills(k, killStreaks.getKills(k) + 1);

        	for (String s : player.Whoplaying()) {
        		Player all = Bukkit.getPlayer(s);
        		killStreaks.checkPlayerKillstreak(all);
        	}
        	perk.SetUnUsedLastStand(p);
        	
    	}
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
    	final Player p = e.getPlayer();
    	if(player.isSpectators(p)) {
    		e.setRespawnLocation(Bukkit.getPlayer(team.getTeams(team.getTeam(p)).get(0)).getLocation());
    	}
    	e.setRespawnLocation(t.Respawn(p, gm.GetCurrentWorld()));
    	if(player.isSpectators(p)) {
        	plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
    			
    			@Override
    			public void run() {
    				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000, 1));
    				
    			}
    		}, 10);
    	}

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
    		weap.ShootSniper(p, v);
    	}
    	e.setCancelled(true);
    }
    @EventHandler
    public void GunInteractEvent(PlayerInteractEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
    		if(e.getPlayer().getItemInHand().getType() == Material.GREEN_RECORD) {
    			killStreaks.ShootDeathMachine(p);
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.MAGMA_CREAM) {
    			killStreaks.lanuchnuke(p);
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.STICK) {
    			weap.CreateC4Ex(p);
    			
    		}
    		if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
    			weap.ZoomUp1(p,Users.PlayerAttactment.get(p));
    		}
    	}
    	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
    			
    	    	if(Users.PlayerGun.get(p).equalsIgnoreCase("MachineGun")) {
    	    		weap.ShootMachineGun(p);
    	    	}
    	    	if(Users.PlayerGun.get(p).equalsIgnoreCase("SubMachine")) {
    	    		weap.ShootSubMachine(p);
    	    	}
    		}
    	}

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	player.RemovePlayer(p);
    	player.RemovePlaying(p);
    	player.RemoveSpectators(p);
    	team.RemovePlayer(p);
    	perk.removeMarathon(p);
    	perk.RemoveLightuser(p);
    	p.getInventory().clear();
    }
    @EventHandler
    public void TagApi(PlayerReceiveNameTagEvent e) {
    	Player p = e.getNamedPlayer();
			if (team.getTeam(p) == 1) {
				e.setTag(ChatColor.RED + e.getNamedPlayer().getName());
			}
			if (team.getTeam(p) == 2) {
				e.setTag(ChatColor.BLUE + e.getNamedPlayer().getName());
			}	
		
    }
    @EventHandler
    public void onResawn(PlayerRespawnEvent e) {
    	Player p = e.getPlayer();
    	if(player.isSpectators(p)) {
    		return;
    	}
    	weap.GiveKnife(p);
    	weap.GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
    }
	@EventHandler
	public void OnSignClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();


		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) e.getClickedBlock().getState();
				Integer j = plugin.getApi().getModuleForClass(Cac.class).GetBox(p);
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
						int index = weap.getweapons("Guns").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Guns").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Guns").get(index);
						//String nextGun = CodCraftCaCOptions.Guns.get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerGun.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk1")) {
						int index = weap.getweapons("Perk1").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Perk1").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Perk1").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk1.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk2")) {
						int index = weap.getweapons("Perk2").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Perk2").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Perk2").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk2.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Perk3")) {
						int index = weap.getweapons("Perk3").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Perk3").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Perk3").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerPerk3.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Equipment")) {
						int index = weap.getweapons("Equipment").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Equipment").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Equipment").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.Playerequipment.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("KillStreaks")) {
						int index = weap.getweapons("KillStreaks").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("KillStreaks").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("KillStreaks").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerKIllStreaks.put(p, s.getLine(3));
					}
					if (s.getLine(1).equals("Attachment")) {
						System.out.println( weap.getweapons("Attactment"));
						int index = weap.getweapons("Attactment").indexOf(s.getLine(3)) + 1;
						if(index >= weap.getweapons("Attactment").size()){
						    index = 0;
						}
						String nextGun = weap.getweapons("Attactment").get(index);
						s.setLine(3, nextGun);
						s.update();
						Users.PlayerAttactment.put(p, s.getLine(3));
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
				weap.EntityNearBy(entity, 3, p);
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
					if(perk.getlaststandstance(p) == 1) {
						return;
					}
					p.setHealth(1);
					if(perk.LastStand(p)){
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
	private Map<String, Integer> psots = new HashMap<>();
	@EventHandler
	public void isSpectating(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!player.isSpectators(p)) {
			return;
		}
		if(psots.get(p.getName()) == null) {
			psots.put(p.getName(), 0);
		}
		int index = team.getTeams(team.getTeam(p)).indexOf(psots.get(p.getName())) + 1;
		if(index >= team.getTeams(team.getTeam(p)).size()){
		    index = 0;
		}
		p.teleport(Bukkit.getPlayer(team.getTeams(team.getTeam(p)).get(index)).getLocation());
	}

}
