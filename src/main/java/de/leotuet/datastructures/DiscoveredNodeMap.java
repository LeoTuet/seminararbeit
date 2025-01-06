package de.leotuet.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DiscoveredNodeMap extends HashMap<Long, DiscoveredNode> {

	/**
	 * Reconstructs the path from the start to the end node.
	 * 
	 * @param startNodeKey the key of the start node
	 * @param startNodeKey the key of the end node
	 * @return a list of node keys representing the path from the start to end node
	 */
	public ArrayList<Long> constructPath(long startNodeKey, long endNodeKey) {
		ArrayList<Long> path = new ArrayList<>();

		// starting from the end node
		long currentKey = endNodeKey;
		path.add(endNodeKey);

		// while the startNode is not reached
		while (currentKey != startNodeKey) {
			// get the predecessor node add it to the path and set it as the next node
			long predecessorNodeKey = this.get(currentKey).getPredecessorNodeKey();
			path.add(0, predecessorNodeKey);
			currentKey = predecessorNodeKey;
		}

		return path;
	}

	/**
	 * Reconstructs the path from the start to each of the nodes in the list. This is used only for the visualization of all
	 * discovered paths.
	 *
	 * @param startNodeKey the key of the start node
	 * @param nodeKeys the keys for which paths should be reconstructed
	 * @return a map where each key is from {@code nodeKeys} and the value is a String representing the path from the start node to
	 * the key
	 */
	public HashMap<Long, String> constructPaths(long startNodeKey, HashSet<Long> nodeKeys) {
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

		return paths;
	}

}
