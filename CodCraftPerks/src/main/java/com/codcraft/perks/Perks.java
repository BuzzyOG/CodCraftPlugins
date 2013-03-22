package com.codcraft.perks;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.codcraft.ffa.CodCraftFFA;
import com.codcraft.infected.CodCraftInfected;
import com.codcraft.perks.lightweight.LightWight;
import com.codcraft.perks.marathon.Marathon;
import com.codcraft.perks.scavenger.Scavenger;
import com.codcraft.tdm.CodCraftTDM;

public class Perks extends JavaPlugin {
	
	public LightWight lw;
	public Scavenger sc;
	public Marathon m;
	public CCAPI api;
	public CodCraftFFA ffa;
	public CodCraftInfected Infected;
	public CodCraftTDM TDM;
	public void onEnable() {
		final Plugin apiplugin = getServer().getPluginManager().getPlugin("CodCraftAPI");
		if(apiplugin == null) {
			getLogger().info("CCAPI not found disabling");
			getServer().getPluginManager().disablePlugin(this);
		} else {
			api = (CCAPI) apiplugin;
		}		
		final Plugin ffa = getServer().getPluginManager().getPlugin("FFA");
		if(ffa == null) {
			getLogger().info("ffa not found disabling");
			getServer().getPluginManager().disablePlugin(this);
		} else {
			this.ffa = (CodCraftFFA) ffa;
		}	
		final Plugin infected = getServer().getPluginManager().getPlugin("Infected");
		if(infected == null) {
			getLogger().info("ffa not found disabling");
		} else {
			this.Infected = (CodCraftInfected) infected;
		}
		final Plugin TDM = getServer().getPluginManager().getPlugin("TDM");
		if(TDM == null) {
			getLogger().info("ffa not found disabling");
		} else {
			this.TDM = (CodCraftTDM) TDM;
		}
		this.lw = new LightWight(this);
		this.m = new Marathon(this);
		this.sc = new Scavenger(this);
	}
	
	public boolean checkIfGameIsInstanceOfPlugin(Game<?> g) {
		if(g == null) {
			throw new IllegalArgumentException("Game can not be null");
		}
		Plugin plug = g.getPlugin();
		if(plug == ffa) return true;
		if(plug == Infected) return true;
		if(plug == TDM) return true;
		return false;
	}
	
}
