package com.CodCraft.sad.GM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.GuiEvent;
import com.CodCraft.api.event.WinEvent;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.TeamPlayer;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.sad.CodCraft;



public class GameListener implements Listener{
	private CodCraft plugin;
	   private GameManager gm; 
	   private TeamPlayer player;
	   private Teleport t;
	   public GameListener(CodCraft plugin) {
		this.plugin = plugin;
		gm = plugin.getApi().getModuleForClass(GameManager.class);
		player = plugin.getApi().getModuleForClass(TeamPlayer.class);
		t = plugin.getApi().getModuleForClass(Teleport.class);
	}
	@EventHandler
	public void onWin(WinEvent e) {
		plugin.getGame();
		if(Game.RoundWinReason.equalsIgnoreCase("Time")) {
			gm.setTeamscore(plugin.getGame().D, gm.getTeamScore(plugin.getGame().D) +1);
		} else {
			plugin.getGame();
			if (Game.RoundWinReason.equalsIgnoreCase("Bomb")) {
				gm.setTeamscore(plugin.getGame().O, gm.getTeamScore(plugin.getGame().O) +1);
			}
		}
		if(gm.getTeamScore(1) == 4) {
			e.setWinMessage("Team " + 1 + " has won the game!");
			e.setTeam(1);
			return;
		} else if (gm.getTeamScore(2) == 4) {
			e.setTeam(2);
			e.setWinMessage("Team " + 2 + " has won the game!");
			return;
		}
		if(gm.getRound() == 3) {
			if(plugin.getGame().D == 1) plugin.getGame().D = 2;
			else if(plugin.getGame().D == 2) plugin.getGame().D = 1;
		
			if(plugin.getGame().O == 1) plugin.getGame().O = 2;
			else if(plugin.getGame().O == 2) plugin.getGame().O = 1;
			
		} 

		gm.setRound(gm.getRound() + 1);
		gm.setGameTimer(120);
		
		t.RespawnAll(gm.GetCurrentWorld());
		e.setCancelled(true);
		
	}
	@EventHandler
	public void onGUI(GuiEvent e) {

		Map<String, String> sss = new HashMap<String, String>();
		for(Entry<String, Integer> es : SAD.Planters.get(1).entrySet()) {
			sss.put(es.getKey(), "Time left "+es.getValue());
		}
		for(Entry<String, Integer> es : SAD.Planters.get(2).entrySet()) {
			sss.put(es.getKey(), "Time left "+es.getValue());
		}
		for(Entry<String, Integer> es : SAD.Defuseers.get(1).entrySet()) {
			sss.put(es.getKey(), "Time left "+es.getValue());
		}
		for(Entry<String, Integer> es : SAD.Defuseers.get(2).entrySet()) {
			sss.put(es.getKey(), "Time left "+es.getValue());
		}
		for (String s : player.Whoplaying()) {
			if(SAD.bombholder.get(gm.GetCurrentWorld()).contains(s)) {
				sss.put(s, "you have bomb!");
			}
		}
		
		e.setPlayerMessagesBefore(sss);
		ArrayList<String> s = new ArrayList<>();
		s.add("Current Round " + gm.getRound());
		if(SAD.Bomb1) {
			s.add("Bomb1 is armed");
			s.add(""+SAD.Bomb1Timer);
		} else {
			s.add("Bomb1 unarmed");
		}
		if(SAD.Bomb2) {
			s.add("Bomb2 is armed");
			s.add(""+SAD.Bomb2Timer);
		} else {
			s.add("Bomb2 unarmed");
		}
		e.setBeforeMessages(s);
	}
	

}
