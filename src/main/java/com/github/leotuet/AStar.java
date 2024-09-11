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

	public String run(int startNodeKey, int endNodeKey) {
		PriorityQueue<PriorityNode> priorityQueue = new PriorityQueue<>();
		HashMap<Integer, Integer> predecessorNodes = new HashMap<Integer, Integer>();
		HashSet<Integer> shortestPathDiscovered = new HashSet<Integer>();
		HashMap<Integer, PriorityNode> discoveredNodes = new HashMap<Integer, PriorityNode>();

		Node startNode = graph.getNode(startNodeKey);
		Node endNode = graph.getNode(endNodeKey);

		PriorityNode startPriorityNode = new PriorityNode(startNode, 0.0, 0.0);
		priorityQueue.add(startPriorityNode);
		discoveredNodes.put(startNodeKey, startPriorityNode);

		while (!priorityQueue.isEmpty()) {
			PriorityNode currentPriorityNode = priorityQueue.poll();
			Node currentNode = currentPriorityNode.getNode();

			if (currentNode.getKey() == endNodeKey) {
				String path = constructPath(predecessorNodes, startNodeKey, endNodeKey);
				String time = new DecimalFormat("#.#").format(currentPriorityNode.getCostToNode() / 60) + "min";
				return path + " in " + time;
			}

			ArrayList<Edge> edges = currentNode.getEdges();
			shortestPathDiscovered.add(currentNode.getKey());

			for (Edge edge : edges) {
				Node targetNode = edge.getTargetNode();
				int targetNodeKey = targetNode.getKey();

				if (shortestPathDiscovered.contains(targetNodeKey)) {
					continue;
				}

				double costToCurrent = currentPriorityNode.getEstimatedCostToEnd() + edge.getCost();
				PriorityNode discoveredNode = discoveredNodes.get(targetNodeKey);

				// Check if Node and more coste efficient way to Node is already discoverd
				if (discoveredNode != null && costToCurrent > discoveredNode.getCostToNode()) {
					continue;
				}

				double estimatedCostToEnd = costToCurrent + calculateHeuristic(targetNode, endNode);
				PriorityNode priorityNode = new PriorityNode(targetNode, costToCurrent, estimatedCostToEnd);

				discoveredNodes.put(targetNodeKey, priorityNode);
				predecessorNodes.put(targetNodeKey, currentNode.getKey());

				if (discoveredNode == null) {
					priorityQueue.add(priorityNode);
				} else {
					// The Node was already discovered but this path is more cost efficient
					priorityQueue.remove(discoveredNode);
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

	private String constructPath(HashMap<Integer, Integer> nodeLinks, int startNodeKey, int endNodeKey) {
		int currentKey = endNodeKey;
		String path = String.valueOf(endNodeKey);
		while (currentKey != startNodeKey) {
			int predecessorNodeKey = nodeLinks.get(currentKey);
			path = predecessorNodeKey + " -> " + path;
			currentKey = predecessorNodeKey;
		}
		return path;
	}
}
