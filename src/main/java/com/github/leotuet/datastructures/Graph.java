package com.github.leotuet.datastructures;

import java.util.HashMap;

public class Graph {
	private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
	private int maxSpeedLimit = 0;

	public void putNode(int key, double x, double y) {
		graph.put(key, new Node(key, x, y));
	}

	public void addEdge(int fromKey, Node toNode, double length, int speedLimit) {
		Node fromNode = graph.get(fromKey);
		fromNode.addEdge(new Edge(length, speedLimit, toNode));
	}

	public void setMaxSpeedLimit(int speedLimit) {
		if (speedLimit > this.maxSpeedLimit) {
			this.maxSpeedLimit = speedLimit;
		}
	}

	public Node getNode(int key) {
		return graph.get(key);
	}

	public int getMaxSpeedLimit() {
		return maxSpeedLimit;
	}

}
