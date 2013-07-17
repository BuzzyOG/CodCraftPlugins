package com.codcraft.weapons.old;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.weapons.Weapons;

public class SMGWeapon implements Listener {
	private Weapons plugin;
	
	public static Map<String, Boolean> users = new HashMap<String, Boolean>();
	
	public SMGWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.add("SMG");
	}
	
	
	@EventHandler
	public void onInteracr(ProjectileLaunchEvent e) {
		if(!(e.getEntity() instanceof Arrow)) {
			return;
		}
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
			if(meta.getDisplayName() == null) {
				return;
			}
			if(meta.getDisplayName().equalsIgnoreCase("SMG")) {
				if(!users.containsKey(p.getName())) {
					users.put(p.getName(), false);
				}
				if(users.get(p.getName())) {
					
				} else {
					Bukkit.getScheduler().runTaskLater(plugin, new SMGGunTimer(plugin, p, 16, 1), 1);
					e.setCancelled(true);
				}

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
					if(meta.getDisplayName().equalsIgnoreCase("SMG")) {
						e.setDamage(4);
					}
				}
			}
	}
	@EventHandler
	public void giveweapon(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("SMG")) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta meta = bow.getItemMeta();
				meta.setDisplayName("SMG");
				bow.setItemMeta(meta);
				e.addItem(bow);
				e.addItem(new ItemStack(Material.ARROW, 64));
				e.addItem(new ItemStack(Material.ARROW, 64));
				e.addItem(new ItemStack(Material.ARROW, 64));
			}
	}

}