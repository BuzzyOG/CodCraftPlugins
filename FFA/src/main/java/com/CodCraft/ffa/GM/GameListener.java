package com.CodCraft.ffa.GM;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.ffa.CodCraft;

public class GameListener implements Listener {
	private CodCraft codCraft;

	public GameListener(CodCraft codCraft) {
		this.codCraft = codCraft;
	}
	@EventHandler
	public void onWin(GameWinEvent e) {
		/*Teams t = codCraft.getApi().getModuleForClass(Teams.class);
		if(t.getTeams(e.getTeam()) == null) {
			e.setWinMessage("TIE!!??!?!?");
			return;
		}
		if(t.getTeams(e.getTeam()) == null){
			return;
		}
		e.setWinMessage(""+t.getTeams(e.getTeam()).get(0)+" has won the Game!");
	}*/
	}
	
	public Location Respawn(Player p, World map) {
	      Game<CodCraft> game = codCraft.getGame().game;
	      GameManager gm = codCraft.getApi().getModuleForClass(GameManager.class);
	      List<Location> locationlist = codCraft.getGame().locations.get(map.getName());
	      List<Location> Aloowed = new ArrayList<Location>();
	      int b1 = 0;
	      int b2 = 0;
	      boolean b = false;
	      for(Location loc : locationlist) {
	         for(TeamPlayer p1 : getNearbyEntities(loc, 5)) {
	        	 Team teamplayer = game.findTeamWithPlayer(p);
	        	 Team teamplayer2 = game.findTeamWithPlayer(p1);
	        	 if(teamplayer.getId() == teamplayer2.getId()) {
	        		 b1++;
	        	 } else {
	        		 b2++;
	        	 }

	         }
	         if(b1 >= 1) {
	            b = true;
	         }
	         if(b2 >= 1) {
	            b = false;
	         } else {
	            b = true;
	         }
	         if(b) {
	            Aloowed.add(loc);
	         }
	      }
	      if(Aloowed.size() == 0) {
	         return locationlist.get(0);
	      }
	      Random rnd = new Random();
	      int i = rnd.nextInt(Aloowed.size());
	      return Aloowed.get(i);

	   }
	   public List<TeamPlayer> getNearbyEntities(Location where, int range) {
		      List<TeamPlayer> found = new ArrayList<TeamPlayer>();
		      Game<CodCraft> game = codCraft.getGame().game;
		      for(Team team : game.getTeams()) {
			      for(TeamPlayer p : team.getPlayers()) {
			    	  Player player = Bukkit.getPlayer(p.getName());
				         if(isInBorder(where, player.getLocation(), range)) {
				            found.add(p);
				         }
				      }
		      }

		      return found;
		   }

		   public boolean isInBorder(Location center, Location notCenter, int range) {
		      int x = center.getBlockX(), z = center.getBlockZ();
		      int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

		      if(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
		         return false;
		      }
		      return true;
		   }
	
	

}
