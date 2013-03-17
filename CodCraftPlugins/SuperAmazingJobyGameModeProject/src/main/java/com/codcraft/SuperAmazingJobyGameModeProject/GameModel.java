package com.codcraft.SuperAmazingJobyGameModeProject;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class GameModel extends Game<SAJGMP> {

	private SAJGMP plugin;
	
	public String map = "Test";
	
	public GameModel(SAJGMP instance) {
		super(instance);
		this.plugin = instance;
	}

	@Override
	public void initialize() {
		
		Team t0 = new Team();
		t0.setName("0");
		t0.setMaxPlayers(5);
		t0.setColor(ChatColor.RED);
		Team t1 = new Team();
		t1.setName("1");
		t1.setMaxPlayers(5);
		t1.setColor(ChatColor.BLUE);
		Team t2 = new Team();
		t2.setName("2");
		t2.setMaxPlayers(5);
		t2.setColor(ChatColor.DARK_PURPLE);
		Team t3 = new Team();
		t3.setName("3");
		t3.setMaxPlayers(5);
		t3.setColor(ChatColor.GRAY);
		Team t4 = new Team();
		t4.setName("4");
		t4.setMaxPlayers(5);
		t4.setColor(ChatColor.YELLOW);
		Team t5 = new Team();
		t5.setName("5");
		t5.setMaxPlayers(5);
		t5.setColor(ChatColor.AQUA);
		Team t6 = new Team();
		t6.setName("6");
		t6.setMaxPlayers(5);
		t6.setColor(ChatColor.DARK_RED);
		Team t7 = new Team();
		t7.setName("7");
		t7.setMaxPlayers(5);
		t7.setColor(ChatColor.GOLD);
		Team t8 = new Team();
		t8.setName("8");
		t8.setMaxPlayers(5);
		t8.setColor(ChatColor.BLACK);
		Team t9 = new Team();
		t9.setName("9");
		t9.setMaxPlayers(5);
		t9.setColor(ChatColor.WHITE);
		addTeam(t1);
		addTeam(t2);
		addTeam(t3);
		addTeam(t4);
		addTeam(t5);
		addTeam(t6);
		addTeam(t7);
		addTeam(t8);
		addTeam(t9);
		addTeam(t0);
		plugin.games.put(getId(), this);
		Bukkit.createWorld(new WorldCreator(getName()));
	}

	@Override
	public void preStateSwitch(GameState<SAJGMP> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<SAJGMP> state) {
		// TODO Auto-generated method stub
		
	}

}
