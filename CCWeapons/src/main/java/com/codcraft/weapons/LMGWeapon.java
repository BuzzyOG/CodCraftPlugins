package com.codcraft.weapons;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;

public class LMGWeapon implements Listener {
	private Weapons plugin;
	
	public static Map<String, Boolean> users = new HashMap<String, Boolean>();
	
	public LMGWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.add("LMG");
		weaptimer();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onleftcick(final PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
				
				ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
				if(meta != null) {
					if(meta.getDisplayName().equalsIgnoreCase("LMG")) {
						if(e.getPlayer().getInventory().contains(Material.BLAZE_ROD)) {
							if(!plugin.reloaders.contains(e.getPlayer().getName())) {
								e.getPlayer().getInventory().remove(Material.ARROW);
								e.getPlayer().getInventory().removeItem(new ItemStack(Material.BLAZE_ROD, 1));
								e.getPlayer().updateInventory();
								e.getPlayer().setExp((float) .9);
								plugin.reloaders.add(e.getPlayer().getName());
								Bukkit.getScheduler().runTaskLater(plugin, new ReloadTimer(plugin, e.getPlayer(), 64, .1), 5);
							}
						}
					}
				}
			}
		}
		
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
				ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
				if(meta.getDisplayName() == null) {
					return;
				}
				if(meta.getDisplayName().equalsIgnoreCase("LMG")) {
					if(!users.containsKey(e.getPlayer().getName())) {
						users.put(e.getPlayer().getName(), true);
					} else {
						if(users.get(e.getPlayer().getName()) == true) {
							users.put(e.getPlayer().getName(), false);
						}else if(users.get(e.getPlayer().getName()) == false) {
							users.put(e.getPlayer().getName(), true);
						}
					}	
				}
			}
		}
	}
	
	
	/*@EventHandler
	public void onInteracr(ProjectileLaunchEvent e) {
		
		if(e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			ItemStack bow = null;
			if(p.getItemInHand().getType() == Material.BOW) {
				bow = p.getItemInHand();
			} else {
				for(ItemStack i : p.getInventory()) {
					if(i.getType() == Material.BOW) {
						bow = i;
						break;
					}
				}
			}
			ItemMeta meta = bow.getItemMeta();
			if(meta.getDisplayName().equalsIgnoreCase("LMG")) {
				if(!users.containsKey(p.getName())) {
					users.put(p.getName(), false);
				}
				if(users.get(p.getName())) {
					
				} else {
					Bukkit.getScheduler().runTaskLater(plugin, new LMGGunTimer(plugin, p, 50, 3), 3);
					e.setCancelled(true);
				}

			}
			
		}
	}*/
	
	@EventHandler
	public void onuser(PlayerDamgedByWeaponEvent e) {
		
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
					if(meta.getDisplayName().equalsIgnoreCase("LMG")) {
						e.setDamage(8);
					}
				}
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(users.containsKey(e.getEntity().getName())) {
			if(users.get(e.getEntity().getName())) {
				users.put(e.getEntity().getName(), false);
			}
		}
	}
	
	
	
	public void weaptimer() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Entry<String, Boolean> s : users.entrySet()) {
					if(s.getValue()) {
						Player p = Bukkit.getPlayer(s.getKey());
						if(p == null) {
							users.put(s.getKey(), false);
						} else {
							if(p.getItemInHand().getType() == Material.BOW) {
								if(p.getInventory().contains(Material.ARROW)) {
									p.launchProjectile(Arrow.class);
									 p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(262), 1) });
								}
							}
							CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
							if(!ccplayer.getPlayer(p).getClass(ccplayer.getPlayer(p).getCurrentclass()).getGun().equalsIgnoreCase("LMG")) {
								users.put(p.getName(), false);
							}
						}
					}
				}
				
			}
		}, 5, 3);
	}
	
	@EventHandler
	public void giveweapon(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("LMG")) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta meta = bow.getItemMeta();
				meta.setDisplayName("LMG");
				bow.setItemMeta(meta);
				e.addItem(bow);
				e.addItem(new ItemStack(Material.ARROW, 64));
				e.addItem(new ItemStack(Material.BLAZE_ROD, 8));
			}
	}

}