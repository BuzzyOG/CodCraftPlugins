package com.codcraft.cccross;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;
import com.codcraft.cccross.test.TestGame;

public class CCCrossPlugin extends CCGamePlugin {//Testing JavaPlugin {
	
	private CCAPI api;
	
	public void onEnable() {
		Plugin plug = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(plug != null) {
			api = (CCAPI) plug;
		}
		String[] str = {"testGame"};
		makegame(str);
		api.registerModule(Cross.class, new Cross(this, api));
	}

	@Override
	public String getTag() {
		return "TestGame";
	}

	@Override
	public void makegame(String[] arg0) {
		TestGame game = new TestGame(this);
		game.setName(arg0[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
		
	}

}
