package com.codcraft.ccgit;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class GitPlugin extends JavaPlugin {
	
	private List<String> allow = new ArrayList<>();
	
	
	public void onEnable() {
		getCommand("git").setExecutor(new GitCommand(this));
		allow = (List<String>) getConfig().getList("allowedgits");
	}

}
