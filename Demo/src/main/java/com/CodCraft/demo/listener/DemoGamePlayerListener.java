package com.CodCraft.demo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.CodCraft.api.services.CCGameListener;
import com.CodCraft.demo.game.DemoGame;

/**
 * Game listener for player actions.
 */
public class DemoGamePlayerListener extends CCGameListener {

   DemoGame game;

   public DemoGamePlayerListener(DemoGame game) {
      this.game = game;
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
      // Skip if game doesn't contain player.
      if(!(game.containsPlayer(event.getEntity()))) {
         return;
      }
      // TODO Handle respawn.
   }

   @EventHandler(priority = EventPriority.MONITOR)
   public void onPlayerQuit(PlayerQuitEvent event) {
      // Remove player from queue if necessary.
      game.getQueuedPlayers().remove(event);
   }
}
