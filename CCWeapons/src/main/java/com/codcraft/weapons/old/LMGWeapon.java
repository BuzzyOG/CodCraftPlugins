package com.codcraft.weapons.old;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
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
import com.codcraft.weapons.Weapons;

public class LMGWeapon implements Listener {
	private Weapons plugin;
	
	
	public LMGWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.add("LMG");
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
								e.getPlayer().getInventory().remove(Material.SNOW_BALL);
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
					CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
					if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("LMG")) {
						Player p = e.getPlayer();
						if(p.getItemInHand().getType() == Material.BOW) {
							if(p.getInventory().contains(Material.SNOW_BALL)) {
								p.launchProjectile(Snowball.class);
								 p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.SNOW_BALL, 1) });
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
					if(meta.getDisplayName().equalsIgnoreCase("LMG")) {
						e.setDamage(8);
					}
				}
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

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
				e.addItem(new ItemStack(Material.SNOW_BALL, 64));
				e.addItem(new ItemStack(Material.BLAZE_ROD, 8));
			}
	}

}