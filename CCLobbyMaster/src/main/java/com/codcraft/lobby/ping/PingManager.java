package com.codcraft.lobby.ping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.codcraft.lobby.CCLobby;
import com.codcraft.lobby.Module;

public class PingManager {
	
	private List<Ping> servers;
	private CCLobby plugin;
	
	
	public PingManager(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	public void updateTimer() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Ping ping : servers) {
					ping.ping();
				}
			}
		}, 0, 1200);
	}
	
	public Ping getPing(String name) {
		for(Ping ping : servers) {
			if(ping.getName().equalsIgnoreCase(name)) {
				
				return ping;
			}
		}
		return null;
	}
	
	public void updateData(String name) {
		for(Ping ping : servers) {
			if(name.equalsIgnoreCase(ping.getName())) {
				ping.ping();
			}
		}
	}
	
	public void setUp(List<Module> modules) {
		servers = new ArrayList<>();
		for(Module module : modules) {
			Ping ping = new Ping(module.getServer(), module.IP, module.port);
			servers.add(ping);
		}
	}
	
	public void pingServers() {
		for(Ping ping : servers) {
			ping.ping();
		}
	}
	
	public Map<String, List<String>> getPingData() {
		Map<String, List<String>> pingdata = new HashMap<String, List<String>>();
		for(Ping ping : servers) {
			pingdata.put(ping.getName(), ping.getData());
		}
		return pingdata;
	}
	
	public List<String> getPingForServer(String name) {
		for(Ping ping : servers) {
			if(ping.getName().equalsIgnoreCase(name)) {
				return ping.getData();
			}
		}
		return null;
	}

}
