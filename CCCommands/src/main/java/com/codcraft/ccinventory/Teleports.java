package com.codcraft.ccinventory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
import com.codcraft.ccommands.CCCommands;
import com.codcraft.ccommands.PlayerDoSpawnEvent;
import com.codcraft.ccommands.PluginMessageTask;

public class Teleports implements Listener {

	  public CCCommands plugin;
	  public Teleports(CCCommands plugin) {
			this.plugin = plugin;
	  }
	  
	  public static List<Material> itemList = new ArrayList<Material>();
	  
		@EventHandler
		public void onLobby(final PlayerDoSpawnEvent e) {
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
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			if(gm.getGameWithPlayer(e.getPlayer()) == null){
				if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL) {
					e.getPlayer().openInventory(plugin.api.getModuleForClass(InventoryManager.class).invList.get("§bTELEPORT MENU"));
				}
			}
		}
		@EventHandler
		public void onClick(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if(plugin.api.getModuleForClass(InventoryManager.class).invList.containsKey(e.getInventory().getName())) {
					if(e.getCurrentItem() != null){
						if (itemList.contains(e.getCurrentItem().getType())){
							executeClick(p, e.getCurrentItem().getType());
							e.setCancelled(true);
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
					prepareTeleport(p, "hub", "lobby"); // CHANGE SERVER PLEASE! *** * * * * *
					break;
				case BOW: 
					prepareTeleport(p, "codcraft", "CodCraft"); // CHANGE SERVER PLEASE!
					break;
				case FEATHER:
					prepareTeleport(p, "battleroom", "BattleRoom"); // CHANGE SERVER PLEASE!
					break;
				case ICE:
					prepareTeleport(p, "freezetag", "Freezetag"); // CHANGE SERVER PLEASE!
					break;
				default:
					break;
			}
			
		}
		
		private void prepareTeleport(Player p, String loc, String server){
			
            File warpFile = new File("./plugins/CCCommands/Warps/" + loc + ".yml");
            
            /*if (!warpFile.exists()) {
                    p.sendMessage("Invalid warp - did you set one at the coords on any server?");
                    return;
            }*/
            
            ByteArrayOutputStream b = new ByteArrayOutputStream();
		    DataOutputStream out = new DataOutputStream(b);
		    try {
		    	out.writeUTF("Connect");
		        out.writeUTF(server);
		    } catch (IOException localIOException) {
		    	plugin.getLogger().info("Error: " + server);
		    }
		    p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            
            
            YamlConfiguration warpLoad = YamlConfiguration.loadConfiguration(warpFile);
  
            String x = warpLoad.getString("x");
            String y = warpLoad.getString("y");
            String z = warpLoad.getString("z");
            float yaw = warpLoad.getInt("yaw");
            float pitch = warpLoad.getInt("pitch");
            Location l = new Location(Bukkit.getWorld(warpLoad.getString("world")), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), yaw, pitch);
            
            if (Bukkit.getServer().getServerName().equals(server)){
            	p.teleport(l);
            	return;
            }
            
            
            
            // FOR LATERz telerpotyAPlayer(p.getName(), server, warpLoad.getString("world"),  x, y, z);
		}
		public void telerpotyAPlayer(String player, String server, String world, String x, String y, String z) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("Teleport");
			    out.writeUTF(player);
			    out.writeUTF(server);
			    out.writeUTF(world);
			    out.writeUTF(x);
			    out.writeUTF(y);
			    out.writeUTF(z);
			} catch (IOException e) {
				e.printStackTrace();
			}
			new PluginMessageTask(plugin, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(plugin);
		}
		
		
}
