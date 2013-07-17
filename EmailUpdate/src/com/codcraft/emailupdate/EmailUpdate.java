package com.codcraft.emailupdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.codcraft.lobby.CCLobby;
import com.codcraft.lobby.ping.Ping;

public final class EmailUpdate extends JavaPlugin {

	private CCLobby lobbymaster;
	private List<String> pinging = new ArrayList<>();

	public void onEnable() {
		Plugin lm = Bukkit.getPluginManager().getPlugin("CCLobbyMaker");
		if(lm != null) {
			this.lobbymaster = (CCLobby) lm;
		}
		pinging.add("ffa");
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				Map<String, Downtype> downs = new HashMap<String, EmailUpdate.Downtype>();
				for(String server : pinging) {
					Ping ping = lobbymaster.pingManager.getPing(server);
					if(ping != null) {
						if(!ping.isOnline() && !ping.isUpdating()) {
							downs.put(server, Downtype.OFFLINE);
						}
					} else {
						downs.put(server, Downtype.NULL);
					}
				}
				email(downs);
			}
		}, 0, 144000);
	}
	
	private void email(Map<String, Downtype> downs) {
		
	}

	private enum Downtype {
		OFFLINE,
		NULL,
	}
	
}
