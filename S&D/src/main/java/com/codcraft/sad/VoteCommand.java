package com.codcraft.sad;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.sad.CodCraftSAD.GameState;

public class VoteCommand implements CommandExecutor {
	private CodCraftSAD plugin;
	public VoteCommand(CodCraftSAD plugin) {
		this.plugin = plugin;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("vote")) {
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer((Player) sender);
			if(!(g == null)) {
				if(g.getPlugin() == plugin) {
					SADModel model = plugin.currentmap.get(g.getId());
					if(model.state == GameState.LOBBY) {
						if(model.voters.contains(sender.getName())) {
							return true;
						}
						if(args.length == 1) {
							if(model.Map1.equalsIgnoreCase(args[0])) {
								model.map1++;
								model.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								return true;
							}
							if( model.Map2.equalsIgnoreCase(args[0])) {
								model.map2++;
								model.voters.add(sender.getName());
								sender.sendMessage("Your vote has been casted!");
								
								return true;
							}
						}
					}
				}
			}
			
		}
		return false;
	}

}
