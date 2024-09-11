package com.github.leotuet.datastructures;

import java.util.HashMap;

public class Graph {
	private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
	private int maxSpeedLimit = 0;

	/**
	 * Puts the Node into the Graph Map
	 * 
	 * @param key unique identifier for this Node
	 * @param x   -coordinate where the node is located
	 * @param y   -coordinate where the node is located
	 */
	public void putNode(int key, double x, double y) {
		graph.put(key, new Node(key, x, y));
	}

	/**
	 * Adds the Edge into the Edge List of the Node
	 * 
	 * @param fromNodeKey Node key from which the edges points
	 * @param toNode      Node to which the edge points
	 * @param length      of the Road in meters
	 * @param speedLimit  on the Road in kilometers per hour (km/h)
	 */
	public void addEdge(int fromNodeKey, Node toNode, double length, int speedLimit) {
		Node fromNode = graph.get(fromNodeKey);
		fromNode.addEdge(new Edge(length, speedLimit, toNode));
	}

	/**
	 * Sets the speed limit if it is the new maximum
	 * 
	 * @param speedLimit on the Road in kilometers per hour (km/h)
	 */
	public void setMaxSpeedLimit(int speedLimit) {
		if (speedLimit > this.maxSpeedLimit) {
			this.maxSpeedLimit = speedLimit;
		}
	}

	/**
	 * @param key of the Node in Graph
	 * @return the specified Node
	 */
	public Node getNode(int key) {
		return graph.get(key);
	}

	/**
	 * @return the max speed limit which exists in the graph in m/s
	 */
	public double getMaxSpeedLimit() {
		return maxSpeedLimit / 3.6;
	}

}
