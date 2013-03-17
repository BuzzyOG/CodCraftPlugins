package com.codcraft.lobby.ping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.codcraft.lobby.CCLobby;
import com.codcraft.lobby.Module;

public class Ping
{
  static Ping _instance = null;
  File configfile;
  FileConfiguration config;
  public Map<String, Result> results = new ConcurrentHashMap<String, Result>();
  public Map<String, String> pinglist = new ConcurrentHashMap<String, String>();
  public HashMap<String, String> display = new HashMap<String, String>();
  public ConfigurationSection servers;

  public static Ping getInstance()
  {
    if (_instance == null) {
      _instance = new Ping();
    }

    return _instance;
  }
  
  public void loadConfig2() {
	  CCLobby plugin = (CCLobby) Bukkit.getServer().getPluginManager().getPlugin("CCLobbyMaker");
	  for(Module modle : plugin.configmap) {
		  plugin.getLogger().info("Load Config" + modle.IP);
		  this.pinglist.put(modle.name, modle.IP);
		  this.display.put(modle.name, modle.server);
	  }
	  
  }

  public void loadConfig() {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("TeleportSigns");
    this.configfile = new File(plugin.getDataFolder(), "ping.yml");
    if (!this.configfile.exists()) {
      plugin.saveResource("ping.yml", false);
    }
    this.config = YamlConfiguration.loadConfiguration(this.configfile);
    this.servers = this.config.getConfigurationSection("servers");
    Set<String> keys = this.servers.getKeys(false);
    for (String s : keys) {
      ConfigurationSection cs = this.servers.getConfigurationSection(s);
      this.pinglist.put(s, cs.getString("address"));
      this.display.put(s, cs.getString("displayname"));
    }
  }

  public void startPing() {
    startPing(this.pinglist);
  }

  public void startPing(Map<String, String> toping) {
    final PingThread pt = new PingThread(toping);
    Timer timer = new Timer();
    TimerTask task = new TimerTask()
    {
      public void run() {
        pt.ping();
      }
    };
    timer.schedule(task, 2000L, 5000L);
  }
}