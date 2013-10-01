package com.codcraft.lobby;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;
import com.codcraft.lobby.signs.LobbyUpdate;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Map.Entry;

public class LobbyModule extends CCModule
{
  private CCLobby plugin;

  public LobbyModule(CCAPI cCAPI, CCLobby plugin)
  {
    super(cCAPI);
    this.plugin = plugin;
  }

  public Map<Integer, Lobby> getLobbys() {
    return ImmutableMap.copyOf(this.plugin.configmap);
  }

  public Map<Integer, LobbyUpdate> getUpdateCodes() {
    return ImmutableMap.copyOf(this.plugin.updates);
  }

  public boolean setUpdateCode(int id, LobbyUpdate LU) {
    this.plugin.updates.put(id, LU);
    return this.plugin.updates.containsKey(Integer.valueOf(id));
  }

  public void UpdateSign(Lobby lobby) {
    for (Entry<Integer, Lobby> en : this.plugin.configmap.entrySet())
      if (((Lobby)en.getValue()).getName().equalsIgnoreCase(lobby.getName())) {
        int i = ((Integer)en.getKey()).intValue();
        ((LobbyUpdate)this.plugin.updates.get(Integer.valueOf(i))).updateSign();
      }
  }

  public Lobby getLobby(String s) {
    for (Entry<Integer, Lobby> entrs : this.plugin.configmap.entrySet()) {
      if (((Lobby)entrs.getValue()).getGame().equalsIgnoreCase(s)) {
        return (Lobby)entrs.getValue();
      }
    }
    return null;
  }

  public Lobby getLobby(int id) {
    return (Lobby)this.plugin.configmap.get(Integer.valueOf(id));
  }

  public void addLobby(Integer ints, Lobby lobby) {
    this.plugin.configmap.put(ints, lobby);
  }
  public void removeLobby(String string) {
    this.plugin.configmap.remove(string);
  }

  public boolean isLobby(String string) {
    return this.plugin.configmap.containsKey(string);
  }

  public boolean isLobby(Lobby lobby) {
    return this.plugin.configmap.containsValue(lobby);
  }

  public void starting()
  {
  }

  public void closing()
  {
  }
}