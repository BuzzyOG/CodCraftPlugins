package com.codcraft.cchat;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
				if (message.toLowerCase().contains(p.getName().toLowerCase()) && !p.getName().equals(Bukkit.getPlayer(Player).getName())){
					message.replace(p.getName(), "§b" + p.getName() + "§r");
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 3F, 0.5F);
					continueChat(message, p, Player, en);
				} else {
					continueChat(message, p, Player, en);
				}
					
			}
		}

	}
	
	public void continueChat(String message, Player p, String Player, Entry<String, ChatType> en){
		if(en.getValue() == ChatType.NONE) {
			return;
		} else if (en.getValue() == ChatType.SERVER) {
			Player player = Bukkit.getPlayer(Player);
			if(player != null) {
				p.sendMessage(message);
			}
		} else if(en.getValue() == ChatType.BUDDIES) {
			Buddy bud = plugin.api.getModuleForClass(Buddy.class);
			if(bud.isBuddy(p.getName(), Player)) {
				p.sendMessage(message);
			}
		}
	}

}
