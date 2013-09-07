package com.codcraft.ffa;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.ScoreBoard;
import com.codcraft.ffa.states.InGameState;
import com.codcraft.ffa.states.LobbyState;

public class FFAGame extends Game<CodCraftFFA> {

	public String map;
	public int map1;
	public int map2;
	public String Map1;
	public String Map2;	
	public ArrayList<String> voters = new ArrayList<>();

	public FFAGame(CodCraftFFA instance) {
		super(instance);
		for (int count = 1; count <= 8; count++){
			Team t = new Team();
			t.setName("Team"+count);
			t.setMaxPlayers(1);
			this.addTeam(t);
		}
	}
	@Override
	public void initialize() {
		ScoreBoard SB = getPlugin().api.getModuleForClass(ScoreBoard.class);
		SB.createScoreBoardForGame(this);
		SB.setObjective("Time Left", this);
		knownStates.put(new InGameState(this).getId(), new InGameState(this));
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		setState(new LobbyState(this));
	    map = "Nuketown";
	}
	@Override
	public void preStateSwitch(GameState<CodCraftFFA> state) {
	}
	@Override
	public void postStateSwitch(GameState<CodCraftFFA> state) {
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