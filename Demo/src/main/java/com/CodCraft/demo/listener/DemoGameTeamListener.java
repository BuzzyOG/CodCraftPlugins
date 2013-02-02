package com.CodCraft.demo.listener;

import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.CCGameListener;
import com.CodCraft.demo.game.DemoGame;

public class DemoGameTeamListener extends CCGameListener {

   DemoGame game;

   public DemoGameTeamListener(DemoGame game) {
      this.game = game;
   }

   public void onTeamPlayerLost(TeamPlayerLostEvent event) {
      // Check on team limit
      if(event.getTeam().getPlayers().size() >= game.getTeamSizeLimit() && game.getTeamSizeLimit() > 0) {
         return;
      }
      // Fill available slot with queued player, if possible.
      TeamPlayer player = game.getQueuedPlayers().poll();
      if(player != null) {
         event.getTeam().addPlayer(player);
      }
   }

   public void onTeamPlayerGained(TeamPlayerGainedEvent event) {
      // TODO check on team limit

   }
}
