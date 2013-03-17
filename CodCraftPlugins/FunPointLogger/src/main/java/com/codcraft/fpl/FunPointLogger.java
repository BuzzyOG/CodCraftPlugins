package com.codcraft.fpl;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class FunPointLogger extends JavaPlugin implements CommandExecutor {
	private CCAPI api;
	public void onEnable() {
		getCommand("points").setExecutor(this);
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().log(Level.WARNING, "ccapi not found disabling");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
	}
	public void onDisable() {
	}
	   @Override
	   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		   Player p = (Player) sender;
		   if(label.equalsIgnoreCase("points")) {
			   api.getModuleForClass(CCPlayerModule.class).updatePlayer(p);
			   p.sendMessage(ChatColor.BLUE+"Your points are "+api.getModuleForClass(CCPlayerModule.class).getPlayer(p).getPoints()+".");
			   return true;
		   }
		return false;
	   }
}