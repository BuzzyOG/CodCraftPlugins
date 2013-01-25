package com.CodCraft.sad.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.sad.CodCraft;

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
         GameManager gm = plugin.getApi().getModuleForClass(GameManager.class);
         if(!gm.hasvoted(p)) {
            if(args[0].equalsIgnoreCase(plugin.getGame().world1)) {
            	gm.AddVote(plugin.getGame().world1);
            	gm.AddVoter(p.getName());
               sender.sendMessage("Your vote for " + plugin.getGame().world1 + "has been casted. The amount of votes now are " + gm.getVotes(plugin.getGame().world1));
               return true;
            }
            if(args[0].equalsIgnoreCase(plugin.getGame().world2)) {
            	gm.AddVote(plugin.getGame().world2);
               gm.AddVoter(p.getName());
               sender.sendMessage("Your vote for " + plugin.getGame().world2 + "has been casted. The amount of votes now are " + gm.getVotes(plugin.getGame().world2));
               return true;
            }
         }
         sender.sendMessage("Please specify a map!");
         return true;
      }
      return false;
   }
}
