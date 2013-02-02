package com.CodCraft.demo;

import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCGamePlugin;
import com.CodCraft.demo.commands.Commander;

/**
 * Main plugin class.
 */
public class Demo extends CCGamePlugin {
   /**
    * API hook.
    */
   private CCAPI api;
   /**
    * Plugin tag.
    */
   private static final String TAG = "[DEMO]";

   @Override
   public void onDisable() {
      // TODO Auto-generated method stub
      super.onDisable();
   }

   @Override
   public void onEnable() {
      // Set the API
      final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
      if(api != null || !(api instanceof CCAPI)) {
         this.api = (CCAPI) api;
      } else {
         // Disable if we cannot get the api.
         getLogger().warning("Could not find API. Disabling...");
         getServer().getPluginManager().disablePlugin(this);
         return;
      }

      // Set the commands.
      getCommand("ccdemo").setExecutor(new Commander(this));
   }

   @Override
   public String getTag() {
      return TAG;
   }

   /**
    * Get the API hook.
    * 
    * @return CCAPI.
    */
   public CCAPI getAPI() {
      return api;
   }

}
