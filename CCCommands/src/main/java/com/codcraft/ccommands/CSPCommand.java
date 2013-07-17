package com.codcraft.ccommands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CSPCommand implements CommandExecutor {
	

	public CSPCommand(CCCommands plugin) {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("CSP")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("CodCraft.CSP")) {
					String world = args[1];
					String cs = args[2];
					String game = args[0];
					if(game.equalsIgnoreCase("ffa")) {
						 File spawns = new File("./plugins/FFA/config.yml");
					     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
					     ConfigurationSection configs = config.createSection("maps."+world+"."+cs);
					     configs.set("x", p.getLocation().getX());
					     configs.set("y", p.getLocation().getY());
					     configs.set("z", p.getLocation().getZ());
					     configs.set("yaw", p.getLocation().getYaw());
					     configs.set("pitch", p.getLocation().getPitch());
					     try {
							config.save(spawns);
							sender.sendMessage("Spawn Point Saved");
							return true;
						} catch (IOException e) {
							sender.sendMessage("Spawn Point Error");
							e.printStackTrace();
							return true;
						}
					} else if (game.equalsIgnoreCase("tdm")) {
						if(args.length == 4) {
							String team = args[3];
							 File spawns = new File("./plugins/TDM/config.yml");
						     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
						     ConfigurationSection configs = config.createSection(team+".maps."+world+"."+cs);
						     configs.set("x", p.getLocation().getX());
						     configs.set("y", p.getLocation().getY());
						     configs.set("z", p.getLocation().getZ());
						     configs.set("yaw", p.getLocation().getYaw());
						     configs.set("pitch", p.getLocation().getPitch());
						     try {
								config.save(spawns);
								sender.sendMessage("Spawn Point Saved");
								return true;
							} catch (IOException e) {
								sender.sendMessage("Spawn Point Error");
								e.printStackTrace();
								return true;
							}
						} else {
							 File spawns = new File("./plugins/TDM/config.yml");
						     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
						     ConfigurationSection configs = config.createSection("maps."+world+"."+cs);
						     configs.set("x", p.getLocation().getX());
						     configs.set("y", p.getLocation().getY());
						     configs.set("z", p.getLocation().getZ());
						     configs.set("yaw", p.getLocation().getYaw());
						     configs.set("pitch", p.getLocation().getPitch());
						     try {
								config.save(spawns);
								sender.sendMessage("Spawn Point Saved");
								return true;
							} catch (IOException e) {
								sender.sendMessage("Spawn Point Error");
								e.printStackTrace();
								return true;
							}
						}
					}
				     
				     
				} else {
					p.sendMessage("You Do not have permission!");
					return true;
				}
			} else {
				sender.sendMessage("Must be player!");
				return true;
			}
		}

		return false;
	}

}
