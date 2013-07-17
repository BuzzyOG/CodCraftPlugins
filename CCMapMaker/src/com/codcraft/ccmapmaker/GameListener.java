package com.codcraft.ccmapmaker;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;

public class GameListener implements Listener {

	private CCMM plugin;
	private GameManager gm;

	public GameListener(CCMM plugin) {
		this.plugin = plugin;
		this.gm = plugin.api.getModuleForClass(GameManager.class);
	}
	
	@EventHandler
	public void onChange(WeatherChangeEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		for(Game<?> g : gm.getAllGames()) {
			if(g.getName().equalsIgnoreCase(e.getWorld().getName())) {
				if(e.toWeatherState()) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		for(Game<?> g : plugin.api.getModuleForClass(GameManager.class).getAllGames()) {
			if(g.getPlugin() == plugin) {
				if(e.getLocation().getWorld().getName().equalsIgnoreCase(g.getName())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onRequest(RequestJoinGameEvent e) {
		Game<?> g = e.getGame();
		if(g != null) {
			if(g.getPlugin() == plugin) {
				MapMaker game = (MapMaker) g;
				Player p = Bukkit.getPlayer(e.getPlayer().getName());
				Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
				for(Entry<String, Location> en : game.spawns.entrySet()) {
					Material mat = Material.valueOf(en.getKey());
					ItemStack is = new ItemStack(mat, 1);
					ItemMeta im = is.getItemMeta();
					if(im != null) {
						im.setDisplayName(mat.name());
					}
					is.setItemMeta(im);
					inv.addItem(is);
				}
				p.openInventory(inv);
				g.getTeams().get(0).addPlayer(p);
				game.getSpawns.add(p.getName());
			}
		}
	}
	
	@EventHandler
	public void onInv(InventoryClickEvent e) {
		HumanEntity en = e.getWhoClicked();
		if(en instanceof Player) {
			Player p = (Player) en;
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					MapMaker game = (MapMaker) g;
					if(game.getSpawns.contains(p.getName())) {
						if(game.spawns.containsKey(e.getCurrentItem().getType().name())) {
							p.teleport(game.spawns.get(e.getCurrentItem().getType().name()));
							game.getSpawns.remove(p.getName());
							p.sendMessage("Teleporting!");
						}
					}
				}
			}
		}
		
		
	}
	
	
	@EventHandler
	public void onJoin(TeamPlayerGainedEvent e) {
		
	}
	
	
	
}
