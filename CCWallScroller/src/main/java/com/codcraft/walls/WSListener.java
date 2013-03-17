package com.codcraft.walls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class WSListener implements Listener {

	private WS plugin;
	private Player p;
	private Location block1;
	private Location block2;
	
	
	public WSListener(WS plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand().getType() == Material.WOOD_SWORD) {
			p = e.getPlayer();
			if(block1 == null) {
				block1 = e.getClickedBlock().getLocation();
				e.getPlayer().sendMessage("First BLocked picked at "+ e.getClickedBlock().getLocation());
				e.setCancelled(true);
			} else if(block1 != null && block2 == null) {
				block2 = e.getClickedBlock().getLocation();
				e.getPlayer().sendMessage("SecondBlock BLocked picked at "+ e.getClickedBlock().getLocation());
				e.getPlayer().sendMessage("Savinf to config");
				List<Location> locs = new ArrayList<>();
				double y = 0;
				if(block1.getY() > block2.getY()) {
					y = block1.getY();
					while(y >= block2.getY()) {
						locs.add(new Location(block1.getWorld(), block1.getX(), y, block1.getZ()));
						y--;
					}
				}else {
					y = block2.getY();
					while(y >= block1.getY()) {
						locs.add(new Location(block1.getWorld(), block1.getX(), y, block1.getZ()));
						y--;
					}
				}

				block1 = null;
				block2 = null;
				Location loc1 = locs.get(0);
				Location loc2 = locs.get(1);
				Location loc3 = locs.get(2);
				Location loc4 = locs.get(3);
				Location loc5 = locs.get(4);
				Location loc6 = locs.get(5);
				Location loc7 = locs.get(6);
				Location loc8 = locs.get(7);
				Location loc9 = locs.get(8);
				Location loc10 = locs.get(9);
				Location loc11 = locs.get(10);
				Location loc12 = locs.get(11);
				new YamlConfiguration();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/WS/config.yml"));
				String stripid = ""+plugin.Strips.size()+1;
				ConfigurationSection Strip = config.createSection("Strips."+stripid);
				ConfigurationSection striploc1 = Strip.createSection("Location1");
				striploc1.set("x", loc1.getX());
				striploc1.set("y", loc1.getY());
				striploc1.set("z", loc1.getZ());
				Strip.set("Location1", striploc1);
				ConfigurationSection striploc2 = Strip.createSection("Location2");
				striploc2.set("x", loc2.getX());
				striploc2.set("y", loc2.getY());
				striploc2.set("z", loc2.getZ());
				Strip.set("Location2", striploc2);
				ConfigurationSection striploc3 = Strip.createSection("Location3");
				striploc3.set("x", loc3.getX());
				striploc3.set("y", loc3.getY());
				striploc3.set("z", loc3.getZ());
				Strip.set("Location3", striploc3);
				ConfigurationSection striploc4 = Strip.createSection("Location4");
				striploc4.set("x", loc4.getX());
				striploc4.set("y", loc4.getY());
				striploc4.set("z", loc4.getZ());
				Strip.set("Location4", striploc4);
				ConfigurationSection striploc5 = Strip.createSection("Location5");
				striploc5.set("x", loc5.getX());
				striploc5.set("y", loc5.getY());
				striploc5.set("z", loc5.getZ());
				Strip.set("Location5", striploc5);
				ConfigurationSection striploc6 = Strip.createSection("Location6");
				striploc6.set("x", loc6.getX());
				striploc6.set("y", loc6.getY());
				striploc6.set("z", loc6.getZ());
				Strip.set("Location6", striploc6);
				ConfigurationSection striploc7 = Strip.createSection("Location7");
				striploc7.set("x", loc7.getX());
				striploc7.set("y", loc7.getY());
				striploc7.set("z", loc7.getZ());
				Strip.set("Location7", striploc7);
				ConfigurationSection striploc8 = Strip.createSection("Location8");
				striploc8.set("x", loc8.getX());
				striploc8.set("y", loc8.getY());
				striploc8.set("z", loc8.getZ());
				Strip.set("Location8", striploc8);
				ConfigurationSection striploc9 = Strip.createSection("Location9");
				striploc9.set("x", loc9.getX());
				striploc9.set("y", loc9.getY());
				striploc9.set("z", loc9.getZ());
				Strip.set("Location9", striploc9);
				ConfigurationSection striploc10 = Strip.createSection("Location10");
				striploc10.set("x", loc10.getX());
				striploc10.set("y", loc10.getY());
				striploc10.set("z", loc10.getZ());
				Strip.set("Location10", striploc10);
				ConfigurationSection striploc11 = Strip.createSection("Location11");
				striploc11.set("x", loc11.getX());
				striploc11.set("y", loc11.getY());
				striploc11.set("z", loc11.getZ());
				Strip.set("Location11", striploc11);
				ConfigurationSection striploc12 = Strip.createSection("Location12");
				striploc12.set("x", loc12.getX());
				striploc12.set("y", loc12.getY());
				striploc12.set("z", loc12.getZ());
				Strip.set("Location12", striploc12);
				
				config.set("Strips."+stripid, Strip);
				try {
					config.save(new File("./plugins/WS/config.yml"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(loc1);
				System.out.println(loc2);
				System.out.println(loc3);
				System.out.println(loc4);
				System.out.println(loc5);
				System.out.println(loc6);
				System.out.println(loc7);
				System.out.println(loc8);
				System.out.println(loc9);
				System.out.println(loc10);
				System.out.println(loc11);
				System.out.println(loc12);
				plugin.Strips.clear();
				plugin.LoadConfig();
				e.setCancelled(true);
			}
		}
		
	}

}
