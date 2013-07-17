package com.codcraft.lobby;

import java.util.Map;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;

public class LobbyModule extends CCModule{
	private CCLobby plugin;
	public LobbyModule(CCAPI cCAPI, CCLobby plugin) {
		super(cCAPI);
		this.plugin = plugin;
	}
	
	public void UpdateSigns() {
		plugin.sign.UpdateSigns();
	}
	
	public void UpdateSign(Lobby lobby) {
		plugin.sign.updateSign(lobby);
	}
	
	public Lobby getLobby(String s) {
		if(plugin.configmap.get(s) != null) {
			return plugin.configmap.get(s);
		}
		return null;
	}
	
	public Map<String, Lobby> getLobbys() {
		return plugin.configmap;
	}
	
	public void addLobby(String string, Lobby lobby) {
		plugin.configmap.put(string, lobby);
	}
	public void removeLobby(String string) {
		plugin.configmap.remove(string);
	}
	
	public boolean isLobby(String string) {
		return plugin.configmap.containsKey(string);
	}
	
	public boolean isLobby(Lobby lobby) {
		return plugin.configmap.containsValue(lobby);
	}
	

	@Override
	public void starting() {
	}

	@Override
	public void closing() {
	}



}
