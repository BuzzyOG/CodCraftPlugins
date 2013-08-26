package com.codcraft.ccbans;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class CCBansListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String pluginChannel, Player reciever, byte[] message) {
		if(!pluginChannel.equalsIgnoreCase(CCBans.INCOMING_PLUGIN_CHANNEL)) {
			return;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		String channel = null;
		try {
			channel = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(channel.equalsIgnoreCase("BanMessage")) {
			String banned = "";

			try {
				banned = in.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Player p = Bukkit.getPlayer(banned);
			OfflinePlayer player = Bukkit.getOfflinePlayer(banned);
			if(p != null) {
				p.kickPlayer("You are banned from CodCraft Network.");
				p.setBanned(true);
			}
			if(player != null) {
				player.setBanned(true);
			}
		}
	}

}
