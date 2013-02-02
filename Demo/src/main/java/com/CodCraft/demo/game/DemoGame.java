package com.CodCraft.demo.game;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.Location;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.GameState;
import com.CodCraft.demo.Demo;
import com.CodCraft.demo.listener.DemoGamePlayerListener;
import com.CodCraft.demo.states.InGameState;
import com.CodCraft.demo.states.PostGameState;
import com.CodCraft.demo.states.PreGameState;
import com.CodCraft.demo.states.StopGameState;
import com.CodCraft.demo.states.UnknownGameState;

/**
 * Demonstrates a working "game" with shifting states.
 * 
 * @author Tokume
 * 
 */
public class DemoGame extends Game<Demo> {

   protected final Set<Location> availableSpawns = new HashSet<Location>();

   // TODO need a queue of players that want to join this specific game if the
   // game is already underway. Queued players will be added to teams on the
   // post game.

   protected final Queue<TeamPlayer> queuedPlayers = new ConcurrentLinkedQueue<TeamPlayer>();

   protected int teamLimit = 2;

   // TODO team size limit.
   protected int teamSizeLimit = 5;

   /**
    * Constructor.
    * 
    * @param instance
    *           - Demo plugin instance.
    */
   public DemoGame(Demo instance) {
      super(instance);
   }

   @Override
   public void initialize() {
      // Set the current state
      StopGameState stopState = new StopGameState();
      stopState.setGame(this);
      currentState = stopState;

      // Generate other game states.
      PreGameState preState = new PreGameState();
      preState.setGame(this);
      InGameState inState = new InGameState();
      inState.setGame(this);
      PostGameState postState = new PostGameState();
      postState.setGame(this);
      UnknownGameState unknownState = new UnknownGameState();
      unknownState.setGame(this);

      // Register game states.
      knownStates.add(stopState);
      knownStates.add(preState);
      knownStates.add(inState);
      knownStates.add(postState);
      knownStates.add(unknownState);

      // Register listeners
      addListener(new DemoGamePlayerListener(this));
   }

   /**
    * Get the queue list of players to join the game.
    * 
    * @return Queue of TeamPlayers that have yet to join the game.
    */
   public Queue<TeamPlayer> getQueuedPlayers() {
      return queuedPlayers;
   }

   /**
    * Get the limit of teams for the game.
    * 
    * @return Team limit.
    */
   public int getTeamLimit() {
      return teamLimit;
   }

   /**
    * Set the limit of teams for the game.
    * 
    * @param teamLimit
    *           - Limit to use.
    */
   public void setTeamLimit(int teamLimit) {
      this.teamLimit = teamLimit;
   }

   /**
    * Get the size limit for teams in the game.
    * 
    * @return Team size limit.
    */
   public int getTeamSizeLimit() {
      return teamSizeLimit;
   }

   /**
    * Set the size limit for teams in the game.
    * 
    * @param teamSizeLimit
    *           - Limit to use.
    */
   public void setTeamSizeLimit(int teamSizeLimit) {
      this.teamSizeLimit = teamSizeLimit;
   }

   @Override
   public void preStateSwitch(GameState<Demo> state) {
      switch(validateState(state)) {
      case PRE: {
         // TODO reset stuff
         break;
      }
      case IN: {
         break;
      }
      case POST: {
         break;
      }
      case STOP: {
         break;
      }
      default: {
         break;
      }
      }
   }

   @Override
   public void postStateSwitch(GameState<Demo> state) {
      switch(validateState(state)) {
      case PRE: {
         break;
      }
      case IN: {
         break;
      }
      case POST: {
         break;
      }
      case STOP: {
         break;
      }
      default: {
         break;
      }
      }
   }

   public ValidState validateState(GameState<Demo> state) {
      if(state instanceof PreGameState) {
         return ValidState.PRE;
      } else if(state instanceof InGameState) {
         return ValidState.IN;
      } else if(state instanceof PostGameState) {
         return ValidState.POST;
      } else if(state instanceof StopGameState) {
         return ValidState.STOP;
      }
      return ValidState.UNKNOWN;
   }

   public enum ValidState {
      PRE,
      IN,
      POST,
      STOP,
      UNKNOWN;
   }

}
