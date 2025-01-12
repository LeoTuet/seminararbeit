package de.leotuet.performance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import de.leotuet.Heuristik;
import de.leotuet.datastructures.DiscoveredNode;
import de.leotuet.datastructures.DiscoveredNodeMap;
import de.leotuet.datastructures.Edge;
import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class AStarTreeSet {

	public static ArrayList<Long> run(Graph graph, long startNodeKey, long endNodeKey) {
		TreeSet<DiscoveredNode> treeSet = new TreeSet<>();
		DiscoveredNodeMap discoveredNodes = new DiscoveredNodeMap();
		HashSet<Long> exploredNodeKeys = new HashSet<>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		DiscoveredNode discoveredStartNode = new DiscoveredNode(startNode, 0.0, 0.0, 0l);
		treeSet.add(discoveredStartNode);

		while (!treeSet.isEmpty()) {
			DiscoveredNode currentDiscoveredNode = treeSet.pollFirst();
			Node currentNode = currentDiscoveredNode.getNode();
			long currentNodeKey = currentNode.getKey();

			// when the end node is reached return the constructed path to it
			if (currentNodeKey == endNodeKey) {
				return discoveredNodes.constructPath(startNodeKey, endNodeKey);
			}

			exploredNodeKeys.add(currentNodeKey);
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				// Continue to the next edge if the node was already explored which mean that its neighbor where already discovered
				if (exploredNodeKeys.contains(targetNodeKey)) {
					continue;
				}

				double totalCost = currentDiscoveredNode.getTotalCost() + edge.getCost();
				DiscoveredNode discoveredNode = discoveredNodes.get(targetNodeKey);

				// Check if Node and a more cost efficient way to the Node was already discoverd
				if (discoveredNode != null && totalCost >= discoveredNode.getTotalCost()) {
					continue;
				}

				double estimatedTotalCostToEnd = totalCost + Heuristik.estimateTime(graph, targetNode, endNode);
				DiscoveredNode newDiscoveredNode = new DiscoveredNode(targetNode, totalCost, estimatedTotalCostToEnd, currentNodeKey);
				discoveredNodes.put(targetNodeKey, newDiscoveredNode);

				if (discoveredNode == null) {
					treeSet.add(newDiscoveredNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					// the priorityQueue only executes the compareTo function on insertion which is why the old object is removed and not
					// updated
					treeSet.remove(discoveredNode);
					treeSet.add(newDiscoveredNode);
				}

			}

		}

		return null;
	}

}
