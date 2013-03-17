package com.codcraft.ccssbb;

import java.util.HashMap;
import java.util.Map;

import com.codcraft.ccssbb.CCSSBB.states;

public class SSBmodel {
	
	protected states state;

	protected String map;
	
	protected Map<String, String> playerclass = new HashMap<String, String>();
	
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public states getState() {
		return state;
	}

	public void setState(states state) {
		this.state = state;
	}
}
