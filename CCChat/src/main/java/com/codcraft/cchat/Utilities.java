package com.codcraft.cchat;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.Buddy;

public class Utilities {
	
	private AntiAd plugin;

	public Utilities(AntiAd plugin) {
		this.plugin = plugin;
	}
	
	public void sendMessageToPlayers(String message, String Player) {
		for(Entry<String, ChatType> en : plugin.players.entrySet()) {
			Player p = Bukkit.getPlayer(en.getKey());
			if(p != null) {
				if(en.getValue() == ChatType.NONE) {
					return;
				} else if (en.getValue() == ChatType.SERVER) {
					Player player = Bukkit.getPlayer(Player);
					if(player != null) {
						p.sendMessage(message);
					}
				} else if (en.getValue() == ChatType.ALL) {
					p.sendMessage(message);
				} else if(en.getValue() == ChatType.BUDDIES) {
					Buddy bud = plugin.api.getModuleForClass(Buddy.class);
					if(bud.isBuddy(p.getName(), Player)) {
						p.sendMessage(message);
					}
				}
			}
		}

	}

}
