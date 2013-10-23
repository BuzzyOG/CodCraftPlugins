package com.codcraft.ffa;


import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;

public class GameListener extends CCGameListener {

	private CodCraftFFA plugin;
	
	public GameListener(CodCraftFFA plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		
		GameManager gm = (GameManager)this.plugin.api.getModuleForClass(GameManager.class);
	    Game<?> game = gm.getGameWithPlayer(e.getPlayer());
	    if (game == null) {
	      return;
	    }
	    if (game.getPlugin() != this.plugin) {
	      return;
	    }
	    FFAGame g = (FFAGame)game;
	    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
	      Block clickedblock = e.getClickedBlock();
	      if ((clickedblock != null) && 
	        ((clickedblock.getState() instanceof Sign))) {
	        Sign sign = (Sign)clickedblock.getState();
	        String MAP = null;
	        if ((clickedblock.getLocation().getX() == 58.0D) && (clickedblock.getLocation().getY() == 41.0D) && (clickedblock.getLocation().getZ() == 195.0D)) {
	          if (sign.getLine(1).equalsIgnoreCase(g.Map1.getName()))
	            MAP = g.Map1.getName();
	          else if (sign.getLine(1).equalsIgnoreCase(g.Map2.getName()))
	            MAP = g.Map2.getName();
	        }
	        else if ((clickedblock.getLocation().getX() == 58.0D) && (clickedblock.getLocation().getY() == 42.0D) && (clickedblock.getLocation().getZ() == 195.0D)) {
	          if (sign.getLine(1).equalsIgnoreCase(g.Map1.getName()))
	            MAP = g.Map1.getName();
	          else if (sign.getLine(1).equalsIgnoreCase(g.Map2.getName())) {
	            MAP = g.Map2.getName();
	          }
	        }
	        if (MAP != null)
	          e.getPlayer().performCommand("vote " + MAP);
	      }
	    }
	  }
}
