package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

public class FiringRunable implements Runnable {
  private String name;
  private long i;
  private Weapons plugin;

  public FiringRunable(String name, long i, Weapons plugin) {
    this.i = i;
    this.name = name;
    this.plugin = plugin;
  }

  public void run() {
    Player p = Bukkit.getPlayer(this.name);
    if ((p != null) && (p.getInventory().contains(Material.SNOW_BALL))) {
      final Projectile proj = p.launchProjectile(Snowball.class);
      proj.setVelocity(proj.getVelocity().multiply(3));
      Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
    	  
    	  
        public void run() {
          proj.remove();
        }
      }, this.i);
      
      p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.SNOW_BALL, 1) });
      p.updateInventory();
    }
  }
}