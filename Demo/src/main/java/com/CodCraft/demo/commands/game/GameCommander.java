package com.CodCraft.demo.commands.game;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.services.CommandHandler;
import com.CodCraft.demo.Demo;

public class GameCommander extends CommandHandler<Demo> {

   public GameCommander(Demo plugin) {
      super(plugin, "game");
      //Register handlers
      registerHandler(new GameListCommand(plugin));
      // register game commands.
      registerCommand("add", new GameAddCommand());
   }

   @Override
   public boolean noArgs(CommandSender sender, Command command, String label) {
      sender.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GOLD + "/ccdemo game " + ChatColor.GRAY + "=======");
      sender.sendMessage(ChatColor.YELLOW + " add " +ChatColor.LIGHT_PURPLE + "[name] " + ChatColor.WHITE + "- Add a new game.");
      sender.sendMessage(ChatColor.YELLOW + " list " + ChatColor.WHITE + "- List all games");
      return true;
   }

   @Override
   public boolean unknownCommand(CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage(ChatColor.RED + plugin.getTag() + " Command " + label + " unknown.");
      return true;
   }

}
