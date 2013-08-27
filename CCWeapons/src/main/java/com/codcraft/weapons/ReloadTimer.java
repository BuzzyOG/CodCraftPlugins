package com.codcraft.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class ReloadTimer implements Runnable {
  private Weapons plugin;
  private String name;
  private PlayerReloadEvent event;

  public ReloadTimer(Weapons plugin, String name, PlayerReloadEvent event)
  {
    this.plugin = plugin;
    this.name = name;
    this.event = event;
  }

  public void run()
  {
    Player p = Bukkit.getPlayer(this.name);
    if (p != null)
      if (p.getExp() <= 0.0F)
      {
        p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.SNOW_BALL, this.event.getAmmo()) });
        if (this.plugin.reloders.containsKey(p.getName())) {
          ((BukkitTask)this.plugin.reloders.get(p.getName())).cancel();
          this.plugin.reloders.remove(p.getName());
        }
      } else {
        p.setExp((float)(p.getExp() - 0.1D));
      }
  }
}