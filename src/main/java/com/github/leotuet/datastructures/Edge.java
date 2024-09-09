package com.github.leotuet.datastructures;

public class Edge {
	private float cost;
	private Node node;

	/**
	 * Constructor for Edge an wich points to a Node
	 * 
	 * @param length     of the Road in meters
	 * @param speedLimit on the Road in kilometers per hour (km/h)
	 * @param Node       to which the edge points
	 */
	public Edge(float length, int speedLimit, Node node) {
		// converts km/h to m/s before calculating the cost
		this.cost = length / (speedLimit / 3.6f);
	}

	/**
	 * @return the node to which the edge points to
	 */
	public Node getNode() {
		return this.node;
	}

	/**
	 * @return the cost which it takes to traverse over edge to the node
	 */
	public float getCost() {
		return cost;
	}
}
