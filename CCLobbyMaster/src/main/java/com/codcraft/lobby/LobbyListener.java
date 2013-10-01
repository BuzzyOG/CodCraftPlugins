package com.codcraft.lobby;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.codcraft.lobby.ping.Ping;

public class LobbyListener implements Listener {

	
	private CCLobby plugin;
	
	public LobbyListener(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCreateSPawn(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBreal(BlockBreakEvent e) {
		if(!e.getPlayer().hasPermission("CodCraft.Build")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!e.getPlayer().hasPermission("CodCraft.Build")) {
			e.setCancelled(true);
		}
	}

	
	@EventHandler
	public void onChange(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onjoin(final PlayerJoinEvent e) {
		e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -465, 52, 327, (float) 180, (float) 0.6));
		e.setJoinMessage(null);
		if(e.getPlayer().hasPermission("codcraft.fly")) {
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
		} else {
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				plugin.updateSigns();
				
			}
		}, 5);
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getClickedBlock() != null) {
			if(e.getClickedBlock().getType() == Material.BEACON) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onleave(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				plugin.updateSigns();
			}
		}, 5);
	}
	
	private List<String> telporters = new ArrayList<>();
	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		if(e.getPlayer().getLocation().getBlockY() < 24) {
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -465, 52, 327, (float) 180, (float) 0.6));
		}
		if(!telporters.contains(e.getPlayer().getName())) {
			for(Module module : plugin.configmap) {
				if(isInside(e.getPlayer(), module.Block1, module.Block2)){
					plugin.getLogger().info("In: " + module.server);
					ByteArrayOutputStream b = new ByteArrayOutputStream();
				      DataOutputStream out = new DataOutputStream(b);
				      try {
				        out.writeUTF("Connect");
				        out.writeUTF(module.server);
				      } catch (IOException localIOException) {
				    	  plugin.getLogger().info("Error: " + module.server);
				      }
				      e.getPlayer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
				      plugin.pingManager.updateData(module.server);
				      Ping ping = plugin.pingManager.getPing(module.server);
				      if(!ping.isUpdating()) {
				    	  plugin.updateSigns();
				      } else {
				    	  Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								plugin.updateSigns();
							}
						}, 20);
				      }
				      
				      telporters.add(e.getPlayer().getName());
				      Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							telporters.remove(e.getPlayer().getName());
							
						}
					}, 5);
				}		
			}
		}
	}
	
	@EventHandler
	public void onEnityRegain(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			e.setFoodLevel(20);
		}
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}
	
	
	
	private boolean isInside(Player p, Location loc1, Location loc2) {
		Location loc = p.getLocation();
		double minX;
		double maxX;
		double maxZ;
		double minZ;
		double maxY;
		double minY;
		if(loc1.getX() > loc2.getX()) {
			maxX = loc1.getX();
			minX = loc2.getX();
		} else {
			maxX = loc2.getX();
			minX = loc1.getX();
		}
		if(loc1.getY() > loc2.getY()) {
			maxY = loc1.getY();
			minY = loc2.getY();
		} else {
			maxY = loc2.getY();
			minY = loc1.getY();
		}
		if(loc1.getZ() > loc2.getZ()) {
			maxZ = loc1.getZ();
			minZ = loc2.getZ();
		} else {
			maxZ = loc2.getZ();
			minZ = loc1.getZ();
		}
		
		if(loc.getX() < minX || loc.getX() > maxX)
			   return false;  
		if(loc.getZ() < minZ || loc.getZ() > maxZ)
			   return false;
		if(loc.getY() < minY || loc.getY() > maxY)
			   return false;   
		return true;		
	}
	


}
