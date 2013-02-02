package com.CodCraft.demo.commands.game;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.ICommand;
import com.CodCraft.demo.Demo;
import com.CodCraft.demo.game.DemoGame;

public class GameAddCommand implements ICommand<Demo> {
   /**
    * Message to show if missing a parameter. Args: plugin tag, name of
    * parameter missing.
    */
   private static final String MISSING = ChatColor.RED + "%s Missing parameter '" + ChatColor.GOLD + "%s" + ChatColor.RED + "'";
   /**
    * Message to show if game with given name already exists. Args: plugin tag,
    * game name.
    */
   private static final String EXISTS = ChatColor.YELLOW + "%s Game '" + ChatColor.GRAY + "%s" + ChatColor.YELLOW + "' already exists.";
   /**
    * Message to show if the game gets created. Args: plugin tag, game name.
    */
   private static final String SUCCESS = ChatColor.GREEN + "%s Game '" + ChatColor.GOLD + "%s" + ChatColor.GREEN + "' created.";

   @Override
   public boolean execute(Demo plugin, CommandSender sender, Command command, String label, String[] args) {
      // Get the name for this new game.
      String name = "";
      try {
         name = args[0];
      } catch(ArrayIndexOutOfBoundsException e) {
         sender.sendMessage(String.format(MISSING, plugin.getTag(), "name"));
         return true;
      }
      // Check that the name is not already taken.
      GameManager manager = plugin.getAPI().getModuleForClass(GameManager.class);
      List<Game<?>> existing = manager.getGamesForPlugin(plugin);
      for(Game<?> exist : existing) {
         if(exist.getName().equalsIgnoreCase("name")) {
            sender.sendMessage(String.format(EXISTS, plugin.getTag(), name));
            return true;
         }
      }
      //Create a new game instance
      DemoGame game = new DemoGame(plugin);
      game.setName(name);
      //Register game
      manager.registerGame(game);
      sender.sendMessage(String.format(SUCCESS, plugin.getTag(), name));
      return true;
   }
}
