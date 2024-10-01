package de.leotuet.datastructures;

public class Edge {
	private double cost;
	private Node targetNode;

	/**
	 * Constructor for Edge an wich points to a Node
	 * 
	 * @param length of the Road in meters
	 * @param speedLimit on the Road in meters per second
	 * @param targetNode to which the edge points
	 */
	public Edge(double length, double speedLimit, Node targetNode) {
		this.cost = length / speedLimit;
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
