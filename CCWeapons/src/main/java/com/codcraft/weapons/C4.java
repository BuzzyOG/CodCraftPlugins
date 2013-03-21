package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;

public class C4 implements Listener {
	private Weapons plugin;
	public C4(Weapons plugin) {
		this.plugin = plugin;
	}
	
	private Map<Location, String> c4spots = new ConcurrentHashMap<Location, String>();
	
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
	public void onPlace(final BlockPlaceEvent e)  {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(!plugin.checkIfGameIsInstanceOfPlugin(gm.getGameWithPlayer(e.getPlayer()))) {
			return;
		}
		if(e.getBlockPlaced().getType() == Material.LEVER) {
			c4spots.put(e.getBlockPlaced().getLocation(), e.getPlayer().getName());
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					if(!e.getPlayer().getInventory().contains(Material.LEVER)) {
						e.getPlayer().setItemInHand(new ItemStack(Material.STICK));
						e.getPlayer().updateInventory();
					}
				}
			}, 2);

		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(c4spots.containsValue(e.getEntity().getName())) {
			Map<Location, String> endt = c4spots;
			for(Entry<Location, String> entry : endt.entrySet()) {
				if(entry.getValue().equalsIgnoreCase(e.getEntity().getName())) {
					c4spots.remove(e.getEntity().getName());
				}
			}
		}
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.STICK) {
				e.getPlayer().getInventory().remove(Material.STICK);
				e.getPlayer().updateInventory();
				Map<Location, String> s = c4spots;
				for(Entry<Location, String> en : s.entrySet()) {
					if(en.getValue().equalsIgnoreCase(e.getPlayer().getName())) {
						for(Entity ent : getNearbyEntities(en.getKey(), 3)) {
							if(ent instanceof Player) {
								expotiondetect(e.getPlayer(), (Player) ent);
							}
						}
						en.getKey().getWorld().createExplosion(en.getKey(), 0);
						en.getKey().getBlock().setType(Material.AIR);
						c4spots.remove(en.getKey());
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
			   p.damage(20);
			   return true;
		   } else if( p1.getId().equalsIgnoreCase(k1.getId()))  {
			   return false;
		   } else {
			   k.damage(20, p);
			   return true;
		   }
	   }
	   


}
