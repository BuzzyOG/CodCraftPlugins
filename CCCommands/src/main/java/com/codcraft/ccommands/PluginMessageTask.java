package com.codcraft.ccommands;

import java.io.ByteArrayOutputStream;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginMessageTask extends BukkitRunnable {
	
	private CCCommands plugin;
	private ByteArrayOutputStream bytes;
	private Player player;

	public PluginMessageTask(CCCommands plugin, Player player, ByteArrayOutputStream bytes) {
	    this.plugin = plugin;
	    this.bytes = bytes;
	    this.player = player;
	  }

	@Override
	public void run() {
		this.player.sendPluginMessage(plugin, CCCommands.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray());
	}

}
