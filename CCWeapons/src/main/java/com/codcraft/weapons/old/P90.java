package com.codcraft.weapons.old;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.weapons.Weapons;

public class P90 implements Listener {
	
	private Weapons plugin;

	public P90(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.put("P90", "");
	}
	
	@EventHandler
	public void onI(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.GLOWSTONE_DUST) {
				ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
				if(meta != null) {
					if(meta.getDisplayName() != null) {
						if("P90".equalsIgnoreCase(meta.getDisplayName())) {
							if(e.getPlayer().getInventory().contains(Material.SNOW_BALL)) {
								e.getPlayer().getInventory().remove(new ItemStack(Material.SNOW_BALL, 1));
								e.getPlayer().launchProjectile(Snowball.class);
							}
						}
					}
				}
			}
		}
	} 
	@EventHandler
	public void onuser(PlayerDamgedByWeaponEvent e) {
		if(e.getCause() == DamageCause.EQUIPMENT) {
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
				if(meta != null) {
					if("P90".equalsIgnoreCase(meta.getDisplayName())) {
						e.setDamage(4);
					}
				}
			}
		}
	}
	@EventHandler
	public void onRequest(PlayerGetClassEvent e) {
		if(plugin.checkIfGameIsInstanceOfPlugin(e.getGame())) {
			e.addItem(new ItemStack(348));
			e.addItem(new ItemStack(Material.SNOW_BALL, 64));
			e.addItem(new ItemStack(Material.SNOW_BALL, 64));
			e.addItem(new ItemStack(Material.SNOW_BALL, 64));
		}
	}

}
