package com.github.leotuet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.github.leotuet.Benchmark.Runnable;
import com.github.leotuet.datastructures.Edge;
import com.github.leotuet.datastructures.Graph;
import com.github.leotuet.datastructures.Node;
import com.github.leotuet.datastructures.PriorityNode;

public class AStar implements Runnable<String> {
	private Graph graph;

	public AStar(Graph graph) {
		this.graph = graph;
	}

	public String run(long startNodeKey, long endNodeKey) {
		PriorityQueue<PriorityNode> priorityQueue = new PriorityQueue<>();
		HashMap<Long, PriorityNode> discoveredNeighborNodes = new HashMap<Long, PriorityNode>();
		HashSet<Long> shortestPathDiscovered = new HashSet<Long>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		PriorityNode startPriorityNode = new PriorityNode(startNode, 0.0, 0.0, 0l);
		priorityQueue.add(startPriorityNode);

		while (!priorityQueue.isEmpty()) {
			PriorityNode currentPriorityNode = priorityQueue.poll();
			Node currentNode = currentPriorityNode.getNode();

			if (currentNode.getKey() == endNodeKey) {
				return constructPath(discoveredNeighborNodes, startNodeKey, endNodeKey);
			}

			shortestPathDiscovered.add(currentNode.getKey());
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				if (shortestPathDiscovered.contains(targetNodeKey)) {
					continue;
				}

				double totalCost = currentPriorityNode.getTotalCost() + edge.getCost();
				PriorityNode discoveredNeighborNode = discoveredNeighborNodes.get(targetNodeKey);

				// Check if Node and a more cost efficient way to the Node was already discoverd
				if (discoveredNeighborNode != null && totalCost >= discoveredNeighborNode.getTotalCost()) {
					continue;
				}

				double estimatedTotalCost = totalCost + calculateHeuristic(targetNode, endNode);
				PriorityNode priorityNode = new PriorityNode(targetNode, totalCost, estimatedTotalCost, currentNode.getKey());
				discoveredNeighborNodes.put(targetNodeKey, priorityNode);

				if (discoveredNeighborNode == null) {
					priorityQueue.add(priorityNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					priorityQueue.remove(discoveredNeighborNode);
					priorityQueue.add(priorityNode);
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

	private String constructPath(HashMap<Long, PriorityNode> discoveredNeighborNodes, long startNodeKey,
			long endNodeKey) {
		long currentKey = endNodeKey;
		ArrayList<Long> route = new ArrayList<>();
		route.add(endNodeKey);
		while (currentKey != startNodeKey) {
			long predecessorNodeKey = discoveredNeighborNodes.get(currentKey).getPredecessorNodeKey();
			route.add(0, predecessorNodeKey);
			currentKey = predecessorNodeKey;
		}
		return route.toString();
	}

}
