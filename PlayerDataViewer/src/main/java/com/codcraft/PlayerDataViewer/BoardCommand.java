package com.codcraft.PlayerDataViewer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codcraft.PlayerDataViewer.letters.Letter;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class BoardCommand implements CommandExecutor {
	private Main plugin;

	public BoardCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
		if(label.equalsIgnoreCase("board")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("CodCraft.Board")) {
					if(args.length == 1) {
						for(Segment seg : plugin.board.lines.get(0).segmants) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.kills) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.deaths) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.wins) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.losses) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.level) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						for(Segment seg : plugin.points) {
							Letter let = new Letter() {};
							 p.sendBlockChange(seg.loc1, Material.WOOL.getId(), let.block1);
							 p.sendBlockChange(seg.loc2, Material.WOOL.getId(), let.block2);
							 p.sendBlockChange(seg.loc3, Material.WOOL.getId(), let.block3);
							 p.sendBlockChange(seg.loc4, Material.WOOL.getId(), let.block4);
							 p.sendBlockChange(seg.loc5, Material.WOOL.getId(), let.block5);
							 p.sendBlockChange(seg.loc6, Material.WOOL.getId(), let.block6);
							 p.sendBlockChange(seg.loc7, Material.WOOL.getId(), let.block7);
							 p.sendBlockChange(seg.loc8, Material.WOOL.getId(), let.block8);
							 p.sendBlockChange(seg.loc9, Material.WOOL.getId(), let.block9);
							 p.sendBlockChange(seg.loc10, Material.WOOL.getId(), let.block10);
							 p.sendBlockChange(seg.loc11, Material.WOOL.getId(), let.block11);
							 p.sendBlockChange(seg.loc12, Material.WOOL.getId(), let.block12);
							 p.sendBlockChange(seg.loc13, Material.WOOL.getId(), let.block13);
							 p.sendBlockChange(seg.loc14, Material.WOOL.getId(), let.block14);
							 p.sendBlockChange(seg.loc15, Material.WOOL.getId(), let.block15);	
						}
						
						plugin.list.loaddata(p, args[0]);
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								if(Bukkit.getPlayer(args[0]) == null) {
									plugin.api.getModuleForClass(CCPlayerModule.class).removePlayer(args[0]);
								}
							}
						}, 20L);
						return true;
					}
				}
			}
		}
		return false;
	}

}
