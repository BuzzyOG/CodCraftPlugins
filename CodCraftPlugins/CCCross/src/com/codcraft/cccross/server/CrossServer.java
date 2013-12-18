package com.codcraft.cccross.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;

import com.codcraft.cccross.CCCrossPlugin;
import com.codcraft.cccross.model.GameInfo;
import com.codcraft.cccross.model.ServerInfo;

public class CrossServer implements Runnable {

	//private String host;
	private int port;
	private ServerSocket ss;
	private Thread read;
	private Map<Long, ServerInfo> officalList = new HashMap<>();
	private boolean debug = false;
	private boolean running = false;
	private CCCrossPlugin plugin;

	public CrossServer(CCCrossPlugin plugin, String hostname, int port) {
		//this.host = hostname;
		this.port = port;
		this.plugin = plugin;
	}
	
	
	public void start() throws IOException {
		running = true;
		ss = new ServerSocket(port);
		read = new Thread(this);
		read.start();
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				List<Long> longs = new ArrayList<>();
				for(Entry<Long, ServerInfo> en : officalList.entrySet()) {
					if(System.currentTimeMillis() - en.getKey() > 5000) {
						longs.add(en.getKey());
					}
				}
				for(Long l : longs) {
					officalList.remove(l);
				}
			}
		}, 100, 100);
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		running = false;
		if(ss != null) {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(read != null) {
			read.stop();
			//read.destroy();
		}

	}


	@Override
	public void run() {
		while(running) {
			try {
				Socket s = ss.accept();
				printMessage("Connection has been established to server from " + s.getInetAddress() + ": " + s.getPort());
				ObjectInputStream input = new ObjectInputStream(s.getInputStream());
				boolean done = false;
				while(!done) {
					byte messageType = input.readByte();
					switch(messageType) {
						case 1:
						try {
							Object obj = input.readObject();
							if(obj instanceof ServerInfo) {
								ServerInfo info = (ServerInfo) obj;
								boolean check = false;
								Long infotoremove = null;
								for(Entry<Long, ServerInfo> osi : officalList.entrySet()) {
									if(check(info, osi.getValue())) {
										infotoremove = osi.getKey();
										check = true;
									}
								}
								officalList.remove(infotoremove);
								if(check) {
									officalList.remove(infotoremove);
									officalList.put(System.currentTimeMillis(), info);
								} else {
									officalList.put(System.currentTimeMillis(), info);
								}
								
							} else {
								printMessage("Bad Object");
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						case 2:
							done = true;
						case 3: 
							done = true;
						default:
							done = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private boolean check(ServerInfo info, ServerInfo osi) {
		if(!info.getId().equalsIgnoreCase(osi.getId()))  return false;
		//if(!info.getAddress().equalsIgnoreCase(osi.getAddress())) return false;
		if(!info.getBungeeid().equalsIgnoreCase(osi.getBungeeid())) return false;
		return true;
	}


	private void printMessage(String message) {
		if(debug) {
			System.out.println("[CCCross] " + message);
		}
	}
	
	public void printDebugOfServers() {
		for(Entry<Long, ServerInfo> en : officalList.entrySet()) {
			ServerInfo info = en.getValue();
			System.out.println("======ServerInfo======");
			System.out.println("Address: " + info.getAddress());
			System.out.println("BungeeID: " + info.getBungeeid());
			System.out.println("ID: " + info.getId());
			System.out.println("Name: " + info.getName());
			System.out.println("Port: " + info.getPort());
			String players = "Players: ";
			for(String player : info.getPlayers()) {
				players += player + ", ";
			}
			System.out.println(players);
			System.out.println("======GameInfo======");
			for(GameInfo ginfo : info.getGameInfo()) {
				System.out.println("GameState: " + ginfo.getGameState());
				System.out.println("Name: " + ginfo.getName());
				System.out.println("PluginName: " + ginfo.getPluginName());
				System.out.println("UUID: " + ginfo.getUuid());
				String gameStates = "GameStates: ";
				for(String gs : ginfo.getGamestates()) {
					gameStates += gs + ", ";
				}
				System.out.println(gameStates);
				String hooks = "Hooks: ";
				for(String h : ginfo.getHookNames()) {
					hooks += h + ", ";
				}
				System.out.println(hooks);
				String players1 = "Players: ";
				for(String player : info.getPlayers()) {
					players1 += player + ", ";
				}
				System.out.println(players1);
				
			}
			System.out.println("====================");
			System.out.println("====================");
			
		}
	}
	
	

}
