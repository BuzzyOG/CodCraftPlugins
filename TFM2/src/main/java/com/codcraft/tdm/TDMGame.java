package com.codcraft.tdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.tdm.states.InGameState;
import com.codcraft.tdm.states.LobbyState;


public class TDMGame extends Game<CodCraftTDM> {	
	
	public String map;
	
	public GameState<?> state;

	public int map1;
	
	public int map2;
	
	public String Map1;
	
	public String Map2;
	
	public ArrayList<String> voters = new ArrayList<>();

	
	
	public TDMGame(CodCraftTDM instance) {
		super(instance);
		TDMTeam t = new TDMTeam();
		t.setColor(ChatColor.RED);
		t.setName("Team1");
		t.setMaxPlayers(6);
		this.addTeam(t);
		TDMTeam t2 = new TDMTeam();
		t2.setColor(ChatColor.BLUE);
		t2.setName("Team1");
		t2.setMaxPlayers(6);
		this.addTeam(t2);
		knownStates.put(new InGameState(this).getId(), new InGameState(this));
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		setState(new LobbyState(this));
	}
	
	@Override
	public void initialize() {
	    map = "Nuketown";
	    for(Team team : teams) {
	    	TDMTeam t = (TDMTeam) team;
	    	if(t.getName().equalsIgnoreCase("Team1")) {
				for(Entry<String, ArrayList<Location>> en : plugin.teamonespawns.entrySet()) {
					List<Location> newspawns = new ArrayList<>();
					for(Location loc : en.getValue()) {
						Bukkit.broadcastMessage(name);
						Location loc1 = new Location(Bukkit.getWorld(name), loc.getX(), loc.getY(), loc.getZ());
						newspawns.add(loc1);
					}
					t.addSpawn(en.getKey(), newspawns);
				}
			} else {
				for(Entry<String, ArrayList<Location>> en : plugin.teamtwospawns.entrySet()) {
					List<Location> newspawns = new ArrayList<>();
					for(Location loc : en.getValue()) {
						Location loc1 = new Location(Bukkit.getWorld(name), loc.getX(), loc.getY(), loc.getZ());
						newspawns.add(loc1);
					}
					t.addSpawn(en.getKey(), newspawns);
				}
			}
	    }
	    
	    
	}
	

	@Override
	public void preStateSwitch(GameState<CodCraftTDM> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CodCraftTDM> state) {
		// TODO Auto-generated method stub
		
	}

	public void detectWin(Game<?> g) {
		Team team = null;
		int score = 0;
		for(Team t : g.getTeams()) {
			if(t.getScore() >= score) {
				team = t;
				score = t.getScore();
			}
		}
		
		GameWinEvent event = new GameWinEvent(team.getName()+" has won!", team, g);
		Bukkit.getPluginManager().callEvent(event);
		Bukkit.broadcastMessage(event.getWinMessage());
		
	}
}
