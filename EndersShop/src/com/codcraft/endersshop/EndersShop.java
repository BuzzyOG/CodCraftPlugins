package com.codcraft.endersshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
		List<String> CryoConnon = new ArrayList<>();
		CryoConnon.add("Points: 5000");
		CryoConnon.add("Fires Bullets at a fastest RPM");
		this.api.getModuleForClass(EnderShop.class).makeItem("Cryo Cannon", "Cryo Cannon", 5000, "battleroom.gold", Material.GOLD_BARDING , "You now have the Cryo Cannon in game!", 0, CryoConnon);
		List<String> GalatialBlaster = new ArrayList<>();
		GalatialBlaster.add("Points: 25000");
		GalatialBlaster.add("Fire 2 Bullets");
		this.api.getModuleForClass(EnderShop.class).makeItem("Glacial Blaster", "Glacial Blaster", 25000, "battleroom.diamond", Material.DIAMOND_BARDING , "You now have the Galatial Blaster in game!", 1, GalatialBlaster);
		List<String> permafrost = new ArrayList<>();
		permafrost.add("Points: 25000");
		permafrost.add("Allows to Perma-freeze one person");
		this.api.getModuleForClass(EnderShop.class).makeItem("Permafrost", "Permafrost", 25000, "battleroom.permafrost", Material.ICE , "You now have the permafrost in game!", 3, permafrost);
		 
		
		List<String> Ice = new ArrayList<>();
		Ice.add("Points: 5000");
		Ice.add("Fires Bullets at a fastest RPM");
		this.api.getModuleForClass(EnderShop.class).makeItem("Ice Breaker", "Ice Breaker", 5000, "battleroom.repeater", Material.DIODE , "You now have the Ice Breaker in game!", 9, Ice);
		List<String> Firewall = new ArrayList<>();
		Firewall.add("Points: 25000");
		Firewall.add("Fire 2 Bullets");
		this.api.getModuleForClass(EnderShop.class).makeItem("Firewall", "Firewall", 25000, "battleroom.comparator", Material.REDSTONE_COMPARATOR , "You now have the Firewall in game!", 10, Firewall);
		List<String> Dragons = new ArrayList<>();
		Dragons.add("Points: 25000");
		Dragons.add("Unfreezes all players on your team");
		this.api.getModuleForClass(EnderShop.class).makeItem("Dragons� Breath", "Dragons� Breath", 25000, "battleroom.DragonsBreath", Material.FIRE, "You now have the Dragons� Breath in game!", 12, Dragons);
		List<String> Save = new ArrayList<>();
		Save.add("Points: 2000");
		Save.add("Unfreezes yourself");
		this.api.getModuleForClass(EnderShop.class).makeItem("Save your Skin", "Save your Skin", 2000, "battleroom.SaveYourSkin", Material.BUCKET, "You now have the Save your Skin in game!", 13, Save);
		
	}
	
	@EventHandler
	public void onLobby(final PlayerToLobbyEvent e) {
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			
			@Override
			public void run() {
				e.getPlayer().getInventory().remove(Material.DIAMOND);
				ItemStack in = new ItemStack(Material.DIAMOND, 1);
				ItemMeta im = in.getItemMeta();
				im.setDisplayName("Shop");
				in.setItemMeta(im);
				e.getPlayer().getInventory().addItem(in);
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
