package com.codcraft.cchat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class AntiAd extends JavaPlugin {

    public static final String INCOMING_PLUGIN_CHANNEL = "CodCraftChat";
	public static final String OUTGOING_PLUGIN_CHANNEL = "CodCraft";
	private Adfinder adfinder;
	public Utilities utils;
	
	public Map<String, ChatType> players = new HashMap<String, ChatType>();
	public CCAPI api;

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " " + "Version" + " " + getDescription().getVersion() + " is now Disabled");
    }

    @Override
    public void onEnable() {
        adfinder = new Adfinder(this);
        
        utils = new Utilities(this);

        registerChannels();

        //Setting op the plugin listener to listen on this :) 
        getServer().getPluginManager().registerEvents(new ADListener(this), this);

        //Setting up the command Executer (antiAD)
        //getCommand("antiad").setExecutor(new ADCommand(this));

        checkConfig();
        final FileConfiguration config = getConfig();
        if(!config.contains("Detected-Commands")){
        config.addDefault("Detected-Commands", Arrays.asList("/msg", "/message", "/tell"));
        
        saveConfig();
        }
        
        File whitelistFile = new File(getDataFolder(), "Whitelist.txt");
        if (!whitelistFile.exists()) {
            try {
                whitelistFile.createNewFile();
            } catch (IOException ex) {
                getLogger().log(Level.WARNING, "Anti Ad error while making new whitelist file!");
            }
        }
        
        Plugin plug = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
        if(plug != null && plug instanceof CCAPI) {
        	api = (CCAPI) plug;
        }

        File logFile = new File(getDataFolder(), "Log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException ex) {
                getLogger().log(Level.WARNING, "Anti Ad error while making new Log file file!");
            }
        }


        for(Player p : Bukkit.getOnlinePlayers()) {
        	if(!players.containsKey(p.getName())) {
        		players.put(p.getName(), ChatType.SERVER);
        	}
        }


        getLogger().info(getDescription().getName() + " " + "Version" + " " + getDescription().getVersion() + " is now Enabled");

    }

    private void registerChannels() {
        Bukkit.getMessenger().registerIncomingPluginChannel(this, INCOMING_PLUGIN_CHANNEL, new ChatListener(this));
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, OUTGOING_PLUGIN_CHANNEL);
	}

	public Adfinder getAdfinder() {
        return adfinder;
    }
	
	  public void sendGlobalMessage(String sender, String format) {
		  ByteArrayOutputStream b = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(b);
	    try {
	      out.writeUTF("ChatMessage");
	      out.writeUTF(sender);
	      out.writeUTF(format);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    new PluginMessageTask(this, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(this);
	  }
    
    private void checkConfig() {
        String name = "config.yml";
        File actual = new File(getDataFolder(), name);
        if (!actual.exists()) {
            getDataFolder().mkdir();
            InputStream input = this.getClass().getResourceAsStream(
                    "/config.yml");
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(actual);
                    byte[] buf = new byte[4096]; // [8192]?
                    int length;
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }
                    getLogger().info(" Loading the Default config: " + name);
                } catch (IOException e) {
                    getLogger().warning("Error while loading the file!!!!!!"+e.getMessage());
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {
                    }

                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
