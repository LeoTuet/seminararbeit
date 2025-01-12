package de.leotuet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import de.leotuet.datastructures.DiscoveredNode;
import de.leotuet.datastructures.DiscoveredNodeMap;
import de.leotuet.datastructures.Edge;
import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class AStar {

	/**
	 * Executes the A* algorithm on the given {@code graph} to find the shortest path from the start node to the end node.
	 *
	 * @param graph the graph to search within
	 * @param startNodeKey the key of the starting node
	 * @param endNodeKey the key of the destination node
	 * @param export a flag indicating whether to export the results for benchmarking purposes
	 * @return a list of node keys or {@code null} if no path is found
	 */
	public static ArrayList<Long> run(Graph graph, long startNodeKey, long endNodeKey, boolean export) {
		// 'open list' nodes to explore
		PriorityQueue<DiscoveredNode> priorityQueue = new PriorityQueue<>();
		DiscoveredNodeMap discoveredNodes = new DiscoveredNodeMap();
		// 'closed list' nodes which were explored
		HashSet<Long> exploredNodeKeys = new HashSet<>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		// Add the initial node with default values since they are not of importance
		DiscoveredNode discoveredStartNode = new DiscoveredNode(startNode, 0.0, 0.0, 0l);
		priorityQueue.add(discoveredStartNode);

		while (!priorityQueue.isEmpty()) {
			DiscoveredNode currentDiscoveredNode = priorityQueue.poll();
			Node currentNode = currentDiscoveredNode.getNode();
			long currentNodeKey = currentNode.getKey();

			// If the end node is reached, construct and return t he path to it
			if (currentNodeKey == endNodeKey) {
				ArrayList<Long> route = discoveredNodes.constructPath(startNodeKey, endNodeKey);

				// Only used for benchmarking purposes
				if (export) {
					HashMap<Long, String> exploredPaths = discoveredNodes.constructPaths(startNodeKey, exploredNodeKeys);
					StringExporter.saveToFile(StringExporter.PATHS_FILE_PATH, exploredPaths.values().toString());
					StringExporter.saveToFile(StringExporter.ROUTE_FILE_PATH, route.toString());
				}

				return route;
			}

			exploredNodeKeys.add(currentNodeKey);
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				// Skip this edge if the target node has already been explored, which means that its neighbors are already discovered
				if (exploredNodeKeys.contains(targetNodeKey)) {
					continue;
				}

				double totalCost = currentDiscoveredNode.getTotalCost() + edge.getCost();
				DiscoveredNode discoveredNode = discoveredNodes.get(targetNodeKey);

				// Skip if a more cost efficient way to the Node was already discoverd
				if (discoveredNode != null && totalCost >= discoveredNode.getTotalCost()) {
					continue;
				}

				// Estimate the time needed to get to the End
				double estimatedTotalCostToEnd = totalCost + Heuristik.estimateTime(graph.getMaxSpeedLimit(), targetNode, endNode);
				DiscoveredNode newDiscoveredNode = new DiscoveredNode(targetNode, totalCost, estimatedTotalCostToEnd, currentNodeKey);
				discoveredNodes.put(targetNodeKey, newDiscoveredNode);

				if (discoveredNode == null) {
					priorityQueue.add(newDiscoveredNode);
				} else {
					// The Node was already discovered, but this path is more cost efficient
					// The priorityQueue only executes the compareTo function on insertion which is why the old object is removed and not
					// updated
					priorityQueue.remove(discoveredNode);
					priorityQueue.add(newDiscoveredNode);
				}

			}

		}

		// No path was found
		return null;
	}

}
