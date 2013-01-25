package com.CodCraft.infected.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.infected.CodCraft;

public class GuiCommand implements CommandExecutor {

   private CodCraft plugin;

   public GuiCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(!(sender instanceof Player)) {
         return true;
      }
      Player p = (Player) sender;
      if(label.equalsIgnoreCase("gui")) {
         if(plugin.getApi().getGui().isModeUser(p)) {
            plugin.getApi().getGui().removeModeuser(p);
         } else {
            plugin.getApi().getGui().AddModeUser(p);
         }
         return true;
      }
      return false;
   }
}
