package com.codcraft.weapons;

import org.bukkit.Material;
import org.bukkit.event.block.Action;

public class Weapon
{
  private final String name;
  private int ammo;
  private int damage;
  private Material mat;
  private Action action;
  private Action otheraction = Action.RIGHT_CLICK_BLOCK;
  private int rpm = 0;
  private int reloads;
  private int reloadTime;
  private long bulletlife;

  public Weapon(String name)
  {
    this.name = name;
  }

  public Action getAction() {
    return this.action;
  }
  public void setAction(Action action) {
    this.action = action;
  }
  public Material getMat() {
    return this.mat;
  }
  public void setMat(Material mat) {
    this.mat = mat;
  }
  public int getAmmo() {
    return this.ammo;
  }
  public void setAmmo(int ammo) {
    this.ammo = ammo;
  }
  public String getName() {
    return this.name;
  }
  public int getDamage() {
    return this.damage;
  }
  public void setDamage(int damage) {
    if ((damage <= -1) || (damage >= 21)) {
      return;
    }
    this.damage = damage;
  }
  public int getRpm() {
    return this.rpm;
  }
  public void setRpm(int rpm) {
    this.rpm = rpm;
  }
  public int getReloads() {
    return this.reloads;
  }
  public void setReloads(int reloads) {
    this.reloads = reloads;
  }

  public int getReloadTime() {
    return this.reloadTime;
  }

  public void setReloadTime(int reloadTime) {
    this.reloadTime = reloadTime;
  }

  public long getBulletlife() {
    return this.bulletlife;
  }

  public void setBulletlife(long bulletlife) {
    this.bulletlife = bulletlife;
  }

  public Action getOtheraction() {
    return this.otheraction;
  }

  public void setOtheraction(Action otheraction) {
    this.otheraction = otheraction;
  }
}