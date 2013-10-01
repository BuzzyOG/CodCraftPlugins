package com.codcraft.ccbans;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

	private CCBans plugin;

	public BanCommand(CCBans plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("ban")) {
			if(!sender.hasPermission("codcraft.ban")) {
				return false;
			}
			if(args.length == 1) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
				player.setBanned(true);
				
				Player p = Bukkit.getPlayer(args[0]);
				if(p != null) {
					p.kickPlayer("You have been banned! Have a good day!");
				}
				plugin.globalBan(args[0]);
			} else {
				sender.sendMessage("Ussage: /ban <name>");
			}

		}
		return false;
	}

}
