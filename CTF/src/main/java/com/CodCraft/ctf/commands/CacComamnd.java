package com.CodCraft.ctf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.Cac;
import com.CodCraft.api.modules.Perks;
import com.CodCraft.ctf.CodCraft;
import com.CodCraft.ctf.Users;

public class CacComamnd implements CommandExecutor {
   private CodCraft plugin;

   public CacComamnd(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	   Cac cac = plugin.getApi().getModuleForClass(Cac.class);
      if(label.equalsIgnoreCase("CaC")) {
         if(args.length == 0) {
            displayhelp(sender);
            return true;
         }
         if(sender instanceof Player) {
            if(args.length == 1) {
               Player p = (Player) sender;
               if(args[0].equalsIgnoreCase("join")) {
                  cac.AddAPlayer(p);
                  cac.LoadSigns(p, Users.PlayerGun.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p),
                              Users.Playerequipment.get(p),Users.PlayerAttactment.get(p));
                  return true;
               }
               if(args[0].equalsIgnoreCase("leave")) {
            	   cac.Leave(p);
            	   Perks perk = plugin.getApi().getModuleForClass(Perks.class);
                  if(Users.PlayerPerk1.get(p).equalsIgnoreCase("Marathon")) {
                	  perk.addMarathon(p);
                  } else {
                	  perk.removeMarathon(p);
                  }
                  if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
                     perk.AddLightuser(p);
                  } else {
                	  perk.RemoveLightuser(p);
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
