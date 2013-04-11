package com.codcraft.ccommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.lobby.LobbyModule;

public class SpawnCommand implements CommandExecutor {
	private CCCommands plugin;
	
	public SpawnCommand(CCCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				GUI gui = plugin.api.getModuleForClass(GUI.class);
				ArrayList<String> s = new ArrayList<>();
				for(Player p1 : Bukkit.getOnlinePlayers()) {
					s.add(p1.getName());
				}
				gui.updateplayerlist(p, s);
				if(gm.getGameWithPlayer(p) == null) {
					p.teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
					plugin.api.getModuleForClass(LobbyModule.class).UpdateSigns();
					p.getInventory().clear();
					for(PotionEffect pe : p.getActivePotionEffects()) {
						p.removePotionEffect(pe.getType());
					}
					return true;
				} else {
					gm.getGameWithPlayer(p).findTeamWithPlayer(p).removePlayer(p);
					p.teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
					plugin.api.getModuleForClass(LobbyModule.class).UpdateSigns();
					p.getInventory().clear();
					for(PotionEffect pe : p.getActivePotionEffects()) {
						p.removePotionEffect(pe.getType());
					}
					
					
					return true;
				}
			}
		}
		
		return false;
	}

}
