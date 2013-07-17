package com.codcraft.videoplayer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public class VideoPlayer extends JavaPlugin {
	
	public Map<String, String> videos = new HashMap<String, String>();
	
	public void onEnable() {
		videos.put("tralier", "/home/videos/traler.mp4");
		getServer().getPluginManager().registerEvents(new VideoPlayerListener(this), this);
	}

}
