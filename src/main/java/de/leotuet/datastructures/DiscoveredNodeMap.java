package de.leotuet.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

	public String constructPaths(long startNodeKey, HashSet<Long> nodeKeys) {
		HashMap<Long, String> paths = new HashMap<>();
		HashSet<Long> containedInOtherPath = new HashSet<>();

		for (long nodeKey : nodeKeys) {
			if (containedInOtherPath.contains(nodeKey)) {
				continue;
			}

			ArrayList<Long> route = new ArrayList<>();
			route.add(nodeKey);

			long currentKey = nodeKey;
			while (currentKey != startNodeKey) {
				long predecessorNodeKey = this.get(currentKey).getPredecessorNodeKey();
				route.add(0, predecessorNodeKey);
				currentKey = predecessorNodeKey;

				// When a node is contained in another node's path it does not need its own.
				// Since its path is already part of the other node's
				containedInOtherPath.add(predecessorNodeKey);

				// Since a HashSet does not guarantee insertion order a path for a node may have already been created. But this path is
				// now contained within another node's one and therefore is not needed
				if (paths.get(predecessorNodeKey) != null) {
					paths.remove(predecessorNodeKey);
				}
			}

			paths.put(route.get(route.size() - 1), route.toString());
		}

		return paths.values().toString();
	}

}
