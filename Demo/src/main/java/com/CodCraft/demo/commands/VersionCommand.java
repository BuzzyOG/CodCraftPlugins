package com.CodCraft.demo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.CodCraft.api.services.ICommand;
import com.CodCraft.demo.Demo;

public class VersionCommand implements ICommand<Demo> {

   @Override
   public boolean execute(Demo plugin, CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GOLD + "CCAPI Demo plugin " + ChatColor.GRAY + "=======");
      sender.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.GOLD + plugin.getDescription().getVersion());
      sender.sendMessage(ChatColor.GRAY + "==============================");
      return true;
   }

}
