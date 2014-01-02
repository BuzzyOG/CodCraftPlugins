package com.codcraft.testgame;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.hook.Hook;

public class TestHook extends Hook {

	private boolean bol = false;

	public TestHook(Game<?> g) {
		super("TestHook", g);
	}

	@Override
	public void initialize() {
		bol = true;
		
	}

	@Override
	public void deinitialize() {
		bol = false;
		
	}

	@Override
	public void update(String[] args) {
	}
	
	@Override
	public boolean isEnabled() {
		return bol;
	}

}
