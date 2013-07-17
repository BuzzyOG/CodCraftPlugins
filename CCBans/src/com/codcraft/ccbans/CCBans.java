package com.codcraft.ccbans;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;




public class CCBans extends JavaPlugin implements Listener {
	
	public List<String> playerstoban = new ArrayList<>();
    public static final String INCOMING_PLUGIN_CHANNEL = "CodCraftBans";
	public static final String OUTGOING_PLUGIN_CHANNEL = "CodCraft";
	
	public void onEnable() {
		getCommand("ban").setExecutor(new BanCommand(this));
	    //getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    //getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener(this));
	    getServer().getPluginManager().registerEvents(this, this);
	    registerChannels();
	}
	
    private void registerChannels() {
        Bukkit.getMessenger().registerIncomingPluginChannel(this, INCOMING_PLUGIN_CHANNEL, new CCBansListener(this));
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, OUTGOING_PLUGIN_CHANNEL);
	}

	public void globalBan(String name) {
		Player[] online = Bukkit.getOnlinePlayers();
		if(online.length == 0) {
			playerstoban.add(name);
		} else {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("BanMessage");
			    out.writeUTF(name);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			new PluginMessageTask(this, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(this);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for(String s : playerstoban) {
			globalBan(s);
		}
		playerstoban.clear();
	}

}
