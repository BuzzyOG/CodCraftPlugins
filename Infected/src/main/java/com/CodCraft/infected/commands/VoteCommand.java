package com.CodCraft.infected.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.infected.CodCraft;
import com.CodCraft.infected.GM.Game;

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
            plugin.getGame();
			if(args[0].equalsIgnoreCase(Game.world1)) {
            	plugin.getGame();
				gm.AddVote(Game.world1);
            	gm.AddVoter(p.getName());
               plugin.getGame();
			plugin.getGame();
			sender.sendMessage("Your vote for " + Game.world1 + "has been casted. The amount of votes now are " + gm.getVotes(Game.world1));
               return true;
            }
            plugin.getGame();
			if(args[0].equalsIgnoreCase(Game.world2)) {
            	plugin.getGame();
				gm.AddVote(Game.world2);
               gm.AddVoter(p.getName());
               plugin.getGame();
			plugin.getGame();
			sender.sendMessage("Your vote for " + Game.world2 + "has been casted. The amount of votes now are " + gm.getVotes(Game.world2));
               return true;
            }
         }
         sender.sendMessage("Please specify a map!");
         return true;
      }
      return false;
   }
}
