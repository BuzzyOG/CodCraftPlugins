package com.codcraft.weapons;

import com.CodCraft.api.services.CCEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerReloadEvent extends CCEvent {
	
  private Player player;
  private int ammo;
  private long reloadtime;
  private static final HandlerList handlers = new HandlerList();

  public PlayerReloadEvent(Player player, int ammo, long reloadtime) {
    this.player = player;
    this.ammo = ammo;
    this.reloadtime = reloadtime;
  }

  public int getAmmo()
  {
    return this.ammo;
  }

  public void setAmmo(int ammo)
  {
    this.ammo = ammo;
  }

  public Player getPlayer()
  {
    return this.player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public long getReloadTime() {
    return this.reloadtime;
  }

  public void setReloadTime(Long time) {
    this.reloadtime = time.longValue();
  }

  public static HandlerList getHandlerList()
  {
    return handlers;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }
}