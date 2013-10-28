package com.codcraft.codcraftplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
	
	private String name;
	
	
	public Integer mysqlid = 0;
	
	protected Integer CCPoints = 0;
	
	protected Integer CCLevel = 0;
	
	protected Integer Kills = 0;
	
	protected Integer Deaths = 0;
	
	protected Integer TDMKills = 0;
	
	protected Integer TDMDeaths = 0;
	
	protected Integer TDMWins = 0;
	
	protected Integer TDMLosses = 0;
	
	protected Integer FFAKills = 0;
	
	protected Integer FFADeaths = 0;
	
	protected Integer FFAWins = 0;
	
	protected Integer FFALosses = 0;
	
	protected Integer SSBKills = 0;
	
	protected Integer SSBDeaths = 0;
	
	protected Integer SSBWins = 0;
	
	protected Integer SSBLosses = 0;
	
	protected Integer UHCKills = 0;
	
	protected Integer UHCDeaths = 0;
	
	protected Integer UHCWins = 0;
	
	protected Integer UHCLosses = 0;
	
	protected Integer Wins = 0;
	
	protected Integer Losses = 0;

	protected Integer currentclass = 1;
	
	protected Integer freezes = 0;
	
	protected Integer unfreezes = 0;
	
	protected Integer BGwins = 0;
	protected Integer BGlosses = 0;
	
	protected Integer credits = 0;
	protected Integer rawCredits = 0;
	
	public CCPlayer(String name) {
		this.name = name;
	}
	
	
	public int incrementCredit(int i) {
		Player p = Bukkit.getPlayer(name);
		int finish = i;
		if(p.hasPermission("battleroom.credit25")){
			finish = (int) (i * 2.5);
		} else if(p.hasPermission("battleroom.credit20")) {
			finish = i * 2;
		} else if(p.hasPermission("battleroom.credit15")) {
			finish = (int) (i * 1.5);
		}
		credits += finish;
		rawCredits += i;
		return finish;
	}
	
	public int getRawCredits() {
		return rawCredits;
	}
	
	public void setRawCredits(int i) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "rawCredits", rawCredits, i);
		Bukkit.getPluginManager().callEvent(event);
		rawCredits = event.getNewscore();
	}
	 
	public void removeCredits(int i) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "rcredits", credits, credits - i);
		Bukkit.getPluginManager().callEvent(event);
		credits = event.getNewscore();
	}
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int i) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "credits", credits, i);
		Bukkit.getPluginManager().callEvent(event);
		credits = event.getNewscore();
	}
	
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
	public boolean setCurrentclass(Integer currentclass) {
		if(currentclass > CaCint || currentclass <= 0) {
			return false;
		}
		this.currentclass = currentclass;
		return true;
	}
	public Integer getLosses() {
		return Losses;
	}
	public void setLosses(Integer losses) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "Losses", Losses, losses);
		Bukkit.getPluginManager().callEvent(event);
		Losses = event.getNewscore();
	}
	public Integer getDeaths() {
		return Deaths;
	}
	public void setDeaths(Integer deaths) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "Deaths", Deaths, deaths);
		Bukkit.getPluginManager().callEvent(event);
		Deaths = event.getNewscore();
	}
	public Integer getWins() {
		return Wins;
	}
	public void setWins(Integer wins) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "Wins", Wins, wins);
		Bukkit.getPluginManager().callEvent(event);
		Wins = event.getNewscore();
	}
	public void setPoints(Integer points) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "CCPoints", CCPoints, points);
		Bukkit.getPluginManager().callEvent(event);
		CCPoints = event.getNewscore();
	}
	public Integer getPoints() {
		return CCPoints;
	}
	
	public void setLevel(Integer Level) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "CCLevel", CCLevel, Level);
		Bukkit.getPluginManager().callEvent(event);
		CCLevel = event.getNewscore();
	}
	public Integer getKills() {
		return Kills;
	}
	public void setKills(Integer kills) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "Kills", Kills, kills);
		Bukkit.getPluginManager().callEvent(event);
		Kills = event.getNewscore();
	}
	public Integer getTDMKills() {
		return TDMKills;
	}
	public void setTDMKills(Integer tDMKills) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "TDMKills", TDMKills, tDMKills);
		Bukkit.getPluginManager().callEvent(event);
		TDMKills = event.getNewscore();
	}
	public Integer getTDMDeaths() {
		return TDMDeaths;
	}
	public void setTDMDeaths(Integer tDMDeaths) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "TDMDeaths", TDMDeaths, tDMDeaths);
		Bukkit.getPluginManager().callEvent(event);
		TDMDeaths = event.getNewscore();
	}
	public Integer getTDMWins() {
		return TDMWins;
	}
	public void setTDMWins(Integer tDMWins) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "TDMWins", TDMWins, tDMWins);
		Bukkit.getPluginManager().callEvent(event);
		TDMWins = event.getNewscore();
	}
	public Integer getTDMLosses() {
		return TDMLosses;
	}
	public void setTDMLosses(Integer tDMLosses) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "TDMLosses", TDMLosses, tDMLosses);
		Bukkit.getPluginManager().callEvent(event);
		TDMLosses = event.getNewscore();
	}
	public Integer getFFAKills() {
		return FFAKills;
	}
	public Integer getFFADeaths() {
		return FFADeaths;
	}
	public Integer getFFAWins() {
		return FFAWins;
	}
	public Integer getFreezes() {
		return freezes;
	}
	public void setFreezes(Integer freezes) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "freezes", this.freezes, freezes);
		Bukkit.getPluginManager().callEvent(event);
		this.freezes = event.getNewscore();
	}
	public Integer getFFALosses() {
		return FFALosses;
	}
	public void setFFALosses(Integer fFALosses) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "FFALosses", FFALosses, fFALosses);
		Bukkit.getPluginManager().callEvent(event);
		FFALosses = event.getNewscore();
	}
	public void setFFAWins(Integer fFAWins) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "FFAWins", FFAWins, fFAWins);
		Bukkit.getPluginManager().callEvent(event);
		FFAWins = event.getNewscore();
	}
	public void setFFADeaths(Integer fFADeaths) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "FFADeaths", FFADeaths, fFADeaths);
		Bukkit.getPluginManager().callEvent(event);
		FFADeaths = event.getNewscore();
	}
	public void setFFAKills(Integer fFAKills) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "FFAKills", FFAKills, fFAKills);
		Bukkit.getPluginManager().callEvent(event);
		FFAKills = event.getNewscore();
	}
	public Integer getSSBKills() {
		return SSBKills;
	}
	@Deprecated
	public void setSSBKills(Integer sSBKills) {
		SSBKills = sSBKills;
	}
	public Integer getSSBDeaths() {
		return SSBDeaths;
	}
	@Deprecated
	public void setSSBDeaths(Integer sSBDeaths) {
		SSBDeaths = sSBDeaths;
	}
	
	public Integer getSSBWins() {
		return SSBWins;
	}
	@Deprecated
	public void setSSBWins(Integer sSBWins) {
		SSBWins = sSBWins;
	}
	public Integer getSSBLosses() {
		return SSBLosses;
	}
	@Deprecated
	public void setSSBLosses(Integer sSBLosses) {
		SSBLosses = sSBLosses;
	}
	public Integer getUHCKills() {
		return UHCKills;
	}
	@Deprecated
	public void setUHCKills(Integer uHCKills) {
		UHCKills = uHCKills;
	}
	public Integer getUHCDeaths() {
		return UHCDeaths;
	}
	@Deprecated
	public void setUHCDeaths(Integer uHCDeaths) {
		UHCDeaths = uHCDeaths;
	}
	public Integer getUHCWins() {
		return UHCWins;
	}
	@Deprecated
	public void setUHCWins(Integer uHCWins) {
		UHCWins = uHCWins;
	}
	public Integer getUHCLosses() {
		return UHCLosses;
	}
	@Deprecated
	public void setUHCLosses(Integer uHCLosses) {
		UHCLosses = uHCLosses;
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
		PlayerStatUpdate event = new PlayerStatUpdate(this, "CaCint", CaCint, caCint);
		Bukkit.getPluginManager().callEvent(event);
		CaCint = event.getNewscore();
	}
	public Integer getUnfreezes() {
		return unfreezes;
	}
	public void setUnfreezes(Integer unfreezes) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "unfreezes", this.unfreezes, unfreezes);
		Bukkit.getPluginManager().callEvent(event);
		this.unfreezes = event.getNewscore();
	}
	public Integer getBGwins() {
		return BGwins;
	}
	public void setBGwins(Integer bGwins) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "BGwins", BGwins, bGwins);
		Bukkit.getPluginManager().callEvent(event);
		BGwins = event.getNewscore();
	}
	public Integer getBGlosses() {
		return BGlosses;
	}
	public void setBGlosses(Integer bGlosses) {
		PlayerStatUpdate event = new PlayerStatUpdate(this, "BGlosses", BGlosses, bGlosses);
		Bukkit.getPluginManager().callEvent(event);
		BGlosses = event.getNewscore();
	}


	public String getName() {
		return name;
	}

	
	

}
