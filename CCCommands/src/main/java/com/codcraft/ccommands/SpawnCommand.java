package com.codcraft.ccommands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;

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
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					g.findTeamWithPlayer(p).removePlayer(p);
				}
				p.teleport(new Location(Bukkit.getWorld("world"), -102.5, 138.5, 60.4, 90, 0));
				p.getInventory().clear();
				for(PotionEffect pe : p.getActivePotionEffects()) {
					p.removePotionEffect(pe.getType());
				}
				p.setLevel(0);
				p.setAllowFlight(false);
				p.setGameMode(GameMode.SURVIVAL);
				p.setHealth(20D);
				p.setFoodLevel(20);
				p.setExp(0);
				PlayerDoSpawnEvent event = new PlayerDoSpawnEvent(p, g);
				Bukkit.getPluginManager().callEvent(event);
				return true;
			}
		}
		
		return false;
	}

}
