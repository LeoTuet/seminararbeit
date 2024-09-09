package com.github.leotuet.datastructures;

public class Edge {
	private double cost;
	private Node targetNode;

	/**
	 * Constructor for Edge an wich points to a Node
	 * 
	 * @param length     of the Road in meters
	 * @param speedLimit on the Road in kilometers per hour (km/h)
	 * @param Node       to which the edge points
	 */
	public Edge(double length, int speedLimit, Node targetNode) {
		// converts km/h to m/s before calculating the cost
		this.cost = length / (speedLimit / 3.6);
		this.targetNode = targetNode;
	}

	/**
	 * @return the node to which the edge points to
	 */
	public Node getTargetNode() {
		return this.targetNode;
	}

	/**
	 * @return the cost which it takes to traverse over edge to the node
	 */
	public double getCost() {
		return this.cost;
	}

}
