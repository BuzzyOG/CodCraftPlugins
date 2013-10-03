package com.admixhosting.battleroom.weapons;

import org.bukkit.entity.Player;

public class ReloadTimer implements Runnable {
	
	private Lazer lazer;
	private Player p;
	private float less;

	public ReloadTimer(Lazer lazer, Player p, float less) {
		this.lazer = lazer;
		this.p = p;
		this.less = less;
	}

	@Override
	public void run() {
		if(p.getExp() > 0) {
			p.setExp(p.getExp() - less);
		} else {
			lazer.reloads.get(p.getName()).cancel();
			lazer.reloads.remove(p.getName());
		}
		
	}

}
