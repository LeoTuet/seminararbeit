package com.github.leotuet.datastructures;

import java.util.HashMap;
import java.util.ArrayList;

public class DiscoveredNodeMap extends HashMap<Long, DiscoveredNode> {

	public String constructPath(long startNodeKey, long endNodeKey) {
		ArrayList<Long> route = new ArrayList<>();
		long currentKey = endNodeKey;
		route.add(endNodeKey);
		while (currentKey != startNodeKey) {
			long predecessorNodeKey = this.get(currentKey).getPredecessorNodeKey();
			route.add(0, predecessorNodeKey);
			currentKey = predecessorNodeKey;
		}
		return route.toString();
	}

}
