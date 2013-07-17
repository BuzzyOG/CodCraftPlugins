package com.codcraft.lobby;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;

public class LobbySign {
	private CCLobby plugin;
	public LobbySign(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	public void UpdateSigns() {
		for(Entry<String, Lobby> lobbymap : plugin.configmap.entrySet()) {
			updateSign(lobbymap.getValue());
		}
	}
	
	public void updateSign(Lobby lobby) {
		if(lobby == null) {
			Bukkit.broadcastMessage("Lobby null");
			return;
		}
		if(!(lobby.getSignBlock().getBlock().getState() instanceof Sign)) {
			lobby.getSignBlock().getBlock().setType(Material.WALL_SIGN);
		}
			Sign s = (Sign) lobby.getSignBlock().getBlock().getState();
			
			GameManager gm = plugin.CCAPI.getModuleForClass(GameManager.class);
			Game<?> game = null;
			
			for(Game<?> g : gm.getAllGames()) {
				if(g.getName().equalsIgnoreCase(lobby.getGame())) {
					game = g;
				}
			}
			
			if(game == null) {
				s.setLine(0, lobby.getName());
				s.setLine(1, ChatColor.DARK_RED+"Offline");
				s.setLine(2, "");
				s.setLine(3, "");
				s.update();
				lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 14, true);
			} else {
				s.setLine(0, lobby.getName());
				int i = 0;

				for (Team team : game.getTeams()) {
					i = i + team.getPlayers().size();
				}
				
				int max = 0;
				for(Team team : game.getTeams()) {
				   max += team.getMaxPlayers();
				}
				s.setLine(1, ""+i+"/"+max);
				s.setLine(3, game.getPlugin().getTag());
				if(game.validate()) {
					String time;
					if(game.getCurrentState().getTimeLeft() == -1) {
						time = game.getCurrentState().getId();
					} else {
						time = game.getCurrentState().getId()+ " " + game.getCurrentState().getTimeLeft();
						
					}
					
					s.setLine(2, time);

				}
				s.update();
				if(game.getCurrentState().getTimeLeft() < 5) {
					if(game.getCurrentState().getTimeLeft() % 2 == 0) {
						lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 4, true);
					} else {
						if(i >= max) {
							lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 14, true);
						} else {
							lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 5, true);
						}
					}
				} else {
					if(i >= max) {
						lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 14, true);
					} else {
						lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte) 5, true);
					}
				}
			}
	}

}
