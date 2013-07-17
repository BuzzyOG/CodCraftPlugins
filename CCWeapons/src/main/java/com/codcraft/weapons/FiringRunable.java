package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

public class FiringRunable implements Runnable {

	private String name;
	

	public FiringRunable(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Player p = Bukkit.getPlayer(name);
		if(p != null) {
			if(p.getInventory().contains(Material.SNOW_BALL)) {
				p.launchProjectile(Snowball.class);
				p.getInventory().removeItem(new ItemStack(Material.SNOW_BALL, 1));
				p.updateInventory();
			}
		} 

	}

}
