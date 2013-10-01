package com.codcraft.endersshop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.endersshop.module.EnderShop;
import com.codcraft.endersshop.shop.Item;
import com.codcraft.lobby.event.PlayerToLobbyEvent;

public class EndersShop extends JavaPlugin implements Listener {
	
	public CCAPI api;

	public void onEnable() {
		Plugin api = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(api != null) {
			this.api = (CCAPI) api;
		} else {
			getLogger().info("API not found! =(");
			return;
		}
		this.api.registerModule(EnderShop.class, new EnderShop(this.api, this));
		getServer().getPluginManager().registerEvents(this, this);
		this.api.getModuleForClass(EnderShop.class).makeItem("Cryo Cannon", "Cryo Cannon", 5000, "battleroom.gold", Material.GOLD_BARDING , "You now have the Cryo Cannon in game!", 0);
		this.api.getModuleForClass(EnderShop.class).makeItem("Galatial Blaster", "Galatial Blaster", 25000, "battleroom.diamond", Material.DIAMOND_BARDING , "You now have the Galatial Blaster in game!", 1);
		this.api.getModuleForClass(EnderShop.class).makeItem("permafrost", "permafrost", 25000, "battleroom.permafrost", Material.ICE , "You now have the permafrost in game!", 3);
		 
		this.api.getModuleForClass(EnderShop.class).makeItem("Ice Breaker", "Ice Breaker", 5000, "battleroom.repeater", Material.DIODE , "You now have the Ice Breaker in game!", 9);
		this.api.getModuleForClass(EnderShop.class).makeItem("Firewall", "Firewall", 25000, "battleroom.diamond", Material.REDSTONE_COMPARATOR , "You now have the Firewall in game!", 10);
		this.api.getModuleForClass(EnderShop.class).makeItem("Dragons’ Breath", "Dragons’ Breath", 25000, "battleroom.DragonsBreath", Material.FIRE, "You now have the Dragons’ Breath in game!", 12);
		this.api.getModuleForClass(EnderShop.class).makeItem("Save your Skin", "Save your Skin", 200, "battleroom.DragonsBreath", Material.BUCKET, "You now have the Save your Skin in game!", 13);
		
	}
	
	@EventHandler
	public void onLobby(final PlayerToLobbyEvent e) {
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			
			@Override
			public void run() {
				e.getPlayer().getInventory().remove(Material.DIAMOND);
				e.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
			}
		}, 1);

	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null){
			if(e.getPlayer().getItemInHand().getType() == Material.DIAMOND) {
				e.getPlayer().openInventory(api.getModuleForClass(EnderShop.class).requestInventory(e.getPlayer()));
			}
		}
	}
	
	
	@EventHandler
	public void onInv(InventoryClickEvent e) {
		if(e.getInventory().getName().equalsIgnoreCase("BattleShop")){
			if(e.getWhoClicked() instanceof Player) {
				Player p =(Player) e.getWhoClicked();
				EnderShop ES = api.getModuleForClass(EnderShop.class);

				Item item = ES.getItemFromMaterial(e.getCurrentItem().getType(), p);
				if(item != null) {
					item.onBuy(p);
				}
				e.setCancelled(true);
			}

		}
	}

}
