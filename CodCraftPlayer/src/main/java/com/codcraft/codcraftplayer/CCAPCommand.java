package com.codcraft.codcraftplayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CCAPCommand implements CommandExecutor {
	
	private CCPlayerMain plugin;

	public CCAPCommand(CCPlayerMain plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
		if(label.equalsIgnoreCase("CCAP")) {
			if(sender.hasPermission("CCAP.ADDCredits")) {
				if(args[0].equalsIgnoreCase("add")) {
					CCPlayer player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(args[1]));
					if(player == null) {
						plugin.api.getModuleForClass(CCPlayerModule.class).loadPlayer(args[1]);
						player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(Bukkit.getPlayer(args[1]));
					}
					player.setCredits(player.getCredits() + 1000);
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						
						@Override
						public void run() {
							plugin.getCCDatabase.savep(Bukkit.getPlayer(args[1]));
							
						}
					}, 30);
					
				}
			}
		}
		return false;
	}

}
