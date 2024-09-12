package com.github.leotuet;

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.text.DecimalFormat;

import com.github.leotuet.datastructures.Edge;
import com.github.leotuet.datastructures.Graph;
import com.github.leotuet.datastructures.Node;
import com.github.leotuet.datastructures.PriorityNode;

public class AStar {
	private Graph graph;

	public AStar(Graph graph) {
		this.graph = graph;
	}

	public String run(long startNodeKey, long endNodeKey) {
		PriorityQueue<PriorityNode> priorityQueue = new PriorityQueue<>();
		HashMap<Long, Long> predecessorNodes = new HashMap<Long, Long>();
		HashMap<Long, PriorityNode> discoveredNeighborNodes = new HashMap<Long, PriorityNode>();
		HashSet<Long> shortestPathDiscovered = new HashSet<Long>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		PriorityNode startPriorityNode = new PriorityNode(startNode, 0.0, 0.0);
		priorityQueue.add(startPriorityNode);

		while (!priorityQueue.isEmpty()) {
			PriorityNode currentPriorityNode = priorityQueue.poll();
			Node currentNode = currentPriorityNode.getNode();

			if (currentNode.getKey() == endNodeKey) {
				String path = constructPath(predecessorNodes, startNodeKey, endNodeKey);
				String time = new DecimalFormat("#.#").format(currentPriorityNode.getCostToNode() / 60) + "min";
				return path + " in " + time;
			}

			shortestPathDiscovered.add(currentNode.getKey());
			ArrayList<Edge> edges = currentNode.getEdges();

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				long targetNodeKey = targetNode.getKey();

				if (shortestPathDiscovered.contains(targetNodeKey)) {
					continue;
				}

				double costToCurrent = currentPriorityNode.getEstimatedCostToEnd() + edge.getCost();
				PriorityNode discoveredNeighborNode = discoveredNeighborNodes.get(targetNodeKey);

				// Check if Node and more coste efficient way to Node is already discoverd
				if (discoveredNeighborNode != null && costToCurrent >= discoveredNeighborNode.getCostToNode()) {
					continue;
				}

				double estimatedCostToEnd = costToCurrent + calculateHeuristic(targetNode, endNode);
				PriorityNode priorityNode = new PriorityNode(targetNode, costToCurrent, estimatedCostToEnd);

				discoveredNeighborNodes.put(targetNodeKey, priorityNode);
				predecessorNodes.put(targetNodeKey, currentNode.getKey());

				if (discoveredNeighborNode == null) {
					priorityQueue.add(priorityNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					priorityQueue.remove(discoveredNeighborNode);
					priorityQueue.add(priorityNode);
				}

			}

		}

		return "No Path found";

	}

	private double calculateHeuristic(Node fromNode, Node toNode) {
		double deltaX = fromNode.getX() - toNode.getX();
		double deltaY = fromNode.getY() - toNode.getY();
		double euclideanDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return euclideanDistance / graph.getMaxSpeedLimit();
	}

	private String constructPath(HashMap<Long, Long> nodeLinks, long startNodeKey, long endNodeKey) {
		long currentKey = endNodeKey;
		String path = String.valueOf(endNodeKey);
		String list = String.valueOf(endNodeKey) + "]";
		while (currentKey != startNodeKey) {
			long predecessorNodeKey = nodeLinks.get(currentKey);
			path = predecessorNodeKey + " -> " + path;
			list = predecessorNodeKey + "," + list;
			currentKey = predecessorNodeKey;
		}
		System.out.println("[" + list);
		return path;
	}
}
