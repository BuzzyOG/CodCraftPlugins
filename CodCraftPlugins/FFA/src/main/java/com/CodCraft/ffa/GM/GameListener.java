package com.CodCraft.ffa.GM;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.WinEvent;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.ffa.CodCraft;

public class GameListener implements Listener {
	private CodCraft codCraft;

	public GameListener(CodCraft codCraft) {
		this.codCraft = codCraft;
	}
	@EventHandler
	public void onWin(WinEvent e) {
		Teams t = codCraft.getApi().getModuleForClass(Teams.class);
		if(t.getTeams(e.getTeam()) == null) {
			e.setWinMessage("TIE!!??!?!?");
			return;
		}
		if(t.getTeams(e.getTeam()) == null){
			return;
		}
		e.setWinMessage(""+t.getTeams(e.getTeam()).get(0)+" has won the Game!");
	}

}
