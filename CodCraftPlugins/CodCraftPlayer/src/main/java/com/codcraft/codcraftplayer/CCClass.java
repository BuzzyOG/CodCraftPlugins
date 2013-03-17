package com.codcraft.codcraftplayer;

public class CCClass {
	
	protected String gun = "test";
	
	protected String attachment = "test";
	
	protected String Perk1 = "test";
	
	protected String Perk2 = "test";
	
	protected String Perk3 = "test";
	
	protected String Equipment = "test";
	
	protected String KillStreak = "test";

	public String mysqlid;

	public String getKillStreak() {
		return KillStreak;
	}

	public void setKillStreak(String killStreak) {
		KillStreak = killStreak;
	}

	public String getPerk3() {
		return Perk3;
	}

	public void setPerk3(String perk3) {
		Perk3 = perk3;
	}

	public String getPerk2() {
		return Perk2;
	}

	public void setPerk2(String perk2) {
		Perk2 = perk2;
	}

	public String getPerk1() {
		return Perk1;
	}

	public void setPerk1(String perk1) {
		Perk1 = perk1;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getGun() {
		return gun;
	}

	public void setGun(String gun) {
		this.gun = gun;
	}

	public String getEquipment() {
		return Equipment;
	}

	public void setEquipment(String Equipment) {
		this.Equipment = Equipment;
		
	}

}
