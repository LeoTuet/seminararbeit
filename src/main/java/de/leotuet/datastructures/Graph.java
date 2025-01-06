package de.leotuet.datastructures;

import java.util.HashMap;
import java.util.Iterator;

public class Graph implements Iterable<Node> {
	private final HashMap<Long, Node> nodes = new HashMap<>();
	private double maxSpeedLimit = 0;

	/**
	 * Puts information about a node into the graph map as an node object.
	 * 
	 * @param key unique identifier for this Node
	 * @param latitude coordinate where the node is located
	 * @param longitude coordinate where the node is located
	 */
	public void putNode(long key, double latitude, double longitude) {
		nodes.put(key, new Node(key, latitude, longitude));
	}

	/**
	 * Adds information about an edge into the edge List of the given node as an edge object.
	 * 
	 * @param fromNodeKey from which the edges points
	 * @param toNode to which the edge points
	 * @param length of the road in meters
	 * @param speedLimit on the road in meters per second
	 */
	public void addEdge(long fromNodeKey, Node toNode, double length, double speedLimit) {
		Node fromNode = nodes.get(fromNodeKey);
		fromNode.addEdge(new Edge(length, speedLimit, toNode));
	}

	/**
	 * Sets the speed limit if it is the new maximum.
	 * 
	 * @param speedLimit in meters per second
	 */
	public void setMaxSpeedLimit(double speedLimit) {
		if (speedLimit > this.maxSpeedLimit) {
			this.maxSpeedLimit = speedLimit;
		}
	}

	/**
	 * @param key of the node in the graph
	 * @return the specified node
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

	/**
	 * Provides an iterator for iterating over nodes in the graph.
	 * 
	 * @return the iterator of the graph map
	 */
	@Override
	public Iterator<Node> iterator() {
		return this.nodes.values().iterator();
	}

}
