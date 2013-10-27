package com.codcraft.ccinventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.InventoryManager;
import com.codcraft.bunggie.Main;
import com.codcraft.ccommands.CCCommands;
import com.codcraft.lobby.event.PlayerToLobbyEvent;

public class Teleports implements Listener {

	  public CCCommands plugin;
	  public CCAPI api;
	  public Main bungee;
	  public Teleports(CCCommands plugin) {
			this.plugin = plugin;
	  }
	  
	  public static List<Material> itemList = new ArrayList<Material>();
	  
		@EventHandler
		public void onLobby(final PlayerToLobbyEvent e) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					e.getPlayer().getInventory().remove(Material.ENDER_PEARL);
					ItemStack in = new ItemStack(Material.ENDER_PEARL, 1);
					ItemMeta im = in.getItemMeta();
					im.setDisplayName("Teleports");
					in.setItemMeta(im);
					e.getPlayer().getInventory().addItem(in);
				}
			}, 1);

		}
		
		@EventHandler
		public void onHotBarClick(PlayerInteractEvent e) {
			GameManager gm = api.getModuleForClass(GameManager.class);
			if(gm.getGameWithPlayer(e.getPlayer()) == null){
				if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
					api.getModuleForClass(InventoryManager.class).openInv(e.getPlayer(), api.getModuleForClass(InventoryManager.class).getInv("§bTELEPORT MENU"));
				}
			}
		}
		
		public void onClick(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if(api.getModuleForClass(InventoryManager.class).invList.containsKey(e.getInventory().getName())) {
					if(e.getCurrentItem() != null){
						if (itemList.contains(e.getCurrentItem().getType())){
							executeClick(p, e.getCurrentItem().getType());
						}
					}
				}
			}
		}

		private void executeClick(Player p, Material mat) {
			
			switch(mat){
				case FLINT:
					p.closeInventory(); // We'll do a switch if there's more than one menu later on!
					break;
				case WOOL:
					prepareTeleport(p, "hub", "server"); // CHANGE SERVER PLEASE! *** * * * * *
					break;
				case BOW: 
					prepareTeleport(p, "codcraft", "server"); // CHANGE SERVER PLEASE!
					break;
				case FEATHER:
					prepareTeleport(p, "battleroom", "server"); // CHANGE SERVER PLEASE!
					break;
				case ICE:
					prepareTeleport(p, "freezetag", "server"); // CHANGE SERVER PLEASE!
					break;
				default:
					break;
			}
			
		}
		
		private void prepareTeleport(Player p, String loc, String server){
			
            File warpFile = new File("./plugins/CCCommands/Warps/" + loc + ".yml");
            
            if (!warpFile.exists()) {
                    p.sendMessage("Invalid warp - did you set one at the coords on any server?");
                    return;
            }
        
            YamlConfiguration warpLoad = YamlConfiguration.loadConfiguration(warpFile);
  
            String x = warpLoad.getString("x");
            String y = warpLoad.getString("y");
            String z = warpLoad.getString("z");
            float yaw = warpLoad.getInt("yaw");
            float pitch = warpLoad.getInt("pitch");
            Location l = new Location(Bukkit.getWorld(warpLoad.getString("world")), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), yaw, pitch);
            
            if (Bukkit.getServer().getName().equals(server)){
            	p.teleport(l);
            	return;
            }
            
            bungee.utils.teleportAPlayerToServer(p.getName(), server, warpLoad.getString("world"),  x, y, z);
		}
		
		
}
