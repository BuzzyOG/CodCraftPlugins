package com.codcraft.lobby.ping;

import java.util.Map;
import java.util.Map.Entry;

public class PingThread
{
  private Map<String, String> pinglist;
  private MCPing ping;

  public PingThread(Map<String, String> pinglist)
  {
    this.pinglist = pinglist;
    this.ping = new MCPing();
  }

  void ping() {
    for (Entry<String, String> e : this.pinglist.entrySet()) {
      this.ping.setAddress(((String)e.getValue()).split(":")[0]);
      this.ping.setPort(Integer.parseInt(((String)e.getValue()).split(":")[1]));
      if (this.ping.fetchData()) {
        Ping.getInstance().results.put(e.getKey(), new Result(this.ping.getPlayersOnline(), this.ping.getMaxPlayers(), this.ping.getMotd(), true));
      } else {
          Ping.getInstance().results.put(e.getKey(), new Result(false));
      }
    }
  }
}