package com.codcraft.ccommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codcraft.codcraftplayer.CCPlayerModule;

public class ClassCommand implements CommandExecutor {
	private CCCommands plugin;

	public ClassCommand(CCCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("class")) {
			if(args.length == 1) {
				CCPlayerModule ccp = plugin.api.getModuleForClass(CCPlayerModule.class);
				ccp.getPlayer((Player) (sender)).setCurrentclass(Integer.parseInt(args[0]));
			}
		}
		return false;
	}

}
