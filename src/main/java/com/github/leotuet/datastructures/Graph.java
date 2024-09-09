package com.github.leotuet.datastructures;

import java.util.HashMap;

public class Graph {
	private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();

	public void putNode(int key, float x, float y) {
		graph.put(key, new Node(key, x, y));
	}

	public Node getNode(int key) {
		return graph.get(key);
	}

	public void addEdge(int fromKey, Node toNode, float length, int speedLimit) {
		Node fromNode = graph.get(fromKey);
		fromNode.addEdge(new Edge(length, speedLimit, toNode));
	}

}
