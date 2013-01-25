package com.CodCraft.infected.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.infected.CodCraft;

public class TeamsCommand implements CommandExecutor {
   private CodCraft plugin;

   public TeamsCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(label.equalsIgnoreCase("team")) {
         sender.sendMessage("You are on Team" + plugin.getApi().getTeams().getTeam((Player) sender));
         return true;
      }

      return false;
   }
}
