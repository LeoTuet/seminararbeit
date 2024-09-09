package com.github.leotuet;

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import com.github.leotuet.datastructures.Edge;
import com.github.leotuet.datastructures.Graph;
import com.github.leotuet.datastructures.Node;
import com.github.leotuet.datastructures.PriorityNode;

public class AStar {
	private Graph graph;

	public AStar(Graph graph) {
		this.graph = graph;
	}

	public void run(int startNodeKey, int endNodeKey) {
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
				System.out.println("Found path");
				return;
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
				double estimatedCostToEnd = costToCurrent + calculateHeuristic(targetNode, endNode);

				PriorityNode discoveredNode = discoveredNodes.get(targetNodeKey);
				if (discoveredNode == null) {
					PriorityNode priorityNode = new PriorityNode(targetNode, costToCurrent, estimatedCostToEnd);
					discoveredNodes.put(targetNodeKey, priorityNode);
					predecessorNodes.put(targetNodeKey, currentNode.getKey());
					priorityQueue.add(priorityNode);
				} else if (costToCurrent < discoveredNode.getCostToNode()) {
					PriorityNode priorityNode = new PriorityNode(targetNode, costToCurrent, estimatedCostToEnd);
					discoveredNodes.put(targetNodeKey, priorityNode);
					predecessorNodes.put(targetNodeKey, currentNode.getKey());
					priorityQueue.remove(discoveredNode);
					priorityQueue.add(priorityNode);
				}

			}

		}

		System.out.println("Nothing found");
		return;

	}

	private double calculateHeuristic(Node fromNode, Node toNode) {
		double deltaX = fromNode.getX() - toNode.getX();
		double deltaY = fromNode.getY() - toNode.getY();
		double euclideanDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return euclideanDistance / (graph.getMaxSpeedLimit() / 3.6);
	}
}
