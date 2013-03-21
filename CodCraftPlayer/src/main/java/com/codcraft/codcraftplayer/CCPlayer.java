package com.codcraft.codcraftplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;


public class CCPlayer {
	
	/**
	 * Integer for amount of CaC a player can have
	 */
	protected Integer CaCint;
	/**
	 * List of current Classes
	 */
	protected Map<Integer, CCClass> classes = new HashMap<Integer, CCClass>();
	
	
	public Integer mysqlid;
	
	protected Integer CCPoints;
	
	protected Integer CCLevel;
	
	protected Integer Kills;
	
	protected Integer Deaths;
	
	protected Integer Wins;
	
	protected Integer Losses;

	protected Integer currentclass = 1;
	
	public Map<Integer, CCClass> getMapClasses() {
		return ImmutableMap.copyOf(classes);
	}
	public List<CCClass> getAllClasses() {
		ArrayList<CCClass> classesl = new ArrayList<>();
		for(Entry<Integer, CCClass> s : classes.entrySet()) {
			classesl.add(s.getValue());
		}
		return classesl;
	}
	
	public Integer getCurrentclass() {
		return currentclass;
	}
	public void setCurrentclass(Integer currentclass) {
		this.currentclass = currentclass;
	}
	public Integer getLosses() {
		return Losses;
	}
	public void setLosses(Integer losses) {
		Losses = losses;
	}
	public Integer getDeaths() {
		return Deaths;
	}
	public void setDeaths(Integer deaths) {
		Deaths = deaths;
	}
	public Integer getWins() {
		return Wins;
	}
	public void setWins(Integer wins) {
		Wins = wins;
	}
	public void setPoints(Integer points) {
		CCPoints = points;
	}
	public Integer getPoints() {
		return CCPoints;
	}
	
	public void setLevel(Integer Level) {
		CCLevel = Level;
	}
	public Integer getKills() {
		return Kills;
	}
	public void setKills(Integer kills) {
		Kills = kills;
	}
	public Integer getLevel() {
		return CCLevel;
	}
	
	
	public boolean setClass(CCClass clazz, int classspot) {
		if(classspot > CaCint) {
			return false;
		}
		if(classes.containsKey(classspot)) {
			return false;
		} else {
			classes.put(classspot, clazz);
			return true;	
		}

	}
	
	
	public CCClass getClass(int classnumber) {
		if(classnumber > CaCint) {
			return null;
		}
		if(classes.get(classnumber) == null) {
			CCClass newClass = new CCClass();
			//TODO set the dedaults
			classes.put(classnumber, newClass);
			return classes.get(classnumber);
		} else {
			return classes.get(classnumber);
		}

		
	}
	public Integer getCaCint() {
		return CaCint;
	}
	public void setCaCint(Integer caCint) {
		CaCint = caCint;
	} 
	
	

}
