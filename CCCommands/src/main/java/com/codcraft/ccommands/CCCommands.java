package com.codcraft.ccommands;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.ccplotme.PlotMe;
import com.codcraft.ccssbb.CCSSBB;
import com.codcraft.ccuhc.UHC;
import com.codcraft.ffa.CodCraftFFA;
import com.codcraft.infected.CodCraftInfected;
import com.codcraft.lobby.CCLobby;
import com.codcraft.tdm.CodCraftTDM;

public class CCCommands extends JavaPlugin {

	public CCAPI api;
	public CodCraftFFA ffa;
	public UHC uhc;
	public CCLobby lobby;
	public CCSSBB ssb;
	public AdminCommand com;
	public CodCraftTDM tdm;
	public CodCraftInfected inf;
	public PlotMe plotme;

	public void onEnable() {
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {
	         this.api = (CCAPI) api;
	      }
	    getServer().getPluginManager().registerEvents(new CCListener(this), this);  
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("a").setExecutor(new AdminCommand(this));
		final Plugin ffa = this.getServer().getPluginManager().getPlugin("FFA");
	      if(ffa != null || !(ffa instanceof CCAPI)) {
	         this.ffa = (CodCraftFFA) ffa;
	      }
	      final Plugin uhc = this.getServer().getPluginManager().getPlugin("CCUHC");
	      	if(uhc != null || !(uhc instanceof CCAPI)) {
	      		this.uhc = (UHC) uhc;
		    }
		  final Plugin lobby = this.getServer().getPluginManager().getPlugin("CCLobby");
		   	if(lobby != null || !(lobby instanceof CCAPI)) {
		   		this.lobby = (CCLobby) lobby;
			}
		  final Plugin ssb = this.getServer().getPluginManager().getPlugin("ccssbb");
			if(ssb != null || !(ssb instanceof CCAPI)) {
				this.ssb = (CCSSBB) ssb;
			}
		final Plugin tdm = this.getServer().getPluginManager().getPlugin("TDM");
			if(tdm != null || !(tdm instanceof CCAPI)) {
				this.tdm = (CodCraftTDM) tdm;
			}
			final Plugin inf = this.getServer().getPluginManager().getPlugin("Infected");
			if(inf != null || !(inf instanceof CCAPI)) {
				this.inf = (CodCraftInfected) inf;
			}
			final Plugin plotme = this.getServer().getPluginManager().getPlugin("CCPloteMe");
			if(plotme != null || !(plotme instanceof CCAPI)) {
				this.plotme = (PlotMe) plotme;
			}
			com = new AdminCommand(this);
	      
	}
	
	
}
