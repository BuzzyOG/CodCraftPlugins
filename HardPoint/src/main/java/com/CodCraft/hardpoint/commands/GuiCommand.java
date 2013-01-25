package com.CodCraft.hardpoint.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.GUI;
import com.CodCraft.hardpoint.CodCraft;

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
    	  GUI gui = plugin.getApi().getModuleForClass(GUI.class);
         if(gui.isModeUser(p)) {
        	 gui.removeModeuser(p);
         } else {
        	 gui.AddModeUser(p);
         }
         return true;
      }
      return false;
   }
}
