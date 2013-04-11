package com.codcraft.lobby;

import java.util.Map;
import java.util.Map.Entry;

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
	
	
	public Map<String, Lobby> getLobby() {
		return plugin.configmap;
	}
	
	public void addLobby(String string, Lobby lobby) {
		plugin.configmap.put(string, lobby);
	}
	public void removeLobby(String string) {
		plugin.configmap.remove(string);
	}
	
	public boolean isLobby(String string) {
		if(plugin.configmap.containsKey(string)) {
			return true;
		} 
		return false;
	}
	
	public boolean isLobby(Lobby lobby) {
		for(Entry<String, Lobby> lobbymap : plugin.configmap.entrySet()) {
			if(lobbymap.getValue().equals(lobby)) {
				return true;
			}
		}
		return false;
	}
	

	@Override
	public void starting() {
		
		
	}

	@Override
	public void closing() {
		// TODO Auto-generated method stub
		
	}



}
