package com.github.leotuet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.github.leotuet.Benchmark.Runnable;
import com.github.leotuet.datastructures.DiscoveredNodeMap;
import com.github.leotuet.datastructures.Edge;
import com.github.leotuet.datastructures.Graph;
import com.github.leotuet.datastructures.Node;
import com.github.leotuet.datastructures.DiscoveredNode;

public class AStar implements Runnable<String> {
	private Graph graph;

	public AStar(Graph graph) {
		this.graph = graph;
	}

	public String run(long startNodeKey, long endNodeKey) {
		PriorityQueue<DiscoveredNode> priorityQueue = new PriorityQueue<>();
		DiscoveredNodeMap discoveredNodes = new DiscoveredNodeMap();
		HashSet<Long> shortestPathDiscovered = new HashSet<Long>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		DiscoveredNode discoveredStartNode = new DiscoveredNode(startNode, 0.0, 0.0, 0l);
		priorityQueue.add(discoveredStartNode);

		while (!priorityQueue.isEmpty()) {
			DiscoveredNode currentDiscoveredNode = priorityQueue.poll();
			Node currentNode = currentDiscoveredNode.getNode();
			long currentNodeKey = currentNode.getKey();

			if (currentNodeKey == endNodeKey) {
				return discoveredNodes.constructPath(startNodeKey, endNodeKey);
			}

			shortestPathDiscovered.add(currentNodeKey);
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				if (shortestPathDiscovered.contains(targetNodeKey)) {
					continue;
				}

				double totalCost = currentDiscoveredNode.getTotalCost() + edge.getCost();
				DiscoveredNode discoveredNode = discoveredNodes.get(targetNodeKey);

				// Check if Node and a more cost efficient way to the Node was already discoverd
				if (discoveredNode != null && totalCost >= discoveredNode.getTotalCost()) {
					continue;
				}

				double estimatedTotalCostToEnd = totalCost + calculateHeuristic(targetNode, endNode);
				DiscoveredNode newDiscoveredNode = new DiscoveredNode(targetNode, totalCost, estimatedTotalCostToEnd,
						currentNodeKey);
				discoveredNodes.put(targetNodeKey, newDiscoveredNode);

				if (discoveredNode == null) {
					priorityQueue.add(newDiscoveredNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					priorityQueue.remove(discoveredNode);
					priorityQueue.add(newDiscoveredNode);
				}

			}

		}

		return "No route found";
	}

	private double calculateHeuristic(Node fromNode, Node toNode) {
		double deltaX = fromNode.getX() - toNode.getX();
		double deltaY = fromNode.getY() - toNode.getY();
		double euclideanDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return euclideanDistance / graph.getMaxSpeedLimit();
	}

}
