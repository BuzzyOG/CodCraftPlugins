package com.codcraft.bunggie;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

	private Main plugin;

	public PluginMessageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onReceivge(PluginMessageEvent e) {
		if(!e.getTag().equalsIgnoreCase("CodCraft")) {
			return;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
		try {
			String channel = in.readUTF();
			if (channel.equalsIgnoreCase("ChatMessage")) {
				String sender = in.readUTF();
				String format = in.readUTF();
				plugin.utils.sendChatMessageToAllServers(sender, format);
				return;
			}else if(channel.equalsIgnoreCase("Teleport")) {
				String player = in.readUTF();
				String server = in.readUTF();
				String world = in.readUTF();
				String x = in.readUTF();
				String y = in.readUTF();
				String z = in.readUTF();
				
				plugin.utils.teleportAPlayerToServer(player, server, world, x, y, z);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	

}
