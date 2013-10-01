package com.codcraft.ccommands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Advertisment implements Runnable {
	
	private Map<String, ArrayList<String>> ads = new HashMap<String, ArrayList<String>>();
	
	public Advertisment() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/CCCommands/ads.yml"));
		for(String name : config.getConfigurationSection("Ads").getKeys(false)) {
			List<String> lines = new ArrayList<>();
			for(String line : config.getConfigurationSection("Ads."+name).getKeys(false)) {
				String s = config.getString("Ads."+name+"."+line);
				lines.add(ChatColor.BLUE+"[Cylum Ad] "+ChatColor.WHITE+ ChatColor.translateAlternateColorCodes('&', s));
			}
			ads.put(name, (ArrayList<String>) lines);
		}
	}

	@Override
	public void run() {
		Random       random    = new Random();
		List<String> keys      = new ArrayList<String>(ads.keySet());
		String       randomKey = keys.get( random.nextInt(keys.size()) );
		ArrayList<String>       value     = ads.get(randomKey);
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(String s : value) {
				p.sendMessage(s);
			}
		}
	}
}
