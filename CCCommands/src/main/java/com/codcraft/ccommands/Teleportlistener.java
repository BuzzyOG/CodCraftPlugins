package com.codcraft.ccommands;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class Teleportlistener implements PluginMessageListener {

	private CCCommands plugin;

	public Teleportlistener(CCCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPluginMessageReceived(String pluginChannel, Player reciever, byte[] message) {
		if(!pluginChannel.equalsIgnoreCase(CCCommands.INCOMING_PLUGIN_CHANNEL)) {
			return;
		}
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		String channel = null;
		try {
			channel = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(channel.equalsIgnoreCase("Teleport")) {
			String sender = "";
			String world = "";
			String x = "";
			String y = "";
			String z = "";
			try {
				sender = in.readUTF();
				world = in.readUTF();
				x = in.readUTF();
				y = in.readUTF();
				z = in.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int X = Integer.parseInt(x);
			int Y = Integer.parseInt(y);
			int Z = Integer.parseInt(z);
			
			Player p = Bukkit.getPlayer(sender);
			if(p != null) {
				p.teleport(new Location(Bukkit.getWorld(world), X, Y, Z));
			}
		}
		
	}

}
