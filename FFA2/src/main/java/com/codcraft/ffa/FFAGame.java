package com.codcraft.ffa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.CodCraft.api.common.Teleport;
import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.hook.FFAHook;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.ScoreBoard;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.codcraft.game.CodCraftMap;
import com.codcraft.codcraft.game.states.LobbyState;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class FFAGame extends CodCraftGame<CodCraftFFA> {

	public FFAGame(CodCraftFFA instance) {
		super(instance);
		//setUpTeams(8, 1, false);
		addHook(new FFAHook(this));
	}
	
	@Override
	public void initialize() {
		ScoreBoard SB = getPlugin().api.getModuleForClass(ScoreBoard.class);
		SB.createScoreBoardForGame(this);
		SB.setObjective("Time Left", this);
		addDefaultStates(this);

	    for(Entry<String, ArrayList<Location>> en : plugin.spawnpoints.entrySet()) {
	    	addMap(new CodCraftMap(en.getKey(), 0, false, true, false));
	    	setSpawns(en.getKey(), en.getValue());
	    }
	    setCurrentMap("Nuketown");
	    setState(new LobbyState(0, this));
	}
	

	@Override
	public void preStateSwitch(GameState state) {
	}
	@Override
	public void postStateSwitch(GameState state) {
	}
	
	public void detectWin() {
		Team team = null;
		int score = 0;
		for(Team t : getTeams()) {
			if(t.getScore() >= score) {
				team = t;
				score = t.getScore();
			}
		}
		GameWinEvent event = new GameWinEvent(team.getName()+" has won!", team, this);
		Bukkit.getPluginManager().callEvent(event);
		Bukkit.broadcastMessage(event.getWinMessage());	
	}
	
	@Override
	public boolean checkWin() {
		for(Team t : getTeams()) {
			if(t.getScore() >= 25) return true;
		}
		return false;
	}
	
	@Override
	public void onWin(GameWinEvent e) {
		CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
		e.setWinMessage(new ArrayList<>(e.getTeam().getPlayers()).get(0).getName()+" has won!");
		for(Team t : e.getGame().getTeams()) {
			if(t.getName().equalsIgnoreCase(e.getTeam().getName())) {
				for(TeamPlayer tp : t.getPlayers()) {
					CCPlayer player = ccpm.getPlayer(tp.getName());
					player.setFFAWins(player.getFFAWins() + 1);
				}
			} else {
				for(TeamPlayer tp : t.getPlayers()) {
					CCPlayer player = ccpm.getPlayer(tp.getName());
					player.setFFALosses(player.getFFALosses() + 1);
				}
			}

		}

	}
	
	

	
	
	@Override
	public void tick() {}
	
	
	
	@Override
	public Location getRespawnLocation(Player p) {
		List<Location> locationlist = spawnPoints.get(getCurrentMap());
	    List<Location> Aloowed = new ArrayList<Location>();
	    int b1 = 0;
	    int b2 = 0;
	    boolean b = false;
	    for(Location loc : locationlist) {
	    	Team team1 = findTeamWithPlayer(p); 
	        for(Player p1 : Teleport.getNearbyEntities(loc, 5)) {
	        	if(p == p1) {
	        		break;
	        	}
	        	Team team2 = findTeamWithPlayer(p1); 
	        	if(team2 == null) {
	        		break;
	        	}
	        	if(team1.getId().equalsIgnoreCase(team2.getId())) {
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
		   Location loc = locationlist.get(0);
		      
		   return new Location(Bukkit.getWorld(getName()), loc.getX(), loc.getY(), loc.getZ());
	    }
	    Random rnd = new Random();
	    int i = rnd.nextInt(Aloowed.size());
	    Location loc = Aloowed.get(i);  
	    return new Location(Bukkit.getWorld(getName()), loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	public void onGUIupdate() {
		updateallgui();
		
	}
	
	
	public void updateallgui() {
		for(Team t : getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				Player p =Bukkit.getPlayer(tp.getName());
				if(p.isOnline()) {
					guiupdate(p);
				}
			}
		}
	}
	
	public void guiupdate(Player p) {
		GUI gui = plugin.api.getModuleForClass(GUI.class);
		TreeMap<String, String> sorted = new TreeMap<>();
		for(Team t : getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				if(sorted.containsKey(""+tp.getKills())) {
					String s = ""+tp.getKills() + "." + "0";
					while(sorted.containsKey(s)) {
						s = s + "0";
					} 
					sorted.put(""+s, tp.getName().substring(0, 4)+"K:"+tp.getKills()+"D:"+tp.getDeaths());
				} else {
					sorted.put(""+tp.getKills(), tp.getName().substring(0, 4)+"K:"+tp.getKills()+"D:"+tp.getDeaths());
				}
			}
		}
		NavigableMap<String, String> sorted2 = sorted.descendingMap();
		gui.updateplayerlist(p, sorted2);
	}

	@Override
	public boolean onDamage(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDeath(Player killer, Player dead) {
		CCPlayerModule ccplayerm = plugin.api.getModuleForClass(CCPlayerModule.class);
		CCPlayer player = ccplayerm.getPlayer(dead);
		player.setFFADeaths(player.getFFADeaths() + 1);
		
		CCPlayer player2 = ccplayerm.getPlayer(killer);
		player2.setFFAKills(player2.getFFAKills() + 1);
		
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}
}