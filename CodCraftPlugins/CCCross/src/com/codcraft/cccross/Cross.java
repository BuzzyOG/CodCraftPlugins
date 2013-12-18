package com.codcraft.cccross;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;
import com.codcraft.cccross.model.ServerInfo;
import com.codcraft.cccross.server.CrossServer;

/**
 * Class for the cross communication of servers to the master! 
 *
 *
 */
public class Cross extends CCModule {
	
	private ServerInfo serverinfo;
	//Null inless server is being run.
	private CrossServer server = null;
	private CCCrossPlugin plugin;
	private Socket sock;
	private String id;
	private String bid;

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
			server = new CrossServer(plugin, hostname, port);
			try {
				server.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Host for the master connection
		final String host = config.getString("host");
		//Port for the connect to the master
		final String port = config.getString("port");
		//Username for the conection
		String username = config.getString("username");
		//Password for the connection
		String password = config.getString("password");
		
		
		
		//ID
		String id = config.getString("id");
		this.id = id;
		//BunngeeID
		String bid = config.getString("bungeeid");
		this.bid = bid;
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
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				try {
					sock = new Socket(host, isInt(port));
					ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
					out.writeByte(1);
					out.writeObject(serverinfo);
					out.flush();
					sock.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}, 0, 60);

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
		ServerInfo info = new ServerInfo(id, bid);
		info.gen(api);
		serverinfo = info;
	}

}
