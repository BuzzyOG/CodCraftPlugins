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
	private double x = 37.5;
	private double y = 209.5;
	private double z = 210.4;
	public SpawnCommand(CCCommands plugin, double x, double y, double z) {
		this.plugin = plugin;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("lobby")) {
			plugin.getLogger().info(sender.getName() + " dispatched lobby command!");
			if(sender instanceof Player) {
				Player p = (Player) sender;
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					g.findTeamWithPlayer(p).removePlayer(p);
				}
				p.teleport(new Location(Bukkit.getWorld("world"), x, y, z, 0, 0));
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
