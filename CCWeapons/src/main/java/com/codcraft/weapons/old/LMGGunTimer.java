package com.codcraft.weapons.old;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;



public class LMGGunTimer implements Runnable {
	
	private Plugin plugin;
	private int xToGo;
	private long delay;
	private Player p;
	public LMGGunTimer(Plugin plugin, Player p, int xToGo, long delay) {
		this.plugin = plugin;
		this.p = p;
		this.xToGo = xToGo;
		this.delay = delay;
	}

	@Override
	public void run() {


		
			 if(p.getItemInHand().getType() == Material.BOW) {
		          if(p.getInventory().contains(Material.ARROW)) {
		             p.launchProjectile(Arrow.class);
		             p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.getMaterial(262), 1) });
		          }
		       }
		
		
	     
		
		xToGo--;
	      if(xToGo > 0) {
	          Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new LMGGunTimer(plugin, p, xToGo, delay), delay);
	       } else {
	    	   Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
				}
			}, 5);
	    	   
	       }
		
	}

}
