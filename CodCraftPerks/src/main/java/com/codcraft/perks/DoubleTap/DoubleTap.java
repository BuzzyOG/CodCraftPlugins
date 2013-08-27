package com.codcraft.perks.DoubleTap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.perks.Perks;
import com.codcraft.weapons.PlayerStartFiringEvent;

public class DoubleTap
  implements Listener
{
  private Perks plugin;

  public DoubleTap(Perks plugin)
  {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    plugin.api.getModuleForClass(CaCModule.class).addweapon("DoubleTap", "Perk2", "");
  }

  @EventHandler
  public void DT(PlayerStartFiringEvent e)
  {
    CCPlayerModule ccpm = (CCPlayerModule)this.plugin.api.getModuleForClass(CCPlayerModule.class);
    CCPlayer p = ccpm.getPlayer(e.getPlayer());
    if (p.getClass(p.getCurrentclass().intValue()).getPerk2().equalsIgnoreCase("DoubleTap"))
      e.setRpm((int)(e.getRpm() * 0.66D));
  }
}