package com.codcraft.lobby.signs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.lobby.CCLobby;
import com.codcraft.lobby.Lobby;

public class LobbySign extends LobbyUpdate {
  private CCLobby plugin;

  public LobbySign(CCLobby plugin, int id) {
    this.plugin = plugin;
    this.id = id;
  }

  public boolean updateSign() {
    Lobby lobby = (Lobby)this.plugin.configmap.get(Integer.valueOf(this.id));
    if (lobby == null) {
      Bukkit.broadcastMessage("Lobby null");
      return false;
    }
    if (!(lobby.getSignBlock().getBlock().getState() instanceof Sign)) {
      lobby.getSignBlock().getBlock().setType(Material.WALL_SIGN);
    }
    Sign s = (Sign)lobby.getSignBlock().getBlock().getState();

    GameManager gm = (GameManager)this.plugin.CCAPI.getModuleForClass(GameManager.class);
    Game<?> game = null;

    for (Game<?> g : gm.getAllGames()) {
      if (g.getName().equalsIgnoreCase(lobby.getGame())) {
        game = g;
      }
    }

    if (game == null) {
      s.setLine(0, lobby.getName());
      s.setLine(1, ChatColor.DARK_RED + "Offline");
      s.setLine(2, "");
      s.setLine(3, "");
      s.update();
      lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
    } else {
      s.setLine(0, lobby.getName());
      int i = 0;

      for (Team team : game.getTeams()) {
        i += team.getPlayers().size();
      }

      int max = 0;
      for (Team team : game.getTeams()) {
        max += team.getMaxPlayers();
      }
      s.setLine(1, i + "/" + max);
      s.setLine(3, game.getPlugin().getTag());
      if (game.validate()) {
        String time;
        if (game.getCurrentState().getTimeLeft() == -1)
          time = game.getCurrentState().getId();
        else {
          time = game.getCurrentState().getId() + " " + game.getCurrentState().getTimeLeft();
        }

        s.setLine(2, time);
      }

      s.update();
      if (game.getCurrentState().getTimeLeft() < 5) {
        if (game.getCurrentState().getTimeLeft() % 2 == 0) {
          lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)4, true);
        }
        else if (i >= max)
          lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
        else {
          lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)5, true);
        }

      }
      else if (i >= max)
        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)14, true);
      else {
        lobby.getLampblock().getBlock().setTypeIdAndData(35, (byte)5, true);
      }
    }

    return true;
  }
}