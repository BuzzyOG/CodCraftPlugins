package com.codcraft.cccross;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;

/**
 * Class for the cross communication of servers to the master! 
 *
 *
 */
public class Cross extends CCModule {

	public Cross(CCAPI api) {
		super(api);
	}

	@Override
	public void closing() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Load the configuration of the server to get infomation!
	 */
	@Override
	public void starting() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./modules/Cross.yml"));
		//Host for the master connection
		String host = config.getString("host");
		//Port for the connect to the master
		String port = config.getString("port");
		//Username for the conection
		String username = config.getString("username");
		//Password for the connection
		String password = config.getString("password");
		if(!validConnectionSetting(host, port, username, password)) {
			throw new IllegalArgumentException("Bad setting...");
		}
		
		
	}
	
	private boolean validConnectionSetting(String host, String port, String username, String password) {
		try {
			if(!Inet4Address.getByName(host).isReachable(1000)) return false;
			if(isInt(port) == null) return false;
			if(username == null) return false;
			if(password == null) return false;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
		
	}
	
	private Integer isInt(String i) {
		try {
			return Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
