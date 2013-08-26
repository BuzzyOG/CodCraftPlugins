package com.codcraft.cchat;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Joby
 */
public class ADListener implements Listener {

    private final AntiAd plugin;

    public ADListener(AntiAd plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(plugin.getAdfinder().check(e.getPlayer(), e.getMessage(), 1,true)); 
       
        Player p = e.getPlayer();
        if (p instanceof Player) {
        	if (p.isOp()) {
        		String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[OP] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
        	} else if (p.hasPermission("CodCraft.Owner")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[O] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
        	} else if (p.hasPermission("CodCraft.SrAdmin")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[Sr_A] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
			} else if (p.hasPermission("CodCraft.Admin")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[A] "+ p.getName() +": "+ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE  + m);
			}  else if (p.hasPermission("CodCraft.Mod")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[M] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.YT")) {
				String m = e.getMessage();
				e.setFormat("["+ChatColor.RED+"Y"+ChatColor.WHITE+"T] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			} else {
				String m = e.getMessage();
				e.setFormat(ChatColor.GRAY + p.getName()+": "+ m);
				e.setMessage(ChatColor.GRAY  + m);
			}
        	e.setCancelled(true);
        	//plugin.utils.sendMessageToPlayers(e.getFormat(), e.getPlayer().getName());
        	 plugin.sendGlobalMessage(e.getPlayer().getName(), String.format(e.getFormat(), new Object[] { e.getPlayer().getName(), e.getMessage() }));
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	e.setJoinMessage(null);
    	plugin.sendGlobalMessage(" ", ChatColor.GRAY + e.getPlayer().getName() + " has joined " + plugin.getConfig().getString("Name"));
    	plugin.players.put(e.getPlayer().getName(), ChatType.ALL);
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
    	plugin.players.remove(e.getPlayer().getName());
    }
   

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignCreation(SignChangeEvent sign) {

        for (int i = 0; i < sign.getLines().length; i++) {

            if (plugin.getAdfinder().check(sign.getPlayer(), sign.getLine(i), 3, false)) {
                i = sign.getLines().length;
                sign.setCancelled(true);
                sign.getBlock().breakNaturally();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommandSent(PlayerCommandPreprocessEvent chat) {


        String CL = chat.getMessage().split("\\s+")[0];
        List<String> Commands = plugin.getConfig().getStringList("Detected-Commands");
        if (Commands.contains(CL)) {
            chat.setCancelled(plugin.getAdfinder().check(chat.getPlayer(), chat.getMessage(), 2,true));
        }
    }
}
