package com.codcraft.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCGamePlugin;

public class CCShop extends CCGamePlugin {
	public List<CCShopItem> items = new ArrayList<>();
	public CCAPI api;

	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().log(Level.WARNING, "ccapi not found disabling");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		getServer().getPluginManager().registerEvents(new CCShopListener(this), this);
		if(loadConfig()) {
		
		}
	}
	
	private boolean loadConfig() {
		File f = new File("./plugins/CCShop/shops.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		for(String shop : config.getConfigurationSection("Shops").getKeys(false)) {
			CCShopItem item = new CCShopItem();
			item.setName(shop);
			item.setPermisison(config.getString("Shops."+shop+".Permission"));
			item.setPrice(config.getInt("Shops."+shop+".Price"));
			item.setBoughtmessage(config.getString("Shops."+shop+".BoughtMessage"));
			item.setDenyboughtmessage(config.getString("Shops."+shop+".DenyMessage"));
		}
		return false;
	}

	@Override
	public String getTag() {
		return "[CCShop]";
	}

	@Override
	public void makegame(String name) {
		// TODO Auto-generated method stub
		
	}

}
