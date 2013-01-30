package com.CodCraft.ctf.GM;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.CodCraft.api.event.GuiEvent;
import com.CodCraft.api.event.WinEvent;
import com.CodCraft.ctf.CodCraft;

public class GameListener implements Listener {

   private CodCraft plugin;

   public GameListener(CodCraft codCraft) {
      this.plugin = codCraft;
   }

   @EventHandler
   public void WoolPickup(PlayerPickupItemEvent e) {
      if(e.getItem().getItemStack().getType() == Material.WOOL) {
         e.setCancelled(true);
      }
   }

   @EventHandler
   public void onWin(WinEvent e) {
      plugin.getGame().Savedata();
   }

   @EventHandler
   public void onGUIEvent(GuiEvent e) {
      if(CTF.Flagcaps.get(2) == null) {
         CTF.Flagcaps.put(2, 0);
      }
      if(CTF.Flagcaps.get(1) == null) {
         CTF.Flagcaps.put(1, 0);
      }
      e.setBlueScoreMessage("Score: " + CTF.Flagcaps.get(2) + "/3");
      e.setRedScoreMessage("Score: " + CTF.Flagcaps.get(1) + "/3");
   }

}
