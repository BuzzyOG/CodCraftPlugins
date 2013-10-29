package com.codcraft.cchat;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.Buddy;
import com.CodCraft.api.modules.GameManager;

public class Utilities {
	
	private AntiAd plugin;

	public Utilities(AntiAd plugin) {
		this.plugin = plugin;
	}
	
	public void sendMessageToPlayers(String message, String Player) {
		for(Entry<String, ChatType> en : plugin.players.entrySet()) {
			Player p = Bukkit.getPlayer(en.getKey());
			if(p != null) {
				continueChat(message, p, Player, en);
			}
		}

	}
	
	public void continueChat(String message, Player p, String Player, Entry<String, ChatType> en){
		if(en.getValue() == ChatType.NONE) {
			return;
		} else if (en.getValue() == ChatType.SERVER) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g == null) {
				Player player = Bukkit.getPlayer(Player);
				if(player != null) {
					if(gm.getGameWithPlayer(player) == null) {
						if (message.toLowerCase().contains(p.getName().toLowerCase()) && !p.getName().equals(Bukkit.getPlayer(Player).getName())){
							message = message.replace(p.getName(), ChatColor.GREEN + p.getName() + ChatColor.RESET);
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 3F, 0.5F);
							p.sendMessage(message);
						} else {
							p.sendMessage(message);
						}
					}

				}
			} else {
				Player player = Bukkit.getPlayer(Player);
				if(player != null) {
					Game<?> g1 = gm.getGameWithPlayer(player);
					if(g1 != null) {
						if(g1.getId().equalsIgnoreCase(g.getId())) {
							if (message.toLowerCase().contains(p.getName().toLowerCase()) && !p.getName().equals(Bukkit.getPlayer(Player).getName())){
								message = message.replace(p.getName(), ChatColor.GREEN + p.getName() + ChatColor.RESET);
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 3F, 0.5F);
								p.sendMessage(message);
							} else {
								p.sendMessage(message);
							}
						}
					}

				}

			}

		} else if(en.getValue() == ChatType.BUDDIES) {
			Buddy bud = plugin.api.getModuleForClass(Buddy.class);
			if(bud.isBuddy(p.getName(), Player)) {
				if (message.toLowerCase().contains(p.getName().toLowerCase()) && !p.getName().equals(Bukkit.getPlayer(Player).getName())){
					message = message.replace(p.getName(), ChatColor.GREEN + p.getName() + ChatColor.RESET);
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 3F, 0.5F);
					p.sendMessage(message);
				} else {
					p.sendMessage(message);
				}

			}
		}
	}

}
