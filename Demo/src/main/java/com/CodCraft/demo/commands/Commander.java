package com.CodCraft.demo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.services.CommandHandler;
import com.CodCraft.api.services.ICommand;
import com.CodCraft.demo.Demo;
import com.CodCraft.demo.commands.game.GameCommander;

/**
 * Command handler for the root command.
 */
public class Commander extends CommandHandler<Demo> {

   public Commander(Demo plugin) {
      super(plugin, "ccdemo");
      // register handlers
      registerHandler(new GameCommander(plugin));
      // TODO register commands
      HelpCommand help = new HelpCommand();
      registerCommand("help", help);
      registerCommand("?", help);
      registerCommand("version", new VersionCommand());
   }

   @Override
   public boolean noArgs(CommandSender sender, Command command, String label) {
      // show help
      sender.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GOLD + " CCAPI Demo " + ChatColor.GRAY + "=======");
      sender.sendMessage(ChatColor.YELLOW + "/ccdemo");
      sender.sendMessage(ChatColor.YELLOW + "    game " + ChatColor.WHITE + " - Game commands.");
      sender.sendMessage(ChatColor.YELLOW + "    help " + ChatColor.WHITE + " - Help menu.");
      sender.sendMessage(ChatColor.YELLOW + "    version " + ChatColor.WHITE + " - Plugin version.");
      return true;
   }

   @Override
   public boolean unknownCommand(CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage(ChatColor.RED + plugin.getTag() + " Unknown command '" + ChatColor.GOLD + label + ChatColor.RED + "'");
      return true;
   }

   private class HelpCommand implements ICommand<Demo> {

      @Override
      public boolean execute(Demo plugin, CommandSender sender, Command command, String label, String[] args) {
         return noArgs(sender, command, label);
      }

   }

}
