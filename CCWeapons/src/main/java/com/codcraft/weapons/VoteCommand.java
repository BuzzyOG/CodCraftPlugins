package com.codcraft.weapons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.ffa.FFAGame;
import com.codcraft.ffa.states.LobbyState;
import com.codcraft.tdm.TDMGame;

public class VoteCommand implements CommandExecutor {
	private Weapons plugin;
	public VoteCommand(Weapons plugin) {
		this.plugin = plugin;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("vote")) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer((Player) sender);
			if(g != null) {
				if(g.getPlugin() == plugin.ffa) {
					FFAGame game = (FFAGame) g;
					if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(game).getId())) {
						if(game.voters.contains(sender.getName())) {
							sender.sendMessage("You have already voted!");
							return true;
						}
						if(args.length == 1) {
							if(game.Map1.equalsIgnoreCase(args[0])) {
								game.map1++;
								game.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								sender.sendMessage("Current votes for " + args[0] + " are " + game.map1);
								return true;
							}
							if( game.Map2.equalsIgnoreCase(args[0])) {
								game.map2++;
								game.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								sender.sendMessage("Current votes for " + args[0] + " are " + game.map2);
								
								return true;
							}
						}
					}
				} else if (g.getPlugin() == plugin.TDM) {
					TDMGame game = (TDMGame) g;
					if(game.getCurrentState().getId().equalsIgnoreCase(new com.codcraft.tdm.states.LobbyState(game).getId())) {
						if(game.voters.contains(sender.getName())) {
							sender.sendMessage("You have already voted!");
							return true;
						}
						if(args.length == 1) {
							if(game.Map1.equalsIgnoreCase(args[0])) {
								game.map1++;
								game.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								sender.sendMessage("Current votes for " + args[0] + " are " + game.map1);
								return true;
							}
							if( game.Map2.equalsIgnoreCase(args[0])) {
								game.map2++;
								game.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								sender.sendMessage("Current votes for " + args[0] + " are " + game.map2);
								
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
