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
				Player p = (Player) sender;
				String clas = args[0];
				Integer atuall = isInt(clas);
				if(atuall != null) {
					CCPlayerModule ccp = plugin.api.getModuleForClass(CCPlayerModule.class);
					if(ccp.getPlayer(p).setCurrentclass(atuall)) {
						sender.sendMessage("Set your class to " + atuall);
					} else {
						sender.sendMessage("Must be a class you have!");
					}
					
					return true;
				} else {
					sender.sendMessage("Must be a number!");
					return true;
				}
			} else {
				sender.sendMessage("Ussage: /class <num>");
				return true;
			}
		}
		return false;
	}
	
	public Integer isInt(String i) {
		Integer integer = null;
		try {
			integer = Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return null;
		}
		return integer;
		
	}

}
