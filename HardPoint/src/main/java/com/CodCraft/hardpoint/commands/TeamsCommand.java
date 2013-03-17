package com.CodCraft.hardpoint.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.Teams;
import com.CodCraft.hardpoint.CodCraft;

public class TeamsCommand implements CommandExecutor {
   private CodCraft plugin;

   public TeamsCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(label.equalsIgnoreCase("team")) {
         sender.sendMessage("You are on Team" + plugin.getApi().getModuleForClass(Teams.class).getTeam((Player) sender));
         return true;
      }

      return false;
   }
}
