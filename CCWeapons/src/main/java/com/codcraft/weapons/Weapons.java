package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.codcraft.cac.Cac;
import com.codcraft.ffa.CodCraftFFA;
import com.codcraft.infected.CodCraftInfected;
import com.codcraft.tdm.CodCraftTDM;


public class Weapons extends JavaPlugin {
	
	public CCAPI api;
	public Cac cac;
	public List<String> reloaders = new ArrayList<>();
	public CodCraftFFA ffa;
	public CodCraftInfected Infected;
	public CodCraftTDM TDM;

	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		final Plugin cccac = Bukkit.getPluginManager().getPlugin("Cac");
		if(cccac == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		cac = (Cac) cccac;
		getServer().getPluginManager().registerEvents(new SniperWeapon(this), this);
		getServer().getPluginManager().registerEvents(new SMGWeapon(this), this);
		getServer().getPluginManager().registerEvents(new LMGWeapon(this), this);
		getServer().getPluginManager().registerEvents(new ShotGunWeapon(this), this);
		getServer().getPluginManager().registerEvents(new RoitSheildWeapon(this), this);
		getServer().getPluginManager().registerEvents(new C4(this), this);
		
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
	}
	
	public boolean checkIfGameIsInstanceOfPlugin(Game<?> g) {
		if(g == null) {
			return false;			
		}
		Plugin plug = g.getPlugin();
		if(plug == ffa) return true;
		if(plug == Infected) return true;
		if(plug == TDM) return true;
		return false;
	}
	

	
	
	

}
