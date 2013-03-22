package com.codcraft.perks.marathon;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.perks.Perks;

public class MarathonListener implements Listener {

	private Perks plugin;

	public MarathonListener(Perks plugin) {
		this.plugin = plugin;
		timer();
		plugin.api.getModuleForClass(CaCModule.class).addweapon("Marathon", "Perk1");
	}
	
	private Map<String, Boolean> users = new HashMap<String, Boolean>();
	
	public void timer() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
					if(g != null) {
						if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
							
							CCPlayerModule cpm = plugin.api.getModuleForClass(CCPlayerModule.class);
							CCPlayer cp = cpm.getPlayer(p);
							if(cp.getClass(cp.getCurrentclass()).getPerk1().equalsIgnoreCase("Marathon")) {
								p.setFoodLevel(20);
							} else {
								if(!users.containsKey(p.getName())) {
									users.put(p.getName(), false);
								}
								if(p.isSprinting()) {
									if(users.get(p.getName())) {
										if(p.getFoodLevel() >= 1) {
											p.setFoodLevel(p.getFoodLevel() - 1);
										}

									} else {
										users.put(p.getName(), true);
									}
								} else {
									if(users.get(p.getName())) {
										users.put(p.getName(), false);
									} else {
										if(p.getFoodLevel() <= 20) {
											p.setFoodLevel(p.getFoodLevel() + 1);
										}
									}
								}
							}
							
							
						}
					}
				}
				
			}
		}, 20, 20);
	}
	

}
