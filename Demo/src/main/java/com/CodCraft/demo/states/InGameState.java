package com.CodCraft.demo.states;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.demo.Demo;

public class InGameState implements GameState<Demo> {

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
      // TODO Auto-generated method stub
      
   }

   @Override
   public Game<Demo> getGame() {
      // TODO Auto-generated method stub
      return null;
   }

}
