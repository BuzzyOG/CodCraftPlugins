package com.CodCraft.infected.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.infected.CodCraft;

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
            if(plugin.getApi().getBuddy().isBuddy(p, k) && plugin.getApi().getBuddy().isBuddy(k, p)) {
               plugin.getApi().getTeams().setTeam(p, plugin.getApi().getTeams().getTeam(k));
            } else if(!plugin.getApi().getBuddy().isBuddy(p, k) && plugin.getApi().getBuddy().isBuddy(k, p)) {
               p.sendMessage(k.getName() + " is not on your buddy list!");
            } else if(plugin.getApi().getBuddy().isBuddy(p, k) && !plugin.getApi().getBuddy().isBuddy(k, p)) {
               p.sendMessage(k.getName() + " does not have you as a buddy!");
            } else {
               p.sendMessage("both of you are not on buddys");
            }
            plugin.getApi().getGui().updatelist();
            return true;
         }
      }
      return false;
   }

   private void displayhelp(CommandSender sender) {

   }
}
