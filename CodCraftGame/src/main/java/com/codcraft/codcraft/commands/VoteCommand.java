package com.codcraft.codcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.codcraft.CodCraftPlugin;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.codcraft.game.states.LobbyState;


public class VoteCommand implements CommandExecutor {
	
	private CodCraftPlugin plugin;
	public VoteCommand(CodCraftPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("vote")) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer((Player) sender);
			if(g != null) {
				if(g instanceof CodCraftGame<?>) {
					CodCraftGame<?> game = (CodCraftGame<?>) g;
					if(game.getCurrentState().getId().equalsIgnoreCase(new LobbyState(0, game).getId())) {
						if(game.voters.contains(sender.getName())) {
							sender.sendMessage("You have already voted!");
							return true;
						}
						if(args.length == 1) {
							if(game.Map1.getName().equalsIgnoreCase(args[0])) {
								game.map1++;
								game.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								sender.sendMessage("Current votes for " + args[0] + " are " + game.map1);
								return true;
							}
							if( game.Map2.getName().equalsIgnoreCase(args[0])) {
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
