package de.leotuet.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import de.leotuet.Heuristik;
import de.leotuet.datastructures.DiscoveredNode;
import de.leotuet.datastructures.Edge;
import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class AStarFibonacciHeap {

	public static ArrayList<Long> run(Graph graph, long startNodeKey, long endNodeKey) {
		FibonacciHeap<DiscoveredNode> fibonacciHeap = new FibonacciHeap<>();
		HashMap<Long, FibonacciHeap.Entry<DiscoveredNode>> discoveredNodes = new HashMap<>();
		HashSet<Long> exploredNodeKeys = new HashSet<>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		DiscoveredNode discoveredStartNode = new DiscoveredNode(startNode, 0.0, 0.0, 0l);
		fibonacciHeap.enqueue(discoveredStartNode, 0);

		while (!fibonacciHeap.isEmpty()) {
			DiscoveredNode currentDiscoveredNode = fibonacciHeap.dequeueMin().getValue();
			Node currentNode = currentDiscoveredNode.getNode();
			long currentNodeKey = currentNode.getKey();

			// when the end node is reached return the constructed path to it
			if (currentNodeKey == endNodeKey) {
				return constructPath(discoveredNodes, startNodeKey, endNodeKey);
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
				FibonacciHeap.Entry<DiscoveredNode> discoveredNode = discoveredNodes.get(targetNodeKey);

				// Check if Node and a more cost efficient way to the Node was already discoverd
				if (discoveredNode != null && totalCost >= discoveredNode.getValue().getTotalCost()) {
					continue;
				}

				double estimatedTotalCostToEnd = totalCost + Heuristik.estimateTime(graph, targetNode, endNode);

				if (discoveredNode == null) {
					DiscoveredNode newDiscoveredNode = new DiscoveredNode(targetNode, totalCost, estimatedTotalCostToEnd, currentNodeKey);
					var entry = fibonacciHeap.enqueue(newDiscoveredNode, estimatedTotalCostToEnd);
					discoveredNodes.put(targetNodeKey, entry);
				} else {
					// The Node was already discovered but this path is more cost efficient
					// the priorityQueue only executes the compareTo function on insertion which is why the old object is removed and not
					// updated
					fibonacciHeap.decreaseKeyUnchecked(discoveredNode, estimatedTotalCostToEnd);
				}

			}

		}

		return null;
	}

	public static ArrayList<Long> constructPath(HashMap<Long, FibonacciHeap.Entry<DiscoveredNode>> discoveredNodes,
			long startNodeKey, long endNodeKey) {
		ArrayList<Long> route = new ArrayList<>();
		long currentKey = endNodeKey;
		route.add(endNodeKey);
		while (currentKey != startNodeKey) {
			long predecessorNodeKey = discoveredNodes.get(currentKey).getValue().getPredecessorNodeKey();
			route.add(0, predecessorNodeKey);
			currentKey = predecessorNodeKey;
		}
		return route;
	}

}
