package com.codcraft.ccssbb;

public enum CustomSkullType
{
  SPIDER("Kelevra_V", CCSSBB.format("Spider Head", new String[0])), 
  ENDERMAN("Violit", CCSSBB.format("Enderman Head", new String[0])), 
  BLAZE("Blaze_Head", CCSSBB.format("HEAD_BLAZE=Blaze Head", new String[0]));

  private final String owner;
  private final String displayName;

  private CustomSkullType(String owner, String displayName) { this.owner = owner;
    this.displayName = displayName; }

  public String getOwner()
  {
    return this.owner;
  }

  public String getDisplayName() {
    return this.displayName;
  }
}