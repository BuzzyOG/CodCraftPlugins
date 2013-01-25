package com.CodCraft.infected.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.infected.CodCraft;
import com.CodCraft.infected.Users;

public class CacComamnd implements CommandExecutor {
   private CodCraft plugin;

   public CacComamnd(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(label.equalsIgnoreCase("CaC")) {
         if(args.length == 0) {
            displayhelp(sender);
            return true;
         }
         if(sender instanceof Player) {
            if(args.length == 1) {
               Player p = (Player) sender;
               if(args[0].equalsIgnoreCase("join")) {
                  plugin.getApi().getCac().AddAPlayer(p);
                  plugin.getApi()
                        .getCac()
                        .LoadSigns(p, Users.PlayerGun.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p),
                              Users.Playerequipment.get(p),Users.PlayerAttactment.get(p));
                  return true;
               }
               if(args[0].equalsIgnoreCase("leave")) {
                  plugin.getApi().getCac().Leave(p);
                  if(Users.PlayerPerk1.get(p).equalsIgnoreCase("Marathon")) {
                     plugin.getApi().getPerks().addMarathon(p);
                  } else {
                     plugin.getApi().getPerks().removeMarathon(p);
                  }
                  if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
                     plugin.getApi().getPerks().AddLightuser(p);
                  } else {
                     plugin.getApi().getPerks().RemoveLightuser(p);
                  }
                  return true;
               }
            }
         }
      }
      return false;
   }

   private void displayhelp(CommandSender sender) {
      sender.sendMessage("/cac join");
      sender.sendMessage("/cac leave");
   }
}
