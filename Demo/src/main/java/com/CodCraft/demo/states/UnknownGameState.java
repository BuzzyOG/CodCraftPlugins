package com.CodCraft.demo.states;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.services.GameState;
import com.CodCraft.demo.Demo;

public class UnknownGameState implements GameState<Demo> {

   Game<Demo> game;

   @Override
   public void setStateDuration(int duration) {
      // TODO Auto-generated method stub

   }

   @Override
   public int getStateDuration() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public Runnable getTask() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void setGame(Game<Demo> game) {
      this.game = game;
   }

   @Override
   public Game<Demo> getGame() {
      return game;
   }

}
