package com.codcraft.lobby.ping;

public class Result
{
  private int playersOnline;
  private int maxPlayers;
  private String motd;
  private boolean online;

  public Result(int playersOnline, int maxPlayers, String motd, boolean b)
  {
    this.playersOnline = playersOnline;
    this.maxPlayers = maxPlayers;
    this.motd = motd;
    this.online = b;
  }

  public Result(boolean b) {
    this.online = b;
  }

  public int getPlayersOnline()
  {
    return this.playersOnline;
  }

  public int getMaxPlayers()
  {
    return this.maxPlayers;
  }

  public String getMotd()
  {
    return this.motd;
  }

  public boolean isOnline()
  {
    return this.online;
  }
}