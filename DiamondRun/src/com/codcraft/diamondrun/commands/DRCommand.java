package com.codcraft.diamondrun.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DRCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("DR")) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("add")) {
					if(args.length > 1) {
						String name = args[1];
						File f = new File("./plugins/DiamondRun/config.yml");
						YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
						ConfigurationSection sc = config.createSection("maps."+name);
						Player p = (Player) sender;
						sc.set("x", p.getLocation().getX());
						sc.set("y", p.getLocation().getY());
						sc.set("z", p.getLocation().getZ());
						sc.set("yaw", p.getLocation().getYaw());
						sc.set("pitch", p.getLocation().getPitch());
						try {
							config.save(f);
							sender.sendMessage("Saved!");
						} catch (IOException e) {
							sender.sendMessage("Not saved!");
						}
					}
				}
			}
		}
		return false;
	}

}
