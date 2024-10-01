package de.leotuet.datastructures;

import java.util.HashMap;

public class Graph {
	private HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	private double maxSpeedLimit = 0;

	/**
	 * Puts the Node into the Graph Map
	 * 
	 * @param key unique identifier for this Node
	 * @param latitude where the node is located
	 * @param longitude where the node is located
	 */
	public void putNode(long key, double latitude, double longitude) {
		nodes.put(key, new Node(key, latitude, longitude));
	}

	/**
	 * Adds the Edge into the Edge List of the Node
	 * 
	 * @param fromNodeKey Node key from which the edges points
	 * @param toNode Node to which the edge points
	 * @param length of the Road in meters
	 * @param speedLimit on the Road in meters per second
	 */
	public void addEdge(long fromNodeKey, Node toNode, double length, double speedLimit) {
		Node fromNode = nodes.get(fromNodeKey);
		fromNode.addEdge(new Edge(length, speedLimit, toNode));
	}

	/**
	 * Sets the speed limit if it is the new maximum
	 * 
	 * @param speedLimit in meters per second
	 */
	public void setMaxSpeedLimit(double speedLimit) {
		if (speedLimit > this.maxSpeedLimit) {
			this.maxSpeedLimit = speedLimit;
		}
	}

	/**
	 * @param key of the Node in Graph
	 * @return the specified Node
	 */
	public Node getNode(long key) {
		return nodes.get(key);
	}

	/**
	 * @return the max speed limit which exists in the graph in meters per second
	 */
	public double getMaxSpeedLimit() {
		return maxSpeedLimit;
	}

}
