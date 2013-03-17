package com.codcraft.lobby;

import java.util.Map.Entry;

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
			if(!(lobbymap.getValue().getSignBlock().getBlock().getState() instanceof Sign)) {
				lobbymap.getValue().getSignBlock().getBlock().setType(Material.WALL_SIGN);
			}
				Sign s = (Sign) lobbymap.getValue().getSignBlock().getBlock().getState();
				
				GameManager gm = plugin.CCAPI.getModuleForClass(GameManager.class);
				Game<?> game = null;
				
				for(Game<?> g : gm.getAllGames()) {
					if(g.getName().equalsIgnoreCase(lobbymap.getValue().getGame())) {
						game = g;
					}
				}
				
				if(game == null) {
					s.setLine(0,lobbymap.getValue().getName());
					s.setLine(1, ChatColor.DARK_RED+"Offline");
					s.setLine(2, "");
					s.setLine(3, "");
					s.update();
					lobbymap.getValue().getLampblock().getBlock().setTypeIdAndData(35, (byte) 14, true);
				} else {
					s.setLine(0, lobbymap.getValue().getName());
					int i = 0;

					for (Team team : game.getTeams()) {
						i = i + team.getPlayers().size();
					}
					
					int max = 0;
					for(Team team : game.getTeams()) {
					   max += team.getMaxPlayers();
					}
					s.setLine(1, ""+i+"/"+max);
					if(game.getPlugin().getName().equalsIgnoreCase("FFA")) {
						if(plugin.FFA != null) {
							//if(plugin.FFA.getGame().currentmap != null) {
								//s.setLine(2, plugin.FFA.getGame().currentmap.getName());
							s.setLine(2, "Running FFA");
							//}
						}
					} else {
					}
					s.setLine(2, game.getPlugin().getTag());
					s.setLine(3, "");
					s.update();
					if(i >= max) {
						lobbymap.getValue().getLampblock().getBlock().setTypeIdAndData(35, (byte) 14, true);
					} else {
						lobbymap.getValue().getLampblock().getBlock().setTypeIdAndData(35, (byte) 5, true);
					}
				}

			    
			

		}
	}

}
