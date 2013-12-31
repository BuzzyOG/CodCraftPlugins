package com.codcraft.ccinventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.InventoryManager;
import com.codcraft.ccommands.CCCommands;
import com.codcraft.ccommands.PlayerDoSpawnEvent;

public class Teleports implements Listener {

	public CCCommands plugin;
	private InventoryManager im;// LAZY

	public Teleports(CCCommands plugin) {
		this.plugin = plugin;
		im = plugin.api.getModuleForClass(InventoryManager.class);
	}

	@EventHandler
	public void onLobby(final PlayerDoSpawnEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				e.getPlayer().getInventory().remove(Material.ENDER_PEARL);
				ItemStack in = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta im = in.getItemMeta();
				im.setDisplayName("Teleports");
				in.setItemMeta(im);
				e.getPlayer().getInventory().addItem(in);
			}
		}, 1);

	}

	@EventHandler
	public void onHotBarClick(PlayerInteractEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if (gm.getGameWithPlayer(e.getPlayer()) == null) {
			if (e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
				im.openInv(e.getPlayer(), im.getInv("TelportList"));
			}
		}
	}

}
