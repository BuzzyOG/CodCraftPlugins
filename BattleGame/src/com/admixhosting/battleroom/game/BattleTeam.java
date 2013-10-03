package com.admixhosting.battleroom.game;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

import com.CodCraft.api.model.Team;

public class BattleTeam extends Team {
	
	private Location Spawn = new Location(Bukkit.getWorld("world"), 37, 213, 150);
	private Color color1;

	public BattleTeam(String string, Location loc) {
		super(string);
		this.Spawn = loc;
	}
	
	
	public Color getColorNew() {
		return color1;
	}
	
	public void setColorNew(Color color) {
		this.color1 = color;
	}
	
	public Location getSpawn() {
		return Spawn;
	}

}
