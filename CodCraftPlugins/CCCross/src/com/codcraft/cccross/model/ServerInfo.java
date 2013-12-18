package com.codcraft.cccross.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.hook.Hook;
import com.CodCraft.api.modules.GameManager;

public class ServerInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6828052809074302371L;

	private String name;
	
	private double tps;
	
	private List<GameInfo> gameInfo = new ArrayList<>();
	
	private String address;
	
	private String port;
	
	private String id;
	
	private String bungeeid;
	
	private List<String> players = new ArrayList<>();

	
	
	public ServerInfo(String id, String bid) {
		this.id = id;
		this.bungeeid = bid;
	}

	//Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTps() {
		return tps;
	}

	public void setTps(double tps) {
		this.tps = tps;
	}

	public List<GameInfo> getGameInfo() {
		return gameInfo;
	}

	public void addGameInfo(GameInfo info) {
		gameInfo.add(info);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBungeeid() {
		return bungeeid;
	}

	public void setBungeeid(String bungeeid) {
		this.bungeeid = bungeeid;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public void gen(CCAPI api) {
		setName(Bukkit.getName());
		setTps(50);
		setAddress(Bukkit.getIp());
		setPort(""+Bukkit.getPort());
		List<String> players = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			players.add(p.getName());
		}
		setPlayers(players);
		GameManager gm = api.getModuleForClass(GameManager.class);
		List<GameInfo> gameinfo = new ArrayList<>();
		for(Game<?> g : gm.getAllGames()) {
			GameInfo gi = new GameInfo(g.getPlugin(), g.getId());
			if(gi != null) {
				gi.setGameState(g.getCurrentState().getId());
				List<String> gss = new ArrayList<>();
				for(Entry<String, GameState> gs : g.getKnownStates().entrySet()) {
					gss.add(gs.getKey());
				}
				gi.setGamestates(gss);
				List<String> hooks = new ArrayList<>();
				for(Hook hook : g.getHooks()) {
					hooks.add(hook.getName());
				}
				gi.setHookNames(hooks);
				gi.setName(g.getName());
				List<String> tps = new ArrayList<>();
				for(Team t : g.getTeams()) {
					for(TeamPlayer tp : t.getPlayers()) {
						tps.add(tp.getName());
					}
				}
				gi.setPlayernames(tps);
			}
			gameinfo.add(gi);
		}
		this.gameInfo = gameinfo;
	}
	

}
