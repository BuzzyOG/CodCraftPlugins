package com.codcraft.diamondrun;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.diamondrun.states.InGame;

public class DRGame extends Game<DiamondRun> {
	
	public Map<String, Location> spawns = new HashMap<String, Location>();
	
	public String map = null;

	public DRGame(DiamondRun instance) {
		super(instance);
		Team team = new Team("Team");
		team.setMaxPlayers(35);
		teams.add(team);
		knownStates.put(new InGame(this).getId(), new InGame(this));
		setState(new InGame(this));
	}

	@Override
	public void initialize() {
		Bukkit.createWorld(new WorldCreator(getName()));
		for(Entry<String, Location> en : plugin.spawns.entrySet()) {
			Location newloc = new Location(Bukkit.getWorld(getName()), en.getValue().getX(), en.getValue().getY(), 
					en.getValue().getZ(), en.getValue().getYaw(), en.getValue().getPitch());
			spawns.put(en.getKey(), newloc);
		}
	}

	@Override
	public void preStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

}
