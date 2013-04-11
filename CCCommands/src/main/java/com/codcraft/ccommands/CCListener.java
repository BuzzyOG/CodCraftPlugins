package com.codcraft.ccommands;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.CodCraft.api.modules.GameManager;



public class CCListener implements Listener {
	private CCCommands plugin;
	public CCListener(CCCommands plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onrepsawn(PlayerRespawnEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta book1 = (BookMeta) book.getItemMeta();
		book1.addPage("Welcome to CodCraft please follow these"+ChatColor.BOLD+" rules."+ChatColor.BLACK+"" +
				"             Respect all Staff marked in "+ChatColor.DARK_RED+" red!"+ ChatColor.BLACK + "           No Hacking!" +
						"               No Advertising." +
						"           Use Common Sense!");
		
		book.setItemMeta(book1);
		e.getPlayer().getInventory().addItem(book);
	}
	
	@EventHandler
	public void commandhappen(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().equalsIgnoreCase("/stop")) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
		      DataOutputStream out = new DataOutputStream(b);
		      try {
		        out.writeUTF("Connect");
		        out.writeUTF("lobby");
		      } catch (IOException localIOException) {
		      }
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
			}
			try {
				Thread.sleep(1000);
				System.out.println("worked");
			} catch (InterruptedException e1) {
				System.out.println("not-worked");
			}
		}
		
	}
	
	
}
