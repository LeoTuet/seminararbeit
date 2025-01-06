package de.leotuet.datastructures;

public class Edge {
	private final double cost;
	private final Node targetNode;

	/**
	 * Constructs an Edge that points to a given target Node.
	 * 
	 * @param length of the road in meters
	 * @param speedLimit on the road in meters per second
	 * @param targetNode to which this edge points
	 */
	public Edge(double length, double speedLimit, Node targetNode) {
		this.cost = length / speedLimit;
		this.targetNode = targetNode;
	}

	/**
	 * @return the node to which this edge points
	 */
	public Node getTargetNode() {
		return this.targetNode;
	}

	/**
	 * @return the cost which it takes to traverse over edge to the {@code targetNode}
	 */
	public double getCost() {
		return this.cost;
	}

}
