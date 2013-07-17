package com.codcraft.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;

public class Semtex implements Listener {

	private GameManager gm;
	private Weapons plugin;
	
	public Map<Entity, Location> spots = new HashMap<Entity, Location>();
	
	public Semtex(Weapons plugin) {
		this.plugin = plugin;
		gm = plugin.api.getModuleForClass(GameManager.class);
	}
	
	@EventHandler
	public void onLaunch(final ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Egg) {
			if(e.getEntity().getShooter() instanceof Player) {
				final Player p = (Player) e.getEntity().getShooter();
				final Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					if(plugin.checkIfGameIsInstanceOfPlugin(g)){
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								if(!e.getEntity().isDead()) {
									for(Entity en : e.getEntity().getNearbyEntities(3, 3, 3)) {
										if(en instanceof Player) {
											Player p2 = (Player)en;
											Team t1 = g.findTeamWithPlayer(p);
											Team t2 = g.findTeamWithPlayer(p2);
											if(p == p2) {
												p.damage(20D);
											} else if(t1 == t2) {
												
											} else if (t1 != t2) {
												p2.damage(20D, p);
											}
										}
									}
									e.getEntity().remove();
								} else {
									if(spots.containsKey(e.getEntity())) {
										Location loc = spots.get(e.getEntity()); 
									}
								}
							}
						}, 160);
					}
				}
			}
		}
	}

}
