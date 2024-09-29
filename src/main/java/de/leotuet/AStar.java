package de.leotuet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import de.leotuet.datastructures.DiscoveredNodeMap;
import de.leotuet.datastructures.Edge;
import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;
import de.leotuet.datastructures.DiscoveredNode;

public class AStar {

	public static String run(Graph graph, long startNodeKey, long endNodeKey) {
		PriorityQueue<DiscoveredNode> priorityQueue = new PriorityQueue<>();
		DiscoveredNodeMap discoveredNodes = new DiscoveredNodeMap();
		HashSet<Long> exploredNodes = new HashSet<Long>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		DiscoveredNode discoveredStartNode = new DiscoveredNode(startNode, 0.0, 0.0, 0l);
		priorityQueue.add(discoveredStartNode);

		while (!priorityQueue.isEmpty()) {
			DiscoveredNode currentDiscoveredNode = priorityQueue.poll();
			Node currentNode = currentDiscoveredNode.getNode();
			long currentNodeKey = currentNode.getKey();

			// when the end node is reached return the constructed path to it
			if (currentNodeKey == endNodeKey) {
				return discoveredNodes.constructPath(startNodeKey, endNodeKey);
			}

			exploredNodes.add(currentNodeKey);
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				// Continue to the next edge if the node was already explored which mean that its neighbor where already discovered
				if (exploredNodes.contains(targetNodeKey)) {
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
					priorityQueue.add(newDiscoveredNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					// the priorityQueue only executes the compareTo function on insertion which is why the old object is removed and not
					// updated
					priorityQueue.remove(discoveredNode);
					priorityQueue.add(newDiscoveredNode);
				}

			}

		}

		return "No route found";
	}

}
