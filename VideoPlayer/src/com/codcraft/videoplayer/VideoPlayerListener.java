package com.codcraft.videoplayer;

import java.io.File;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;

public class VideoPlayerListener implements Listener {
	
	private VideoPlayer plugin;

	public VideoPlayerListener(VideoPlayer plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand() != null) {
				if(e.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {
					Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
					for(Entry<String, String> en : plugin.videos.entrySet()) {
						ItemStack is = new ItemStack(Material.MAP, 1);
						ItemMeta im = is.getItemMeta();
						if(im != null) {
							im.setDisplayName(en.getKey());
							is.setItemMeta(im);
						}
						inv.addItem(is);
					}
					e.getPlayer().openInventory(inv);
				}
				
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			if(e.getCurrentItem().getType() == Material.MAP) {
				ItemMeta im = e.getCurrentItem().getItemMeta();
				if(im != null) {
					if(plugin.videos.containsKey(im.getDisplayName())) {
						if(e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							ItemStack is = new ItemStack(Material.MAP);
							if(is != null && is.getType() == Material.MAP) {
								MapView map = Bukkit.getMap(is.getDurability());
								Video vid = new FileVideo(new File(plugin.videos.get(im.getDisplayName())));
								vid.start();
								VideoSender.startSending(vid, map, p);
								p.setItemInHand(is);
								p.closeInventory();
							}
							
						}
					}
				}
			}
		}
	}
}
