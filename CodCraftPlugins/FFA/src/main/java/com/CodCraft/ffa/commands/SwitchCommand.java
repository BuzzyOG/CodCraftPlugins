package com.CodCraft.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Buddy;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.ffa.CodCraft;

public class SwitchCommand implements CommandExecutor {

   private CodCraft plugin;

   public SwitchCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(!(sender instanceof Player)) {
         return true;
      }
      Player p = (Player) sender;
      if(label.equalsIgnoreCase("switch")) {
         if(args.length == 0) {
            displayhelp(sender);
            return true;
         }
         if(args.length == 1) {
            Player k = Bukkit.getPlayer(args[0]);
            Buddy b = plugin.getApi().getModuleForClass(Buddy.class);
            if(b.isBuddy(p, k) && b.isBuddy(k, p)) {
            	Game<CodCraft> game = plugin.getGame().game;
            	GameManager gm = plugin.getApi().getModuleForClass(GameManager.class);
            	Team t1 = game.findTeamWithPlayer(p);
            	Team t2 = game.findTeamWithPlayer(k);
            	t1.removePlayer(p);
            	t2.addPlayer(p);
            } else if(!b.isBuddy(p, k) && b.isBuddy(k, p)) {
               p.sendMessage(k.getName() + " is not on your buddy list!");
            } else if(b.isBuddy(p, k) && !b.isBuddy(k, p)) {
               p.sendMessage(k.getName() + " does not have you as a buddy!");
            } else {
               p.sendMessage("both of you are not on buddys");
            }
            plugin.getApi().getModuleForClass(GUI.class).updatelist();
            return true;
         }
      }
      return false;
   }

   private void displayhelp(CommandSender sender) {

   }
}
