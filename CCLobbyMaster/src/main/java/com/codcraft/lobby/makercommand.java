package com.codcraft.lobby;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codcraft.lobby.LobbyMaker.Step;

public class makercommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(label.equalsIgnoreCase("maker")) {
				if(LobbyMaker.getStep() == Step.NAME) {
					LobbyMaker.name = args[0];
					LobbyMaker.setStep((Player) sender, Step.SERVER);
					return true;
				} else if(LobbyMaker.getStep() == Step.SERVER) {
					LobbyMaker.server = args[0];
					LobbyMaker.setStep((Player) sender, Step.Block1);
					return true;
				}
			}
		}
		return false;
	}

}
