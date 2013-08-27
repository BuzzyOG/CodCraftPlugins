package com.codcraft.perks.SleightofHand;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.perks.Perks;
import com.codcraft.weapons.PlayerReloadEvent;

public class SleightofHand
  implements Listener
{
  private Perks plugin;

  public SleightofHand(Perks plugin)
  {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    plugin.api.getModuleForClass(CaCModule.class).addweapon("SleightofHand", "Perk2", "");
  }

  @EventHandler
  public void onReload(PlayerReloadEvent e)
  {
    CCPlayerModule ccpm = (CCPlayerModule)this.plugin.api.getModuleForClass(CCPlayerModule.class);
    CCPlayer p = ccpm.getPlayer(e.getPlayer());
    if (p.getClass(p.getCurrentclass().intValue()).getPerk2().equalsIgnoreCase("SleightofHand"))
      e.setReloadTime(Long.valueOf((long) (e.getReloadTime() * 0.75D)));
  }
}