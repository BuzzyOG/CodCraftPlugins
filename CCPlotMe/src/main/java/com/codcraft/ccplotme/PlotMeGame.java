package com.codcraft.ccplotme;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.worldcretornica.plotme.PlotGen;
import com.worldcretornica.plotme.PlotMapInfo;

public class PlotMeGame extends Game<PlotMe> {

	private PlotMe plugin;
	
	public PlotMeGame(PlotMe instance) {
		super(instance);
		this.plugin = instance;
	}

	@Override
	public void initialize() {
		Team t = new Team();
		t.setMaxPlayers(50);
		teams.add(t);
		
		PlotMapInfo pmi = new PlotMapInfo();
		com.worldcretornica.plotme.PlotMe.plotmaps.put(getName().toLowerCase(), plugin.addDefaultpmi(getName(), pmi));
		Bukkit.createWorld(new WorldCreator(getName()).generator(new PlotGen(pmi)));
	}
	
	@Override
	public void deinitialize() {
		com.worldcretornica.plotme.PlotMe.plotmaps.remove(getName());
		
	}

	@Override
	public void preStateSwitch(GameState<PlotMe> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<PlotMe> state) {
		// TODO Auto-generated method stub
		
	}

}
