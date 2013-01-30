package com.CodCraft.hardpoint.GM;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.GuiEvent;
import com.CodCraft.hardpoint.CodCraft;

public class GameListener implements Listener {

   private CodCraft plugin;

   public GameListener(CodCraft plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onGUIUpdate(GuiEvent e) {
      ArrayList<String> temp = new ArrayList<String>();
      if(plugin.getHardpoint().contested) {
         temp.add("Point Contested!");
      } else if(plugin.getHardpoint().team1) {
         temp.add("Team 1 has point");
      } else if(plugin.getHardpoint().team2) {
         temp.add("Team 2 has point");
      } else {
         temp.add("Point open!");
      }
      e.setBeforeMessages(temp);
   }

}
