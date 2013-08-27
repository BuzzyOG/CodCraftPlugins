package com.codcraft.weapons;

import com.CodCraft.api.services.CCEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerStartFiringEvent extends CCEvent  {
	
    private Player player;
    private int rpm;
    private static final HandlerList handlers = new HandlerList();

    public PlayerStartFiringEvent(Player player, int RPM) {
    	this.player = player;
    	this.rpm = RPM;
    }

    public int getRpm() {
    	return this.rpm;
    }
    
    public void setRpm(int rpm) {
    	this.rpm = rpm;
    }
    
    public Player getPlayer() {
    	return this.player;
    }

    public void setPlayer(Player player) {
    	this.player = player;
    }

    public static HandlerList getHandlerList() {
    	return handlers;
    }

    public HandlerList getHandlers() {
    	return handlers;
    }
}