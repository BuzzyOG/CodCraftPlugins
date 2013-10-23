package com.admixhosting.battleroom.command;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattleGame;
import com.admixhosting.battleroom.states.LobbyState;

public class TeamCommand implements CommandExecutor {
	
	private BattleRoom plugin;

	public TeamCommand(BattleRoom plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(label.equalsIgnoreCase("team")) {
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				List<Game<?>> games =  gm.getGamesForPlugin(plugin);
				Game<?> g = null;
				for(Game<?> temp : games) {
					BattleGame g1 = (BattleGame) temp;
					if(g1.getInLobby().contains(p.getName())) {
						g = g1;
					}
				}
				
				if(g == null) {
					p.sendMessage("You must be in a Battleroom game");
					return true;
				}
				if(g.getPlugin() != plugin) {
					p.sendMessage("You must be in a Battleroom game");
					return true;
				}
				BattleGame game = (BattleGame) g;
				if(g.getCurrentState().getId().equalsIgnoreCase(new LobbyState(g).getId())) {
					if(args.length == 0) {
						p.sendMessage("You are on " + game.requestedTeams.get(p.getName()).getName() + " team.");
					} else {
						String team = args[0];
						Team Team1 = game.findTeamWithName(team);
						if(Team1 == null) {
							p.sendMessage("Please type Blue/Red");
							return true;
						}
						//Team Team2 = null;
						for(Team t : game.getTeams()) {
							if(!t.getName().equalsIgnoreCase(Team1.getName())) {
								//Team2 = t;
							}
						}
						if(Team1.getName().equalsIgnoreCase(game.requestedTeams.get(p.getName()).getName())) {
							p.sendMessage("You are already on that team");
						} else {
							int T1 = 0;
							int T2 = 0;
							for(Entry<String, Team> en : game.requestedTeams.entrySet()) {
								if(en.getValue().getName().equalsIgnoreCase(Team1.getName())) {
									T1++;
								} else {
									T2++;
								}
							}
							if(T1 >= T2 + 2) {
								p.sendMessage("Team full");
								return true;
							} else {
								p.sendMessage("Team switched!");
								game.requestedTeams.put(p.getName(), Team1);
								p.teleport(Team1.getSpawn());
								if(Team1.getName().equalsIgnoreCase("Blue")) {
									p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
								} else {
									p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
								}
								return true;
							}
						}
					}
				}

			}
		}
		return false;
	}

}
