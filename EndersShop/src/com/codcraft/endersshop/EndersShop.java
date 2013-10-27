package com.codcraft.endersshop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
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
import com.codcraft.bunggie.*;

public class EndersShop extends JavaPlugin implements Listener {
	
	public CCAPI api;
	public Main bungee;
	
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
		this.api.getModuleForClass(EnderShop.class).makeItem("Galatial Blaster", "Galatial Blaster", 25000, "battleroom.diamond", Material.DIAMOND_BARDING , "You now have the Galatial Blaster in game!", 1, GalatialBlaster);
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
				e.getPlayer().getInventory().remove(Material.ENDER_PEARL);
				ItemStack in = new ItemStack(Material.DIAMOND, 1);
				ItemStack in2 = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta im = in.getItemMeta();
				ItemMeta im2 = in.getItemMeta();
				im.setDisplayName("Shop");
				im2.setDisplayName("Teleports");
				in.setItemMeta(im);
				in2.setItemMeta(im2);
				e.getPlayer().getInventory().addItem(in);
				e.getPlayer().getInventory().addItem(in2);
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
			if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
				e.getPlayer().openInventory(api.getModuleForClass(EnderShop.class).teleportInventory(e.getPlayer()));
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
		
		if(e.getInventory().getName().equalsIgnoreCase("Quick Teleport")){
			if(e.getWhoClicked() instanceof Player) {
				Player p =(Player) e.getWhoClicked();
				Material clickedItem = e.getCurrentItem().getType();
				
				if(clickedItem != null) {
					checkTeleport(clickedItem, p);
				}
				e.setCancelled(true);
			}

		}
	}

	private void checkTeleport(Material clickedItem, Player p) {
		
		switch (clickedItem){
			case WOOL:
				exeTP(p, "hub" //server here);
				break;
			case BOW:
				exeTP(p, "codcraft" //server here);
				break;
			case FEATHER:
				exeTP(p, "battleroom" //server here);
				break;
			case ICE:
				exeTP(p, "freezetag" //server here);
				break;
			default:
				p.sendMessage("Invalid warp!");
				break;
		}		
	}

	private void exeTP(Player p, String location, String server) {
		
		  File warpFile = new File("./plugins/CCCommands/Warps/" + location + ".yml");
		  
		  if (!warpFile.exists()) {
			  p.sendMessage("Invalid warp did you set one at the coords on any server?");
			  return;
		  }
	      
		  YamlConfiguration warpLoad = YamlConfiguration.loadConfiguration(warpFile);
	
		  String x = warpLoad.getString("x");
		  String y = warpLoad.getString("y");
		  String z = warpLoad.getString("z");
		  
		  bungee.utils.teleportAPlayerToServer(p.getName(), server, warpLoad.getString("world"),  x, y, z);
	}
}
