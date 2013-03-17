package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;


public class SniperWeapon implements Listener {
	private Weapons plugin;
	public SniperWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.add("Sniper");
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onleftcick(final PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.BOW) {
				
				ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
				if(meta != null) {
					if(meta.getDisplayName().equalsIgnoreCase("Sniper")) {
						if(e.getPlayer().getInventory().contains(Material.BLAZE_ROD)) {
							if(!plugin.reloaders.contains(e.getPlayer().getName())) {
								e.getPlayer().getInventory().remove(Material.ARROW);
								e.getPlayer().getInventory().removeItem(new ItemStack(Material.BLAZE_ROD, 1));
								e.getPlayer().updateInventory();
								e.getPlayer().setExp((float) .9);
								plugin.reloaders.add(e.getPlayer().getName());
								Bukkit.getScheduler().runTaskLater(plugin, new ReloadTimer(plugin, e.getPlayer(), 64, .2), 5);
							}
						}
					}
				}
			}
		}
		
	}
	
	
	@EventHandler
	public void onInteracr(EntityShootBowEvent e) {
		if(!(e.getProjectile() instanceof Arrow)) {
			return;
		}
		if(e.getForce() <=  .5) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
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
			if(meta.getDisplayName().equalsIgnoreCase("Sniper")) {
				Vector v = e.getProjectile().getVelocity();
				Vector v1 = v.multiply(1000);
				e.getProjectile().setVelocity(v1);

			}
			
		}
	}
	
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
					if(meta.getDisplayName().equalsIgnoreCase("Sniper")) {
						e.setDamage(20);
					}
				}
		}
	}
	@EventHandler
	public void giveweapon(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("Sniper")) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta meta = bow.getItemMeta();
				meta.setDisplayName("Sniper");
				bow.setItemMeta(meta);
				e.addItem(bow);
				e.addItem(new ItemStack(Material.ARROW, 64));
				
			}
	}


}
