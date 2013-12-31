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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.inventory.Item;
import com.codcraft.ccommands.CCCommands;
import com.codcraft.ccommands.PluginMessageTask;

public class CCItem extends Item {
	
	private int slot;
	private List<String> lore;
	private ItemStack is;
	private CCCommands plugin;
	
	public CCItem(CCCommands plugin, Material mat, String displayName, int slot, String lore, int id, Boolean enchant) {
		this.plugin = plugin;
		List<String> newLore = new ArrayList<String>();
		newLore.add(lore);
		ItemStack is = new ItemStack(mat, 1, (byte) id);
		ItemMeta im = is.getItemMeta();
		im.setLore(newLore);
		im.setDisplayName(displayName);
			if (enchant){
				im.addEnchant(Enchantment.DURABILITY, 10, true);
			}
		is.setItemMeta(im);
		this.is = is;
		this.slot = slot;
		this.lore = newLore;
	}

	@Override
	public ItemStack getItem(Player p) {
		return is;
	}

	@Override
	public int getPosition(Player p) {
		return slot;
	}

	@Override
	public List<String> getLore(Player p) {
		return lore;
	}

	@Override
	public void onClick(Player p) {
		executeClick(p, is.getType());
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
