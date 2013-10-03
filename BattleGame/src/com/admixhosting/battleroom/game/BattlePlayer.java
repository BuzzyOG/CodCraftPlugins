package com.admixhosting.battleroom.game;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.CodCraft.api.model.TeamPlayer;

public class BattlePlayer extends TeamPlayer {
	
	private Vector toMove;
	private Boolean onWall = false;
	private Location oldLoc;
	private Boolean frozen = false;
	private boolean InBase = false;
	public boolean medic = false;
	//private boolean leader = false;
	private boolean permfrozen = false;
	private Location frozenpoint;
	
	private int frozens;
	private int unfrozen;

	public BattlePlayer(String name) {
		super(name);
	}

	public Vector getToMove() {
		return toMove;
	}

	public void setToMove(Vector toMove) {
		this.toMove = toMove;
	}

	public Boolean getOnWall() {
		return onWall;
	}

	public void setOnWall(Boolean onWall) {
		this.onWall = onWall;
	}

	public Location getOldLoc() {
		return oldLoc;
	}

	public void setOldLoc(Location oldLoc) {
		this.oldLoc = oldLoc;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isInBase() {
		return InBase;
	}

	public void setInBase(boolean inBase) {
		InBase = inBase;
	}

	public boolean isPermfrozen() {
		return permfrozen;
	}

	public void setPermfrozen(boolean permfrozen) {
		this.permfrozen = permfrozen;
	}

	public Location getFrozenpoint() {
		return frozenpoint;
	}

	public void setFrozenpoint(Location frozenpoint) {
		this.frozenpoint = frozenpoint;
	}

	public int getFrozens() {
		return frozens;
	}

	public void setFrozens(int frozens) {
		this.frozens = frozens;
	}

	public int getUnfrozen() {
		return unfrozen;
	}

	public void setUnfrozen(int unfrozen) {
		this.unfrozen = unfrozen;
	}

}
