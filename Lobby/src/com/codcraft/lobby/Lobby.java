package com.codcraft.lobby;

import org.bukkit.Location;

public abstract class Lobby
{
  protected String name;
  protected Location block1;
  protected Location block2;
  protected String game;
  protected Location signBlock;
  protected Location lampblock;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getBlock1() {
    return this.block1;
  }

  public void setBlock1(Location block1) {
    this.block1 = block1;
  }

  public Location getBlock2() {
    return this.block2;
  }

  public void setBlock2(Location block2) {
    this.block2 = block2;
  }

  public String getGame() {
    return this.game;
  }

  public void setGame(String game) {
    this.game = game;
  }

  public Location getSignBlock() {
    return this.signBlock;
  }

  public void setSignBlock(Location signBlock) {
    this.signBlock = signBlock;
  }

  public Location getLampblock() {
    return this.lampblock;
  }

  public void setLampblock(Location lampblock) {
    this.lampblock = lampblock;
  }
}