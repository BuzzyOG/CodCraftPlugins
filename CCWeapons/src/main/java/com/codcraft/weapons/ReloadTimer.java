package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class ReloadTimer implements Runnable {
	
	private Weapons plugin;
	private String name;
	private Double time;
	private int snowballs;

	public ReloadTimer(Weapons plugin, String name, double time, int snowballs) {
		this.plugin = plugin;
		this.name = name;
		this.time = time;
		this.snowballs = snowballs;
	}

	@Override
	public void run() {
		Player p = Bukkit.getPlayer(name);
		if(p != null) {
			if(p.getExp() <= 0) {
				p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, snowballs));
				if(plugin.reloders.containsKey(p.getName())) {
					plugin.reloders.get(p.getName()).cancel();
					plugin.reloders.remove(p.getName());
				}
			} else {
				p.setExp((float) (p.getExp() - .1));
			}
		}
	}

}
