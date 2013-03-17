package com.codcraft.PlayerDataViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Line {
	
	protected Map<String, ArrayList<Character>> letters = new HashMap<String, ArrayList<Character>>();
	
	protected List<Segment> segmants = new ArrayList<>();

}
