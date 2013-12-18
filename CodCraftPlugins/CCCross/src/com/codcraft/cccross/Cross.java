package com.codcraft.cccross;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.hook.Hook;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCModule;
import com.codcraft.cccross.model.GameInfo;
import com.codcraft.cccross.model.ServerInfo;
import com.codcraft.cccross.server.CrossServer;

/**
 * Class for the cross communication of servers to the master! 
 *
 *
 */
public class Cross extends CCModule {
	
	private ServerInfo serverinfo;
	private Map<String, GameInfo> gameInfos = new HashMap<>();
	//Null inless server is being run.
	private CrossServer server = null;
	private CCCrossPlugin plugin;
	private Socket sock;

	public Cross(CCCrossPlugin plugin, CCAPI api) {
		super(api);
		this.plugin = plugin;
	}

	@Override
	public void closing() {
		if(server != null) {
			System.out.println(server);
			server.stop();
		}
		
	}

	
	/**
	 * Load the configuration of the server to get infomation!
	 */
	@Override
	public void starting() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./modules/Cross.yml"));
		//Check if this instance should run a server
		boolean runServer = config.getBoolean("runServer");
		if(runServer) {
			String hostname = config.getString("serverHost");
			int port = Integer.parseInt(config.getString("serverPort"));
			server = new CrossServer(hostname, port);
			try {
				server.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Host for the master connection
		String host = config.getString("host");
		//Port for the connect to the master
		String port = config.getString("port");
		//Username for the conection
		String username = config.getString("username");
		//Password for the connection
		String password = config.getString("password");
		if(validConnectionSetting(host, port, username, password) != null) {
			throw new IllegalArgumentException("Bad setting... " + validConnectionSetting(host, port, username, password));
		}
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			/**
			 * Timer for updating all gameInfo stats
			 */
			@Override
			public void run() {
				generateServerInfo();
				
			}
		}, 0, 20);
		generateServerInfo();
		try {
			sock = new Socket(host, isInt(port));
			ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
			System.out.println("Pause 1 sec");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done waiting!");
			out.writeByte(1);
			System.out.println("Sending this object over " + serverinfo);
			out.writeObject(serverinfo);
			out.flush();
			sock.close();
			System.out.println("Got here");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String validConnectionSetting(String host, String port, String username, String password) {
		try {
			if(!Inet4Address.getByName(host).isReachable(2000)) return "Unreachable";
			if(isInt(port) == null) return "Port not int";
			if(username == null) return "username is null";
			if(password == null) return "password is null";
		} catch (UnknownHostException e) {
			return "Error UnknowHost";
		} catch (IOException e) {
			return "Error IOException";
		}
		return null;
		
	}
	
	private Integer isInt(String i) {
		try {
			return Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private void generateServerInfo() {
		ServerInfo info = new ServerInfo();
		info.gen(api);
		serverinfo = info;
	}

}
