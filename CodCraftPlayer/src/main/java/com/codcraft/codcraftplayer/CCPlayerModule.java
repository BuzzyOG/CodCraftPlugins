package com.codcraft.codcraftplayer;

import org.bukkit.entity.Player;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;

public class CCPlayerModule extends CCModule {

	private CCPlayerMain plugin;
	
	public CCPlayerModule(CCPlayerMain plugin, CCAPI api) {
		super(api);
		this.plugin = plugin;
	}
	
	public CCPlayer getPlayer(Player p) {
		if(plugin.players.containsKey(p.getName())) {
			return plugin.players.get(p.getName());
		} else {
			return null;
		}
	}
	
	public boolean removePlayer(String p) {
		if(!plugin.players.containsKey(p)) {
			return true;
		} 
		plugin.players.remove(p);
		if(plugin.players.containsKey(p)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean addPlayer(Player p, CCPlayer player) {
		if(plugin.players.containsKey(p.getName())) {
			return false;
		} else {
			plugin.players.put(p.getName(), player);
		}
		return plugin.players.containsKey(p.getName());
	}
	
	public void updatePlayer(Player p) {
		plugin.getCCDatabase.updatep(p);
	}
	
	public void loadPlayer(String p) {
		plugin.getCCDatabase.getp(p);
	}

	@Override
	public void starting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closing() {
		// TODO Auto-generated method stub
		
	}

	public CCPlayer getPlayer(String p) {
		CCPlayer player = null;
		if(plugin.players.containsKey(p)) player = plugin.players.get(p);
		return player;
	}

}
