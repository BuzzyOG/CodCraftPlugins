package com.codcraft.cccross.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.codcraft.cccross.model.ServerInfo;

public class CrossServer implements Runnable {

	private String host;
	private int port;
	private ServerSocket ss;
	private Thread read;
	private List<ServerInfo> officalList = new ArrayList<>();
	private boolean debug = true;

	public CrossServer(String hostname, int port) {
		this.host = hostname;
		this.port = port;
	}
	
	
	public void start() throws IOException {
		ss = new ServerSocket(port);
		read = new Thread(this);
		read.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
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
		try {
			printMessage("Waiting for connection");
			Socket s = ss.accept();
			printMessage("Connection has been established to server from " + s.getInetAddress() + ": " + s.getPort());
			ObjectInputStream input = new ObjectInputStream(s.getInputStream());
			printMessage("First");
			boolean done = false;
			while(!done) {
				byte messageType = input.readByte();
				switch(messageType) {
					case 1:
					try {
						Object obj = input.readObject();
						System.out.println(obj);
						if(obj instanceof ServerInfo) {
							officalList.add((ServerInfo) obj);
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
			/*while(!s.isClosed()) {
				if(input.read() == -1) {
					
				}
				printMessage("2");
				String string = input.readUTF();
				System.out.println(string);
			}*/
			String message = "Servers, ";
			for(ServerInfo info : officalList) {
				message += info.getName() + " " + info.getPort() + ", ";
			}
			printMessage(message);
			printMessage("Socketed Closed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void printMessage(String message) {
		if(debug) {
			System.out.println("[CCCross] " + message);
		}
	}
	
	

}
