package com.codcraft.perks.Bandolier;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.perks.Perks;
import com.codcraft.weapons.PlayerReloadEvent;
import com.codcraft.weapons.Weapon;
import com.codcraft.weapons.WeaponModule;

public class Bandolier implements Listener {
	
  private Perks plugin;

  public Bandolier(Perks plugin) {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    plugin.api.getModuleForClass(CaCModule.class).addweapon("Bandolier", "Perk1", "");
  }

  @EventHandler(priority=EventPriority.MONITOR)
  public void ongetclas(PlayerGetClassEvent e) {
    WeaponModule weap = (WeaponModule)this.plugin.api.getModuleForClass(WeaponModule.class);
    Weapon weapin = weap.getWeaponForPlayer(e.getPlayer());
    CCPlayerModule ccpm = (CCPlayerModule)this.plugin.api.getModuleForClass(CCPlayerModule.class);
    CCPlayer p = ccpm.getPlayer(e.getPlayer());
    if (p.getClass(p.getCurrentclass().intValue()).getPerk1().equalsIgnoreCase("Bandolier")) {
    	e.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(Material.SNOW_BALL, weapin.getAmmo()) });
    }
  }

  @EventHandler
  public void reload(PlayerReloadEvent e)
  {
    CCPlayerModule ccpm = (CCPlayerModule)this.plugin.api.getModuleForClass(CCPlayerModule.class);
    CCPlayer p = ccpm.getPlayer(e.getPlayer());
    if (p.getClass(p.getCurrentclass().intValue()).getPerk1().equalsIgnoreCase("Bandolier")) {
      e.setAmmo(e.getAmmo() * 2);
    }
  }
}