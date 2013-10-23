package com.codcraft.ccommands;

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.hook.Hook;
import com.CodCraft.api.modules.GameManager;

public class GameInfoCommand implements CommandExecutor {
	private GameManager gm;

	public GameInfoCommand(CCCommands plugin) {
		gm = plugin.api.getModuleForClass(GameManager.class);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		if(label.equalsIgnoreCase("gameinfo")) {
			if(!sender.hasPermission("CodCraft.GameInfo")) {
				sender.sendMessage("You do not have permission!");
				return true;
			}
			if(args.length == 0) {
				for(Game<?> g1 : gm.getAllGames()) {
					sender.sendMessage(g1.getName());
				}
				return true;
			}
			if(args.length == 1) {
				Game<?> g = null;
				for(Game<?> g1 : gm.getAllGames()) {
					if(g1.getName().equalsIgnoreCase(args[0])) {
						g = g1;
						break;
					}
				}
				if(g != null) {
					sender.sendMessage("=============="+g.getName()+"==============");
					sender.sendMessage("ID: "+g.getId());
					String Teams = "Teams: ";
					for(Team team : g.getTeams()) {
						Teams = Teams + team.getName() + ", ";
					}
					sender.sendMessage(Teams);
					String Players = "Players: ";
					for(Team team : g.getTeams()) {
						for(TeamPlayer tp : team.getPlayers()) {
							Players = Players + tp.getName() + ", ";
						}
					}
					sender.sendMessage(Players);
					String states = "States: ";
					for(Entry<String, ?> state : g.getKnownStates().entrySet()) {
						states = states + state.getKey() + ", ";
					}
					sender.sendMessage(states);
					if(g.getCurrentState() != null) {
						sender.sendMessage("Current State: "+g.getCurrentState().getId()+ " Time Left: "+ g.getCurrentState().getTimeLeft());
					}
					sender.sendMessage("Round: " + g.getRound());
					String hooks = "Hooks: ";
					for(Hook hook : g.getHooks()) {
						if(hook.isEnabled()) {
							hooks = hooks + ChatColor.GREEN + hook.getName();
						} else {
							hooks = hooks + ChatColor.RED + hook.getName();
						}
						hooks = hooks + ChatColor.WHITE + ", ";
					}
					sender.sendMessage(hooks);
					sender.sendMessage("Current Map: " + g.getCurrentmap());
					return true;
				}
			}
		}
		return false;
	}

}
