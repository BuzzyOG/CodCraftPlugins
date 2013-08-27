package com.codcraft.weapons.old;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.weapons.Weapons;


public class RoitSheildWeapon implements Listener {
	private Weapons plugin;
	public RoitSheildWeapon(Weapons plugin) {
		this.plugin = plugin;
		plugin.cac.weapons.put("Roit", "");
	}
	
	

	@EventHandler (priority = EventPriority.LOWEST)
	public void onuser(PlayerDamgedByWeaponEvent e) {
			if(e.getDamagee().getItemInHand().getType() == Material.IRON_DOOR) {
				Player p = e.getDamagee();
				ItemStack bow = null;
				if(p.getItemInHand().getType() == Material.IRON_DOOR) {
					bow = p.getItemInHand();
				} 
				if(bow == null) {
					return;
				}
				ItemMeta meta = bow.getItemMeta();
				if(meta.getDisplayName().equalsIgnoreCase("Roit")) {
					if(p.canSee(e.getDamager())) {
						e.setCancelled(true);
					}
				}
			}
		
	}
	@EventHandler
	public void giveweapon(PlayerGetClassEvent e) {
			CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
			
			if(ccplayer.getPlayer(e.getPlayer()).getClass(ccplayer.getPlayer(e.getPlayer()).getCurrentclass()).getGun().equalsIgnoreCase("Roit")) {
				ItemStack bow = new ItemStack(Material.IRON_DOOR);
				ItemMeta meta = bow.getItemMeta();
				meta.setDisplayName("Roit");
				bow.setItemMeta(meta);
				e.addItem(bow);
			}
	}


}
