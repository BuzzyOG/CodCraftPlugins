package com.codcraft.testgame;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class TestMain extends CCGamePlugin {
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TestListener(), this);
	}

	@Override
	public String getTag() {
		return "TestGame";
	}

	@Override
	public void makeGame(String[] args) {
		TestGame game = new TestGame(this);
		game.setName(args[0]);
		getApi().getModuleForClass(GameManager.class).registerGame(game);
		
	}

}
