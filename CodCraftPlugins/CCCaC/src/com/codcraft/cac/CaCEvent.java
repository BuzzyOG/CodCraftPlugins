package com.codcraft.cac;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.services.CCEvent;

public class CaCEvent extends CCEvent {
   private static final HandlerList handlers = new HandlerList();
   private Player Cacer;
   private boolean cancelled = false;

   public CaCEvent(Player p) {
      this.Cacer = p;
   }

   public boolean isCancelled() {
      return cancelled;
   }

   public Player getPlayer() {
      return Cacer;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }
   
   @Override
   public HandlerList getHandlers() {
      return handlers;
   }
}
