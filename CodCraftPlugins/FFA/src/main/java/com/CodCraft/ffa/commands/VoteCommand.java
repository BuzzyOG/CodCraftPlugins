package com.CodCraft.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.services.GameState;
import com.CodCraft.ffa.CodCraft;

public class VoteCommand implements CommandExecutor {

   private CodCraft plugin;

   public VoteCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(!(sender instanceof Player)) {
         sender.sendMessage("Must be a player!");
         return true;
      }
      if(label.equalsIgnoreCase("vote")) {
         Player p = (Player) sender;

         if(!plugin.getGame().hasvoted(p)) {
            if(args[0].equalsIgnoreCase(plugin.getGame().world1)) {
            	plugin.getGame().setWorld1votes(plugin.getGame().getWorld1votes() + 1);
            	plugin.getGame().addvoter(p);
               sender.sendMessage("Your vote for " + plugin.getGame().world1 + "has been casted. ");
               return true;
            }
            if(args[0].equalsIgnoreCase(plugin.getGame().world2)) {
            	plugin.getGame().setWorld2votes(plugin.getGame().getWorld2votes() + 1);
            	plugin.getGame().addvoter(p);
               sender.sendMessage("Your vote for " + plugin.getGame().world2 + "has been casted. ");
               return true;
            }
         }
         sender.sendMessage("Please specify a map!");
         return true;
      }
      return false;
   }
}
