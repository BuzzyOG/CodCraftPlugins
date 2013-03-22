package com.codcraft.perks.scavenger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.perks.Perks;

public class ScavengerListener implements Listener {

	private Perks plugin;

	public ScavengerListener(Perks plugin) {
		this.plugin = plugin;
		plugin.api.getModuleForClass(CaCModule.class).addweapon("Scavenger", "Perk1");
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getEntity());
		if(g != null) {
			if (plugin.checkIfGameIsInstanceOfPlugin(g)) {
				e.getDrops().add(new ItemStack(Material.CHEST, 1));
			}
		}
	}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.CHEST) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(e.getPlayer());
			if(g != null) {
				if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
					final Player p = e.getPlayer();
					CCPlayerModule cpm = plugin.api.getModuleForClass(CCPlayerModule.class);
					CCPlayer ccp = cpm.getPlayer(p);
					if(ccp.getClass(ccp.getCurrentclass()).getPerk1().equalsIgnoreCase("Scavenger")) {
						e.getPlayer().getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 2));
						if(ccp.getClass(ccp.getCurrentclass()).getEquipment().equalsIgnoreCase("C4")) {
							if(!e.getPlayer().getInventory().contains(Material.LEVER)) {
								e.getPlayer().getInventory().addItem(new ItemStack(Material.LEVER, 1));
							}
						}
					}
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						p.getInventory().remove(Material.CHEST);	
					}
				}, 1);
				}
			}
		}

	}

}
