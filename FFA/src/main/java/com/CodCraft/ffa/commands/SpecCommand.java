package com.CodCraft.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.ffa.CodCraft;

public class SpecCommand implements CommandExecutor {

   private CodCraft plugin;

   public SpecCommand(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(label.equalsIgnoreCase("spec")) {
         if(!(sender instanceof Player)) {
            sender.sendMessage("Must be Player!");
            return true;
         }
         Player p = (Player) sender;
         Game<CodCraft> tp = plugin.getGame().game;
         if(tp.getSpectators().contains(p.getName())) {
        	 tp.getSpectators().remove(p.getName());
         } else {
        	 tp.getSpectators().add(p.getName());
         }
      }
      if(label.equalsIgnoreCase("tpo")) {
         Player p = (Player) sender;
         World w = Bukkit.getWorld(args[0]);
         p.teleport(w.getSpawnLocation());
      }
      return false;
   }

}
