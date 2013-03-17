package com.codcraft.PlayerDataViewer;


import java.util.ArrayList;
import java.util.List;

public abstract class Board {
	protected List<Line> lines = new ArrayList<>();
	
	public void addLine(Line line) {
		lines.add(line);
	}
	
	
}
