package com.codcraft.lobby.event;

import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.CCEvent;
import com.codcraft.lobby.Lobby;
import java.util.Map.Entry;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerEnterLobbyEvent extends CCEvent
  implements Cancellable
{
  private boolean canceled = false;
  private TeamPlayer player;
  private Entry<Integer, Lobby> lobby;
  private static final HandlerList handlers = new HandlerList();

  public PlayerEnterLobbyEvent(TeamPlayer p, Entry<Integer, Lobby> lobby2)
  {
    this.player = p;
    this.lobby = lobby2;
  }

  public TeamPlayer getPlayer()
  {
    return this.player;
  }

  public void setPlayer(TeamPlayer player) {
    this.player = player;
  }

  public Entry<Integer, Lobby> getLobby() {
    return this.lobby;
  }

  public void setLobby(Entry<Integer, Lobby> lobby) {
    this.lobby = lobby;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public boolean isCancelled()
  {
    return this.canceled;
  }

  public void setCancelled(boolean arg0)
  {
    this.canceled = arg0;
  }
}