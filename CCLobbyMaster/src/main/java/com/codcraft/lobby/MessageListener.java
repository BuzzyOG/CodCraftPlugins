package com.codcraft.lobby;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {
	
	private CCLobby plugin;

	public MessageListener(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		String string = new String(message);
		plugin.getLogger().info("Channel: "+ channel + " player: " + player + " message: " + string);
		
	}

}
