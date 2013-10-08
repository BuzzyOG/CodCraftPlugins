package com.codcraft.ffa;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;
import com.codcraft.lobby.signs.LobbyUpdate;

public class FFALobbyUpdate extends LobbyUpdate {
  private CodCraftFFA plugin;

  public FFALobbyUpdate(CodCraftFFA plugin, Integer id) {
	  
    this.plugin = plugin;
    this.id = id.intValue();
  }

  @SuppressWarnings("deprecation")
  public boolean updateSign() {
    LobbyModule lm = (LobbyModule)this.plugin.api.getModuleForClass(LobbyModule.class);
    Lobby lobby = lm.getLobby(this.id);
    if (lobby == null) {
      return false;
    }
    GameManager gm = (GameManager)this.plugin.api.getModuleForClass(GameManager.class);
    Game<?> game = null;
    for (Game<?> g : gm.getAllGames()) {
      if (g.getName().equalsIgnoreCase(lobby.getGame())) {
        game = g;
      }
    }
    Sign s = (Sign)lobby.getSignBlock().getBlock().getState();
    if (game == null) {
      s.setLine(0, lobby.getName());
      s.setLine(1, ChatColor.DARK_RED + "Offline");
      s.setLine(2, "");
      s.setLine(3, "");
      s.update();
      lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
    } else {
      if (game.getPlugin() != this.plugin) {
        return false;
      }
      int max = 0;
      for (Team team : game.getTeams()) {
        max += team.getMaxPlayers();
      }
      int i = 0;
      for (Team team : game.getTeams()) {
        i += team.getPlayers().size();
      }
      if (i >= max) {
        s.setLine(0, lobby.getName());
        s.setLine(1, i + "/" + max);
        s.setLine(2, game.getCurrentState().getId() + ": " + game.getCurrentState().getTimeLeft());
        s.setLine(3, ChatColor.DARK_RED + "Game Full");
        s.update();
        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
      } else {
        s.setLine(0, lobby.getName());
        s.setLine(1, i + "/" + max);
        s.setLine(2, game.getCurrentState().getId() + ": " + game.getCurrentState().getTimeLeft());
        s.setLine(3, ChatColor.GREEN + "Joinable");
        s.update();
        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)5, true);
      }
    }
    return true;
  }
}