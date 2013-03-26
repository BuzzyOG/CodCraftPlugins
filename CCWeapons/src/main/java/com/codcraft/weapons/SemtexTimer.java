package com.codcraft.weapons;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;



public class SemtexTimer implements Runnable {
	private Egg entity;
	private Semtex semtex;
	public SemtexTimer(Semtex sem, Entity e) {
		this.entity = (Egg) e;
		this.semtex = sem;
	}

	@Override
	public void run() {
		if(semtex.spots.containsKey(entity)) {
			if(semtex.spots.get(entity) == null) {
				entity.remove();
				explodedetect();
				semtex.spots.remove(entity);
			} else {
			}
		}
		
	}

	private void explodedetect() {
	}

}
