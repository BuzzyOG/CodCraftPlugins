package com.codcraft.cac;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CaCcommands implements CommandExecutor {

	private Cac plugin;
	public CaCcommands(Cac plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("cac")) {
			if(args.length == 0) {
				runHelp(sender);
			} else if(args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "join":
					plugin.api.getModuleForClass(CaCModule.class).addCaCUser((Player) sender);
					return true;
				case "leave":
					plugin.api.getModuleForClass(CaCModule.class).Leave((Player) sender);
					return true;
				default:
					runHelp(sender);
					return true;
				}

			} else if (args.length == 2) {
				if(args[0].equalsIgnoreCase("forcejoin")) {
					plugin.api.getModuleForClass(CaCModule.class).forceAddCaCUser((Player) sender, Integer.parseInt(args[1]));
				}
			}
		}
		return false;
	}

	private void runHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.BLUE+"/cac join: to join the cac.");
		sender.sendMessage(ChatColor.BLUE+"/cac leave: to leave the cac.");
	}

}
