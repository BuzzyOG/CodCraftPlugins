package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;

public class C4 implements Listener {
	private Weapons plugin;
	public C4(Weapons plugin) {
		this.plugin = plugin;
		plugin.api.getModuleForClass(CaCModule.class).addweapon("C4", "equipment");
	}
	
	private Map<Location, String> c4spots = new HashMap<Location, String>();
	
		public synchronized Map<Location, String> getC4Spots() {
			return c4spots;
		}
	
	   @EventHandler
	   public void onEntdamage(PlayerDamgedByWeaponEvent e) {
		   if(e.getCause() == DamageCause.CUSTOM) {
				   if(plugin.checkIfGameIsInstanceOfPlugin(e.getGame())) {
					   e.setCancelled(false);
					   e.setDamage(20);
			   }
		   }
	   }
	   
	   @EventHandler
	   public void getClass(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getEquipment().equalsIgnoreCase("C4")) {
				e.addItem(new ItemStack(Material.LEVER, 1));
			}
	   }
	   
	
	@EventHandler
	public void onPlace(final BlockPlaceEvent e)  {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(!plugin.checkIfGameIsInstanceOfPlugin(gm.getGameWithPlayer(e.getPlayer()))) {
			return;
		}
		if(e.getBlockPlaced().getType() == Material.LEVER) {
			getC4Spots().put(e.getBlockPlaced().getLocation(), e.getPlayer().getName());
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
						e.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
						e.getPlayer().updateInventory();
				}
			}, 2);

		}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
			if(g != null) {
				if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
					for(Entry<Location, String> en : getC4Spots().entrySet()) {
						List<Entity> enties = getNearbyEntities(en.getKey(), 1);
						for(Entity entity : enties) {
							if(entity instanceof Player) {
								((Player) entity).damage(20D);
								
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(getC4Spots().containsValue(e.getEntity().getName())) {
			Map<Location, String> endt = getC4Spots();
			for(Entry<Location, String> entry : endt.entrySet()) {
				if(entry.getValue().equalsIgnoreCase(e.getEntity().getName())) {
					entry.getKey().getBlock().setType(Material.AIR);
					getC4Spots().remove(e.getEntity().getName());
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.STICK) {
				e.getPlayer().getInventory().remove(Material.STICK);
				e.getPlayer().updateInventory();
				Map<Location, String> s = getC4Spots();
				for(Entry<Location, String> en : s.entrySet()) {
					if(en.getValue().equalsIgnoreCase(e.getPlayer().getName())) {
						for(Entity ent : getNearbyEntities(en.getKey(), 3)) {
							if(ent instanceof Player) {
								expotiondetect(e.getPlayer(), (Player) ent);
							}
						}
						en.getKey().getWorld().createExplosion(en.getKey(), 0);
						en.getKey().getBlock().setType(Material.AIR);
						getC4Spots().remove(en.getKey());
					}
				}
			}
		}
	}
	
	
	
	public List<Entity> getNearbyEntities(Location where, int range) {
	      List<Entity> found = new ArrayList<Entity>();

	      for(Entity entity : where.getWorld().getEntities()) {
	         if(isInBorder(where, entity.getLocation(), range)) {
	            found.add(entity);
	         }
	      }
	      return found;
	   }

	   public boolean isInBorder(Location center, Location notCenter, int range) {
	      int x = center.getBlockX(), z = center.getBlockZ();
	      int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

	      if(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
	         return false;
	      }
	      return true;
	   }
	   
	   
	   public boolean expotiondetect(Player p, Player k) {
		   GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		   Game<?> game = gm.getGameWithPlayer(p);
		   if(game == null) {
			   return false;
		   }
		   Team p1 = game.findTeamWithPlayer(p);
		   Team k1 = game.findTeamWithPlayer(k);
		   if (p== k){
			   p.damage(20D);
			   return true;
		   } else if( p1.getId().equalsIgnoreCase(k1.getId()))  {
			   return false;
		   } else {
			   k.damage(20D, p);
			   return true;
		   }
	   }
	   


}
