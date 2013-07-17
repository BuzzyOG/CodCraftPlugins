package com.codcraft.lobby.ping;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Ping {
	
	//Info
	private final String name;
	private InetSocketAddress server;
	private int port;
	
	//ReturnInfo
	private Integer players;
	private Integer maxplayers;
	private String motd;
	private int pingVersion;
	private int protocolVersion;
	private String gameVersion;
	private boolean online = false;
	
	//updating
	private boolean updating = false;
	

	public Ping(String name, String IP, int port) {
		this.name = name;
		this.server = new InetSocketAddress(IP, port);
		this.port = port;
	}
	
	public Ping(String name) {
		this.name = name;
	}
	
	public List<String> getData() {
		List<String> data = new ArrayList<>();
		data.add(name);
		data.add(""+players);
		data.add(""+maxplayers);
		data.add(motd);
		data.add(""+isOnline());
		return data;
	}
	
	@SuppressWarnings("resource")
	public boolean ping() {
		updating = true;
		online = false;
		try {
	      Socket socket = new Socket();

	      socket.setSoTimeout(200);

	      socket.connect(this.server, 200);

	      OutputStream outputStream = socket.getOutputStream();
	      DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

	      InputStream inputStream = socket.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));

	      dataOutputStream.write(new byte[] { -2, 1, -6 });

	      dataOutputStream.writeShort(11);
	      dataOutputStream.write("MC|PingHost".getBytes("UTF-16BE"));
	      dataOutputStream.writeShort(server.getHostString().length() * 2 + 7);
	      dataOutputStream.write(73);
	      dataOutputStream.writeShort(server.getHostString().length());
	      dataOutputStream.write(server.getHostString().getBytes("UTF-16BE"));
	      dataOutputStream.writeInt(server.getPort());

	      int packetId = inputStream.read();

	      if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	      }

	      if (packetId != 255) {
	        throw new IOException("Invalid packet ID (" + packetId + ").");
	      }

	      int length = inputStreamReader.read();

	      if (length == -1) {
	        throw new IOException("Premature end of stream.");
	      }

	      if (length == 0) {
	        throw new IOException("Invalid string length.");
	      }

	      char[] chars = new char[length];

	      if (inputStreamReader.read(chars, 0, length) != length) {
	        throw new IOException("Premature end of stream.");
	      }

	      String string = new String(chars); 
	      if (string.startsWith("\247")) {
	        String[] data = string.split("\0");
	        online = true;
	        this.pingVersion = Integer.parseInt(data[0].substring(1));
	        this.protocolVersion = Integer.parseInt(data[1]);
	        this.gameVersion = data[2];
	        this.motd = data[3];
	        this.players = Integer.parseInt(data[4]);
	        this.maxplayers = Integer.parseInt(data[5]);
	      } else {
	    	  online = true;
	        String[] data = string.split("§");
	        this.motd = data[0];
	        this.players = Integer.parseInt(data[1]);
	        this.maxplayers = Integer.parseInt(data[2]);
	      }
	      try {
	        Thread.sleep(100L);
	      } catch (InterruptedException ex) {
	    	  Bukkit.getLogger().log(Level.SEVERE, "[TeleportSigns] Something is wrong here!", ex);
	      }
	      dataOutputStream.close();
	      outputStream.close();

	      inputStreamReader.close();
	      inputStream.close();
	      socket.close();
	    } catch (SocketException exception) {
	      updating = false;
	      return false;
	    } catch (IOException exception) {
	      updating = false;
	      return false;
	    }
		updating = false;
		return true;
	}
	

	
	
	public String getName() {
		return name;
	}


	public InetSocketAddress getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = new InetSocketAddress(server, port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Integer getPlayers() {
		return players;
	}

	public Integer getMaxplayers() {
		return maxplayers;
	}

	public String getMotd() {
		return motd;
	}

	public int getPingVersion() {
		return pingVersion;
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public boolean isUpdating() {
		return updating;
	}

	public void setUpdating(boolean updating) {
		this.updating = updating;
	}

	public boolean isOnline() {
		return online;
	}
	
}
