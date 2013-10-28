package com.codcraft.ccommands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class DisguiseCommand implements CommandExecutor, Listener {

	  public CCCommands plugin;
	  
	  public DisguiseCommand(CCCommands plugin) {
	  this.plugin = plugin;
	  }
	  
	  public Map <Player, String> setTags = new HashMap<>();
	  public Map <Player, String> oldTags = new HashMap<>();
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  
		  if (cmd.getName().equalsIgnoreCase("dis")){
			  
			  Player p = ((Player)sender);
			  if (args.length == 0){
				  s(p, "&cTry /dis <player>");
				  return true;
			  }
		  
			  setTags.put(p, args[0]);
			  TagAPI.refreshPlayer(p);
			  s(p, "&aYou are now disguised as " + args[0] + "&a.");
		  }
		  
		  if (cmd.getName().equalsIgnoreCase("undis")){
			  Player p = ((Player)sender);
			  if (!setTags.containsKey((p))){
				  s(p, "&cYou are not disguised.");
			  } else {
				  setTags.remove(p);
				  s(p, "&aUndisguised!");
				  TagAPI.refreshPlayer(p);
			  }
		  }
		  
	  return true;
	  }
	  
	  @EventHandler (priority = EventPriority.NORMAL)
	  public void onNameTag(PlayerReceiveNameTagEvent e) {
		  if (setTags.containsKey(e.getNamedPlayer())) {
			  oldTags.put(e.getNamedPlayer(), e.getTag());
			  e.setTag(setTags.get(e.getNamedPlayer()));
		  } else if (oldTags.containsKey(e.getNamedPlayer())){
			  e.setTag(oldTags.get(e.getNamedPlayer()));
			  oldTags.remove(e.getNamedPlayer());
		  }
	  }
	  
	  private void s (Player p, String m){
		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
	  }
}
