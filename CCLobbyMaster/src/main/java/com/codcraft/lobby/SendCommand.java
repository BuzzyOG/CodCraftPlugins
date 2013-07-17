package com.codcraft.lobby;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCommand implements CommandExecutor {
	
	private CCLobby plugin;

	public SendCommand(CCLobby plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(label.equalsIgnoreCase("send")) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				String data = args[0];
				try {
					out.writeUTF("Forward");
					out.writeUTF("pvp");
					out.writeUTF("CodCraftAPI");
					out.writeShort(data.length()); 
					out.writeUTF(data); 
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Player)sender).sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
				sender.sendMessage("sent!");

			}
		}
		
		return false;
	}

}
