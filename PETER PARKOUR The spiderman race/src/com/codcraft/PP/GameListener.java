package com.codcraft.PP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;

import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;

public class GameListener implements Listener {
	
	private Parcore plugin;
	private List<String> joining = new ArrayList<>();
	private List<String> jumping = new ArrayList<>();

	public GameListener(Parcore plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onLEaf(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onRe(final RequestJoinGameEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			if(!joining.contains(e.getPlayer().getName())) {
				e.getGame().getTeams().get(0).addPlayer(e.getPlayer());
				joining.add(e.getPlayer().getName());
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						joining.remove(e.getPlayer().getName());
						
					}
				}, 20);
			}

		}
	}
	
	@EventHandler
	public void onHurt(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onIn(final PlayerInteractEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(g.getPlugin() == plugin) {
				if(e.getAction() == Action.PHYSICAL) {
					if(!joining.contains(e.getPlayer().getName())) {
						if(!jumping.contains(e.getPlayer().getName())) {
							Vector vec = e.getPlayer().getVelocity();
							e.getPlayer().setVelocity(new Vector(vec.getX(), vec.getY() + 1.7, vec.getZ()));
							jumping.add(e.getPlayer().getName());
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
								
								@Override
								public void run() {
									jumping.remove(e.getPlayer().getName());
									
								}
							}, 5);
						}
						
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() != SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}

	}
	
	@EventHandler
	public void onCreeper(EntityExplodeEvent e) {

		e.setCancelled(true);
	}
	
	@EventHandler
	public void w(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);
		}
	}


}
