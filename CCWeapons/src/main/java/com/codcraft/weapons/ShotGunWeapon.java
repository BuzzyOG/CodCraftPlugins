package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;


public class ShotGunWeapon implements Listener {
	private Weapons plugin;
	public ShotGunWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.add("ShotGun");
	}
	
	
	@EventHandler
	public void onInteracr(EntityShootBowEvent e) {
		if(!(e.getProjectile() instanceof Arrow)) {
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
			if(meta.getDisplayName().equalsIgnoreCase("ShotGun")) {
				Bukkit.broadcastMessage(""+e.getForce());
				if(e.getForce() >= .9) {
					Vector v = e.getProjectile().getVelocity();
					Bukkit.broadcastMessage("1");
					Vector a1 = v;
					Arrow a = p.launchProjectile(Arrow.class);
					Vector v1 = new Vector(a.getLocation().getX() + 50, a.getLocation().getY(), a.getLocation().getZ() + 0);
					Vector v2 = a.getVelocity();
					v2.angle(v1);
					a.setVelocity(v2);
					
					Vector a2 = v;
					Vector a3 = v;
					Vector a4 = v;
					Vector a5 = v;
					Vector a6 = v;
					e.setCancelled(true);
				}

			}
			
		}
	}
	
	@EventHandler
	public void onuser(PlayerDamgedByWeaponEvent e) {
		if(e.getCause() == DamageCause.ARROW) {
			if(e.getDamager().getItemInHand().getType() == Material.BOW) {
				Player p = e.getDamager();
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
				if(meta.getDisplayName().equalsIgnoreCase("ShotGun")) {
					e.setDamage(20);
				}
			}
		}
	}
	@EventHandler
	public void giveweapon(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("ShotGun")) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta meta = bow.getItemMeta();
				meta.setDisplayName("ShotGun");
				bow.setItemMeta(meta);
				e.addItem(bow);
				e.addItem(new ItemStack(Material.ARROW, 64));
				e.addItem(new ItemStack(Material.ARROW, 64));
			}
	}
	



}
