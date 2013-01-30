package com.CodCraft.ffa.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;


import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.KillStreaks;
import com.CodCraft.api.modules.TeamPlayer;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.ffa.CodCraft;

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
                     Game<CodCraft> game = plugin.getGame().game;
                     KillStreaks killStreaks = plugin.api.getModuleForClass(KillStreaks.class);
                     GUI GUI = plugin.api.getModuleForClass(GUI.class);
                     GameManager manger = plugin.api.getModuleForClass(GameManager.class);
                     Team t = game.findTeamWithPlayer(p);
                     com.CodCraft.api.model.TeamPlayer teamplayer = t.findPlayer(p);
                     teamplayer.setKills(amount);
                     killStreaks.setKills(p, amount);
                     GUI.updatelist();
                     plugin.getGame().game.getTeams().get(0);
                     t.setTeamScore(amount);
                     // GET max score
                     //TODO Check if the score is max
                     return true;
                  } else if(args.length == 4) {
                      TeamPlayer player = plugin.api.getModuleForClass(TeamPlayer.class);
                      KillStreaks killStreaks = plugin.api.getModuleForClass(KillStreaks.class);
                      GUI GUI = plugin.api.getModuleForClass(GUI.class);
                      GameManager manger = plugin.api.getModuleForClass(GameManager.class);
                      Teams t = plugin.api.getModuleForClass(Teams.class);
                     int amount = Integer.parseInt(args[2]);
                     Player k = Bukkit.getPlayer(args[3]);
                     player.setKills(k, amount);
                     killStreaks.setKills(p, amount);
                     GUI.updatelist();
                     manger.setTeamscore(t.getTeam(k), amount);
                     manger.DetectWin(75);
                     return true;
                  }

               }
            }
            // Set Team Command
            if(args[1].equalsIgnoreCase("team")) {
               Player p = (Player) sender;
               if(p.hasPermission("CodCraft.setTeam") || p.isOp()) {
            	   GUI GUI = plugin.getApi().getModuleForClass(GUI.class);
            	   Teams t = plugin.getApi().getModuleForClass(Teams.class);
                  if(args.length == 3) {
                     int team = Integer.parseInt(args[2]);
                     t.setTeam(p, team);
                     GUI.updatelist();
                     TagAPI.refreshPlayer(p);
                     return true;
                  } else if(args.length == 4) {
                     int team = Integer.parseInt(args[2]);
                     Player k = Bukkit.getPlayer(args[3]);
                     t.setTeam(k, team);
                     GUI.updatelist();
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
