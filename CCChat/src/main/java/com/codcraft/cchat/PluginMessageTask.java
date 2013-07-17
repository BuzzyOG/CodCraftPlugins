package com.codcraft.cchat;

import java.io.ByteArrayOutputStream;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginMessageTask extends BukkitRunnable {
	
	  private AntiAd plugin;
	private ByteArrayOutputStream bytes;
	private Player player;

	public PluginMessageTask(AntiAd plugin, Player player, ByteArrayOutputStream bytes) {
	    this.plugin = plugin;
	    this.bytes = bytes;
	    this.player = player;
	  }

	@Override
	public void run() {
		this.player.sendPluginMessage(plugin, AntiAd.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray());
	}

}
