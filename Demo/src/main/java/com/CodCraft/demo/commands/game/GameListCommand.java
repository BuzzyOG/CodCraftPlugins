package com.CodCraft.demo.commands.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CommandHandler;
import com.CodCraft.api.services.ICommand;
import com.CodCraft.demo.Demo;
import com.CodCraft.demo.game.DemoGame;

/**
 * Game list command handler.
 */
public class GameListCommand extends CommandHandler<Demo> {

   /**
    * Current page a player is on.
    */
   private final Map<String, Integer> currentPage = new HashMap<String, Integer>();
   /**
    * Page limit.
    */
   private static final int PAGE_LIMIT = 10;
   /**
    * Message to show if there are no games. Args: plugin tag.
    */
   private static final String NO_GAMES = "%s " + ChatColor.GRAY + " There are no games registered.";
   /**
    * Available games header. Args: current page, total pages.
    */
   private static final String AVAILABLE = ChatColor.GREEN + "==== " + ChatColor.GRAY + "Available Games " + ChatColor.AQUA + "%d" + ChatColor.GRAY
         + ":" + ChatColor.AQUA + "%d " + ChatColor.GREEN + "====";
   /**
    * Game message. Args: Status color, Game name, Number of players, Max number
    * of players, Number of queued players.
    */
   private static final String GAME_STATUS = "%s %s " + ChatColor.WHITE + "- In:" + ChatColor.GREEN + "%d " + ChatColor.WHITE + "Max:"
         + ChatColor.RED + "%d " + ChatColor.WHITE + "Wait:" + ChatColor.GRAY + "%d";

   /**
    * Constructor.
    * 
    * @param plugin
    *           - Demo plugin.
    */
   public GameListCommand(Demo plugin) {
      super(plugin, "list");
      registerCommand("next", new NextCommand());
      registerCommand("prev", new PrevCommand());
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(args.length != 0 && !(args[0].equalsIgnoreCase("next") || args[0].equalsIgnoreCase("prev"))) {
         try {
            int page = Integer.parseInt(args[0]);
            if(!(currentPage.containsKey(sender.getName()))) {
               // Insert
               currentPage.put(sender.getName(), 0);
            } else {
               // Increment
               currentPage.put(sender.getName(), page);
            }
            noArgs(sender, command, label);
         } catch(NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + plugin.getTag() + " '" + ChatColor.GOLD + args[0] + ChatColor.RED + "' is not a valid page number.");
            return true;
         }
      }
      return super.onCommand(sender, command, label, args);
   }

   @Override
   public boolean noArgs(CommandSender sender, Command command, String label) {
      GameManager manager = plugin.getAPI().getModuleForClass(GameManager.class);
      List<Game<?>> games = manager.getGamesForPlugin(plugin);
      paginate(games, sender);
      return true;
   }

   @Override
   public boolean unknownCommand(CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage(ChatColor.RED + plugin.getTag() + " Unknown list command '" + ChatColor.GOLD + label + ChatColor.RED + "'");
      return true;
   }

   /**
    * Page the list.
    * 
    * @param list
    *           - List of games.
    * @param sender
    *           - CommandSender to message.
    */
   private void paginate(List<Game<?>> list, CommandSender sender) {
      // Check if list is empty.
      if(list.isEmpty()) {
         sender.sendMessage(String.format(NO_GAMES, plugin.getTag()));
      }
      // Grab the number of pages.
      int num = list.size() / PAGE_LIMIT;
      double rem = (double) list.size() % (double) PAGE_LIMIT;
      if(rem != 0) {
         num++;
      }
      if(!(currentPage.containsKey(sender.getName()))) {
         // Insert
         currentPage.put(sender.getName(), 0);
      }
      int current = currentPage.get(sender.getName());
      // validate current page number.
      if(current < 0) {
         // Tried to use a negative page, adjust.
         current = 0;
         currentPage.put(sender.getName(), current);
      } else if(current > num) {
         // Tried to go to a page that didn't exist.
         current = num;
         currentPage.put(sender.getName(), current);
      }
      // Iterate through list
      sender.sendMessage(String.format(AVAILABLE, current + 1, num));
      for(int i = current * PAGE_LIMIT; i < (current * PAGE_LIMIT + PAGE_LIMIT); i++) {
         Game<?> raw = null;
         try {
            raw = list.get(i);
         } catch(IndexOutOfBoundsException e) {
            // Hit end of list so end.
            break;
         }
         DemoGame game;
         try {
            game = DemoGame.class.cast(raw);
            if(game == null) {
               continue;
            }
         } catch(ClassCastException e) {
            continue;
         }
         int in = 0;
         for(Team team : game.getTeams()) {
            in += team.getPlayers().size();
         }
         sender.sendMessage(String.format(GAME_STATUS, getGameStatusColor(game), game.getName(), in, game.getTeamLimit() * game.getTeamSizeLimit(),
               game.getQueuedPlayers().size()));
      }
   }

   /**
    * Get the chat color for the game's current status.
    * 
    * @param game
    *           - Game to check.
    * @return Color to represent the status.
    */
   private ChatColor getGameStatusColor(DemoGame game) {
      switch(game.validateState(game.getCurrentState())) {
      case IN: {
         return ChatColor.GREEN;
      }
      case POST: {
         return ChatColor.GRAY;
      }
      case PRE: {
         return ChatColor.GOLD;
      }
      case STOP: {
         return ChatColor.RED;
      }
      default: {
         break;
      }
      }
      return ChatColor.BLACK;
   }

   /**
    * Increases the page by one.
    */
   private class NextCommand implements ICommand<Demo> {

      @Override
      public boolean execute(Demo plugin, CommandSender sender, Command command, String label, String[] args) {
         if(!(currentPage.containsKey(sender.getName()))) {
            // Insert
            currentPage.put(sender.getName(), 0);
         } else {
            // Increment
            currentPage.put(sender.getName(), currentPage.get(sender.getName()) + 1);
         }
         return noArgs(sender, command, label);
      }
   }

   /**
    * Decreases the page by one.
    */
   private class PrevCommand implements ICommand<Demo> {
      @Override
      public boolean execute(Demo plugin, CommandSender sender, Command command, String label, String[] args) {
         if(!(currentPage.containsKey(sender.getName()))) {
            // Insert
            currentPage.put(sender.getName(), 0);
         } else {
            // Increment
            currentPage.put(sender.getName(), currentPage.get(sender.getName()) - 1);
         }
         return noArgs(sender, command, label);
      }
   }
}
