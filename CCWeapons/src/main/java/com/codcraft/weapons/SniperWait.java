package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SniperWait implements Runnable {
	
	private SniperWeapon sniper;

	public SniperWait(SniperWeapon sniper) {
		this.sniper = sniper;
	}

	@Override
	public void run() {
		List<String> toremove = new ArrayList<>();
		Iterator<String> i = sniper.waiters.iterator();
		while(i.hasNext()) {
			String s = i.next();
			Player p = Bukkit.getPlayer(s);
			if(p.getExp() <= 0) {
				toremove.add(p.getName());
			} else {
				p.setExp((float) (p.getExp() - .50));
			}
		}
		sniper.waiters.removeAll(toremove);
		
	}

}
