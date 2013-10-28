package com.codcraft.ccgit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GitCommand implements CommandExecutor {

	private GitPlugin plugin;

	public GitCommand(GitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(sender.hasPermission("Git.git")) {
				if(label.equalsIgnoreCase("git")) {
					if("push".equalsIgnoreCase(args[0])) {
						sender.sendMessage("Starting push command!");
					}
				}
			}
		}
		return false;
	}

}
