package com.codcraft.cchat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatListener implements PluginMessageListener {

	private AntiAd plugin;

	public ChatListener(AntiAd plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPluginMessageReceived(String pluginChannel, Player reciever, byte[] message) {
		if(!pluginChannel.equalsIgnoreCase(AntiAd.INCOMING_PLUGIN_CHANNEL)) {
			return;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		String channel = null;
		try {
			channel = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(channel.equalsIgnoreCase("ChatMessage")) {
			String sender = "";
			String chatmessage = "";
			try {
				sender = in.readUTF();
				chatmessage = in.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.utils.sendMessageToPlayers(chatmessage, sender);
		}
	}

}
