package com.CodCraft.infected.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.infected.CodCraft;

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
         if(plugin.getApi().getPlayers().isSpectators(p)) {
            plugin.getApi().getPlayers().Addplaying(p);
            plugin.getApi().getPlayers().RemoveSpectators(p);
         } else {
            plugin.getApi().getPlayers().AddSpectators(p);
            plugin.getApi().getPlayers().RemovePlayer(p);
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
