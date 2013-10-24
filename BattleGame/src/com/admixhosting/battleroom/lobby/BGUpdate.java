package com.admixhosting.battleroom.lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattleGame;
import com.admixhosting.battleroom.states.InGameState;
import com.admixhosting.battleroom.states.LobbyState;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;
import com.codcraft.lobby.signs.LobbyUpdate;

public class BGUpdate extends LobbyUpdate {
	
	private BattleRoom plugin;
	private List<Location> locs = new ArrayList<>();

	public BGUpdate(BattleRoom plugin, int id) {
	    this.plugin = plugin;
	    this.id = id;
	    System.out.println(id);
	    YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File("./plugins/BattleRoom/lobby.yml"));
	    int x1 = Integer.parseInt(yaml.getString("lobby." + id + ".Location1.x"));
	    int y1 = Integer.parseInt(yaml.getString("lobby." + id + ".Location1.y"));
	    int z1 = Integer.parseInt(yaml.getString("lobby." + id + ".Location1.z"));
	    int x2 = Integer.parseInt(yaml.getString("lobby." + id + ".Location2.x"));
	    int y2 = Integer.parseInt(yaml.getString("lobby." + id + ".Location2.y"));
	    int z2 = Integer.parseInt(yaml.getString("lobby." + id + ".Location2.z"));

		int minX;
		int maxX;
		int maxZ;
		int minZ;
		int maxY;
		int minY;
		if(x1 > x2) {
			maxX = x1;
			minX = x2;
		} else {
			maxX = x1;
			minX = x2;
		}
		if(y1 > y2) {
			maxY = y1;
			minY = y2;
		} else {
			maxY = y2;
			minY = y1;
		}
		if(z1 > z2) {
			maxZ = z1;
			minZ = z2;
		} else {
			maxZ = z2;
			minZ = z1;
		}
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				 for (int z = minZ; z <= maxZ; z++) {
					 Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
					 if(loc.getBlock().getType() == Material.STAINED_CLAY) {
						 locs.add(loc);
					 }
				 }
			}
		}
			 
	    
	}
	@SuppressWarnings("deprecation")
	public boolean updateSign() {
		LobbyModule lm = (LobbyModule)this.plugin.api.getModuleForClass(LobbyModule.class);
	    Lobby lobby = lm.getLobby(this.id);
	    if (lobby == null) {
	      return false;
	    }
	    GameManager gm = (GameManager)this.plugin.api.getModuleForClass(GameManager.class);
	    Game<?> game = null;
	    for (Game<?> g : gm.getAllGames()) {
	      if (g.getName().equalsIgnoreCase(lobby.getGame())) {
	        game = g;
	      }
	    }
	    Sign s = (Sign)lobby.getSignBlock().getBlock().getState();
	    if (game == null) {
	      s.setLine(0, lobby.getName());
	      s.setLine(1, ChatColor.DARK_RED + "Offline");
	      s.setLine(2, "");
	      s.setLine(3, "");
	      s.update();
	      for(Location loc : locs) {
	    	  loc.getBlock().setData((byte) 14);
	      }
	      lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
	    } else {
	      if (game.getPlugin() != this.plugin) {
	        return false;
	      }
	      int max = 0;
	      for (Team team : game.getTeams()) {
	        max += team.getMaxPlayers();
	      }
	      int i = 0;
	      if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
	    	  i = ((BattleGame)game).getInLobby().size();
	      } else {
		      for (Team team : game.getTeams()) {
			        i += team.getPlayers().size();
		      }
	      }
	      if(game.getCurrentState().getId().equalsIgnoreCase(new InGameState(game).getId())) {
		        s.setLine(0, lobby.getName());
		        s.setLine(1, i + "/" + max);
		        s.setLine(2, game.getCurrentState().getId() + ": " + game.getCurrentState().getTimeLeft());
		        s.setLine(3, ChatColor.DARK_RED + "InGAME!");
		        s.update();
			      for(Location loc : locs) {
			    	  loc.getBlock().setData((byte) 14);
			      }
		        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
	      } else {
	    	  if (i >= max) {
	  	        s.setLine(0, lobby.getName());
	  	        s.setLine(1, i + "/" + max);
	  	        s.setLine(2, game.getCurrentState().getId() + ": " + game.getCurrentState().getTimeLeft());
	  	        s.setLine(3, ChatColor.DARK_RED + "Game Full");
	  	        s.update();
	  	      for(Location loc : locs) {
		    	  loc.getBlock().setData((byte) 14);
		      }
	  	        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
	  	      } else {
	  	        s.setLine(0, lobby.getName());
	  	        s.setLine(1, i + "/" + max);
	  	        s.setLine(2, game.getCurrentState().getId() + ": " + game.getCurrentState().getTimeLeft());
	  	        s.setLine(3, ChatColor.GREEN + "Joinable");
	  	        s.update();
	  	      for(Location loc : locs) {
		    	  loc.getBlock().setData((byte) 13);
		      }
	  	        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)5, true);
	  	      }
	      }
	     
	    }
	    return true;
	  }

}
