package com.codcraft.ccommands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;

public class JoinCommand implements CommandExecutor {
	private CCCommands plugin;

	public JoinCommand(CCCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("join")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					g.findTeamWithPlayer(p).removePlayer(p);
				}
				String name = args[0];
				List<Game<?>> games = new ArrayList<>();
				for(Game<?> game : gm.getAllGames()) {
					if(game.getName().equalsIgnoreCase(name)) {
						games.add(game);
					}
				}
				if(games.size() == 1) {
					RequestJoinGameEvent event = new RequestJoinGameEvent(new TeamPlayer(p.getName()), g, null);
					Bukkit.getPluginManager().callEvent(event);
					if(!event.isCancelled()) {
						if(event.getTeam() != null) {
							event.getTeam().addPlayer(event.getPlayer());
						}
					}
				} else {
					p.sendMessage("Did you mean");
					for(Game<?> g1 : games) {
						p.sendMessage(g1.getName()+":" + g1.getPlugin());
					}
				}
			}
		}
		return false;
	}

}
