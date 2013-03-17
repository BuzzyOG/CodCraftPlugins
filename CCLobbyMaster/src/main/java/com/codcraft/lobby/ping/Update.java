package com.codcraft.lobby.ping;

import com.codcraft.lobby.CCLobby;

public class Update implements Runnable {
  private CCLobby plugin;

  public Update(CCLobby plugin)
  {
    this.plugin = plugin;
  }

  public void run()
  {
    plugin.updateSigns();
  }

  
}