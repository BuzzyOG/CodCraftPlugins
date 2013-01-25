package com.CodCraft.infected.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import com.CodCraft.infected.CodCraft;

public class AdminCommands implements CommandExecutor {
   private CodCraft plugin;

   public AdminCommands(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      // Admin Commands
      if(label.equalsIgnoreCase("a") || label.equalsIgnoreCase("admin")) {
         // Set Commands
         if(args[0].equalsIgnoreCase("set")) {
            // Set Kills Command
            if(args[1].equalsIgnoreCase("kills")) {
               Player p = (Player) sender;
               if(p.hasPermission("CodCraft.setKills") || p.isOp()) {
                  if(args.length == 3) {
                     int amount = Integer.parseInt(args[2]);
                     plugin.getApi().getPlayers().setKills(p, amount);
                     plugin.getApi().getKillStreaks().setKills(p, amount);
                     plugin.getApi().getGui().updatelist();
                     plugin.getApi().getGameManager().setTeamscore(plugin.getApi().getTeams().getTeam(p), amount);
                     plugin.getApi().getGameManager().DetectWin(75);
                     return true;
                  } else if(args.length == 4) {
                     int amount = Integer.parseInt(args[2]);
                     Player k = Bukkit.getPlayer(args[3]);
                     plugin.getApi().getPlayers().setKills(k, amount);
                     plugin.getApi().getGui().updatelist();
                     plugin.getApi().getGameManager().setTeamscore(plugin.getApi().getTeams().getTeam(k), amount);
                     plugin.getApi().getGameManager().DetectWin(75);
                     return true;
                  }

               }
            }
            // Set Team Command
            if(args[1].equalsIgnoreCase("team")) {
               Player p = (Player) sender;
               if(p.hasPermission("CodCraft.setTeam") || p.isOp()) {
                  if(args.length == 3) {
                     int team = Integer.parseInt(args[2]);
                     plugin.getApi().getTeams().setTeam(p, team);
                     plugin.getApi().getGui().updatelist();
                     TagAPI.refreshPlayer(p);
                     return true;
                  } else if(args.length == 4) {
                     int team = Integer.parseInt(args[2]);
                     Player k = Bukkit.getPlayer(args[3]);
                     plugin.getApi().getTeams().setTeam(k, team);
                     plugin.getApi().getGui().updatelist();
                     TagAPI.refreshPlayer(k);
                     return true;
                  }
               }
            }

         }
      }

      return false;
   }
}
