package com.codcraft.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
/**
 * Class for the Plugin. 
 */
public class CCShop extends JavaPlugin {
	
	/**
	 * List of all ShopItems.
	 */
	public List<CCShopItem> items = new ArrayList<>();
	/**
	 * The CodCraftAPI instance.
	 */
	public CCAPI api;
	/**
	 * Permission instance from vault. 
	 */
	private Permission perms;
	/**
	 * Enable function for the plugin.
	 * 	- Sets the API instance
	 * 	- Registers the Listener
	 */
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
		setSigns();
		setupPermissions();
	}
	/**
	 * Enum for CCShops for wether
	 * to run a event or to give a permision.
	 */
	public enum Type {
		
		PERMISSION,
		COMMAND
		
	}
	/**
	 * Adds the SignLoc default from the config. 
	 */
        private void setSigns() {
		File f = new File("./plugins/CCShop/shoplocs.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<SignLoc> signs = new ArrayList<>();
		for(String shop : config.getConfigurationSection("Shops").getKeys(false)) {
			SignLoc temp = new SignLoc();
			Location buysign = new Location(Bukkit.getWorld("world"), config.getInt("Shops."+shop+".x"), config.getInt("Shops."+shop+".y"), config.getInt("Shops."+shop+".z"));
			int dirrection = config.getInt("Shops."+shop+".d");
			temp.d = dirrection;
			if(buysign.getBlock().getState() instanceof Sign) {
				temp.buy = buysign;
			}
			Location info2 = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() - 1, buysign.getZ());
			if(info2.getBlock().getState() instanceof Sign) {
				temp.info2 = info2;
			}
			if(dirrection == 3) {
				Location info1 = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() - 1, buysign.getZ() - 1);
				if(info1.getBlock().getState() instanceof Sign) {
					temp.info1 = info1;
				}
				Location info3 = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() - 1, buysign.getZ() + 1);
				if(info3.getBlock().getState() instanceof Sign) {
					temp.info3 = info3;
				}
			} else if (dirrection == 0) {
				Location info1 = new Location(buysign.getWorld(), buysign.getX() + 1, buysign.getY() - 1, buysign.getZ());
				if(info1.getBlock().getState() instanceof Sign) {
					temp.info1 = info1;
				}
				Location info3 = new Location(buysign.getWorld(), buysign.getX() - 1, buysign.getY() - 1, buysign.getZ());
				if(info3.getBlock().getState() instanceof Sign) {
					temp.info3 = info3;
				}
			} else if (dirrection == 1) {
				Location info1 = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() - 1, buysign.getZ() + 1);
				if(info1.getBlock().getState() instanceof Sign) {
					temp.info1 = info1;
				}
				Location info3 = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() - 1, buysign.getZ() - 1);
				if(info3.getBlock().getState() instanceof Sign) {
					temp.info3 = info3;
				}
			} else {
				Location info1 = new Location(buysign.getWorld(), buysign.getX() - 1, buysign.getY() - 1, buysign.getZ());
				if(info1.getBlock().getState() instanceof Sign) {
					temp.info1 = info1;
				}
				Location info3 = new Location(buysign.getWorld(), buysign.getX() + 1, buysign.getY() - 1, buysign.getZ());
				if(info3.getBlock().getState() instanceof Sign) {
					temp.info3 = info3;
				}
			}

			Location Itemframe = new Location(buysign.getWorld(), buysign.getX(), buysign.getY() + 1, buysign.getZ());
			temp.itemfram = Itemframe;
			signs.add(temp);
		}
		for(int i = 0; i < signs.size(); i++) {
			SignLoc sl = signs.get(i);
			CCShopItem si;
			if(items.size() - 1 >= i) {
				si = items.get(i);
			} else {
				si = null;
			}
			
			if(si != null) {
				getLogger().info(""+sl.buy);
				getLogger().info(""+sl.info1);
				getLogger().info(""+sl.info2);
				getLogger().info(""+sl.info3);
				
				Sign buy = (Sign) sl.buy.getBlock().getState();
				
				Sign info1 = (Sign) sl.info1.getBlock().getState();
				Sign info2 = (Sign) sl.info2.getBlock().getState();
				Sign info3 = (Sign) sl.info3.getBlock().getState();
				buy.setLine(0, "[CCShop]");
				buy.setLine(1, si.getName());
				buy.setLine(2, ""+si.getPrice());
				buy.update();
				info1.setLine(0, si.Sign1info1);
				info1.setLine(1, si.Sign1info2);
				info1.setLine(2, si.Sign1info3);
				info1.setLine(3, si.Sign1info4);
				info1.update();
				info2.setLine(0, si.Sign2info1);
				info2.setLine(1, si.Sign2info2);
				info2.setLine(2, si.Sign2info3);
				info2.setLine(3, si.Sign2info4);
				info2.update();
				info3.setLine(0, si.Sign3info1);
				info3.setLine(1, si.Sign3info2);
				info3.setLine(2, si.Sign3info3);
				info3.setLine(3, si.Sign3info4);
				info3.update();
				System.out.println(""+sl.itemfram.getBlock().getType());
				//ItemFrame iframe = (ItemFrame) buy.getWorld().spawn(sl.itemfram, ItemFrame.class);
				//iframe.setItem(new ItemStack(si.getMaterial()));
			}
		}
		
	}
	/**
	 * Registers the instance for permission from vault. 
	 */
	private boolean setupPermissions() {
        	RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        	perms = rsp.getProvider();
        	return perms != null;
    	}
    	/**
    	 * Loads the config for the CCShopItems. 
    	 */
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
			item.Sign1info1 = config.getString("Shops."+shop+".s1i1");
			item.Sign1info2 = config.getString("Shops."+shop+".s1i2");
			item.Sign1info3 = config.getString("Shops."+shop+".s1i3");
			item.Sign1info4 = config.getString("Shops."+shop+".s1i4");
			item.Sign2info1 = config.getString("Shops."+shop+".s2i1");
			item.Sign2info2 = config.getString("Shops."+shop+".s2i2");
			item.Sign2info3 = config.getString("Shops."+shop+".s2i3");
			item.Sign2info4 = config.getString("Shops."+shop+".s2i4");
			item.Sign3info1 = config.getString("Shops."+shop+".s3i1");
			item.Sign3info2 = config.getString("Shops."+shop+".s3i2");
			item.Sign3info3 = config.getString("Shops."+shop+".s3i3");
			item.Sign3info4 = config.getString("Shops."+shop+".s3i4");
			item.setMaterial(config.getString("Shops."+shop+".Item"));
			if(item.getPermisison().equalsIgnoreCase("false")) {
				item.setType1(Type.COMMAND);
			}
			items.add(item);
		}
		return true;
	}
}
