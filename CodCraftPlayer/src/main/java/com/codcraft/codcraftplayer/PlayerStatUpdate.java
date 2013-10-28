package com.codcraft.codcraftplayer;

import org.bukkit.event.HandlerList;

import com.CodCraft.api.services.CCEvent;

public class PlayerStatUpdate extends CCEvent {
	
	private String stat;
	private CCPlayer player;
	private int oldscore;
	private int newscore;

	public PlayerStatUpdate(CCPlayer player, String stat, int oldscore, int newscore) {
		this.player = player;
		this.stat = stat;
		this.oldscore = oldscore;
		this.newscore = newscore;
	}
	

	public int getNewscore() {
		return newscore;
	}


	public void setNewscore(int newscore) {
		this.newscore = newscore;
	}


	public int getOldscore() {
		return oldscore;
	}


	public void setOldscore(int oldscore) {
		this.oldscore = oldscore;
	}


	public CCPlayer getPlayer() {
		return player;
	}


	public void setPlayer(CCPlayer player) {
		this.player = player;
	}


	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}


	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}
	   
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
