package com.codcraft.bunggie;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

public class Utilities {
	
	private Main plugin;

	public Utilities(Main plugin) {
		this.plugin = plugin;
	}
	
	
	public void teleportAPlayerToServer(String player, String server, String world,  String x, String y, String z) {
		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 DataOutputStream out = new DataOutputStream(b);
		 try {
			 out.writeUTF("Teleport");
			 out.writeUTF(player);
			 out.writeUTF(world);
			 out.writeUTF(x);
			 out.writeUTF(y);
			 out.writeUTF(z);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 BungeeCord.getInstance().getScheduler().runAsync(plugin, new SendPluginMessage("CodCraftTeleport", getServer(server), b));
	}
	
	public void sendChatMessageToAllServers(String sender, String format) {
		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 DataOutputStream out = new DataOutputStream(b);
		 try {
			 out.writeUTF("ChatMessage");
			 out.writeUTF(sender);
			 out.writeUTF(format);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    for (ServerInfo serverinfo : BungeeCord.getInstance().getServers().values()) {
	    	BungeeCord.getInstance().getScheduler().runAsync(plugin, new SendPluginMessage("CodCraftChat", serverinfo, b));
	    }
	}
	
	public ServerInfo getServer(String name) {
		return BungeeCord.getInstance().getServerInfo(name);
	}

}
