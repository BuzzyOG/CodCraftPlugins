package com.codcraft.bunggie;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

	public HubCommand(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			((ProxiedPlayer) sender).connect(BungeeCord.getInstance().getServerInfo("lobby"));
		}


	}

}
