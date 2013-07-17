package com.codcraft.ccmapmaker;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MMAdd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("mm")) {
			if(args.length == 4) {
				Player p = (Player) sender;
				String method = args[0];
				String world = args[1];
				String name = args[2];
				String mat = args[3];
				Material mats = convert(mat);
				if(method.equalsIgnoreCase("add")) {
					File f = new File("./plugins/MapMaker/config.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					ConfigurationSection section = config.getConfigurationSection("maps."+world);
					ConfigurationSection newsection = section.createSection(name);
					newsection.set("x", p.getLocation().getX());
					newsection.set("y", p.getLocation().getY());
					newsection.set("z", p.getLocation().getZ());
					newsection.set("mat", mats.name());
					sender.sendMessage("Saved " + name + " to " + mats.name() + " at your current location.");
					try {
						config.save(f);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private Material convert(String mat) {
		if(isInt(mat)){
			Integer i = Integer.parseInt(mat);
			return Material.getMaterial(i);
		} else {
			return Material.valueOf(mat);
		}
	}

	public boolean isInt(String mat) {
		try {
			Integer.parseInt(mat);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
