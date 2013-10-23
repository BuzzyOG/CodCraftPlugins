package com.codcraft.ccuhc;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class UHC extends CCGamePlugin {
	
	public CCAPI api;
	
	
	public void onEnable() {
		final Plugin pluginapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(pluginapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		} else {
			api = (CCAPI) pluginapi;
		}
		getCommand("uhc").setExecutor(new UHCCommand(this));
		getServer().getPluginManager().registerEvents(new UHClistener(this), this);
		//timer();
	}
	
	public void makegame(String[] name) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		Game<?> game = new UHCGame(this);
		game.setName(name[0]);
		gm.registerGame(game);
	}
	@Override
	public String getTag() {
		return "[UHC]";
	}
	
	public Location scatterPlayerRandom(Player p, World w) {
	    Random random = new Random();
	   GameManager gm = api.getModuleForClass(GameManager.class);
	   Game<?> g = gm.getGameWithPlayer(p);
	   int range = 0;
	   if(g != null) {
		   if(g.getPlugin() == this) {
			   UHCGame game = (UHCGame) g;
			   range = game.getRadius();
			   boolean b = false;
			    Location finalTeleport = null;
			    while(!b) {
			    	int x = random.nextInt(range) - 1;
				    if(random.nextInt(1) == 1) {
				    	x = x * -1;
				    }
				    int z = random.nextInt(range) - 1;
				    if(random.nextInt(1) == 1) {
				    	z = z * -1;
				    }
				   finalTeleport = new Location(w, x, 65, z);
				    finalTeleport.setY(getSafeY(finalTeleport));
				    if(finalTeleport.getBlock().getType() != Material.WATER) {
				    	if(finalTeleport.getBlock().getRelative(BlockFace.DOWN).getType() != Material.WATER) {
				    		b = true;
				    	}
				    }
			    }
			    
			    
			      return finalTeleport;
		   }
	   }
	return null;
	    
	  }
	
	  private int getSafeY(Location loc) {
		    return loc.getWorld().getHighestBlockAt(loc).getY() - 1;
		  }
	
	/*public void timer() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				for(Entry<String, GameModel> model : games.entrySet()) {
					if(!model.getValue().isRunning()) {
						Game<?> g = api.getModuleForClass(GameManager.class).getGameWithId(model.getKey());
						if(g.getTeams().get(0).getPlayers().size() != 0) {
							if(model.getValue().getTimeleft() <= 0) {
								model.getValue().setRunning(true);
							}
							model.getValue().setTimeLeft(model.getValue().getTimeleft() - 1);
						}
					}
				}
				
			}
		}, 20L, 20L);
	}*/
}
