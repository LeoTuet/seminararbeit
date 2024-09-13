package com.github.leotuet.datastructures;

public class PriorityNode implements Comparable<PriorityNode> {
	private Node node;
	private double totalCost;
	private double estimatedTotalCost;

	/**
	 * Constructor for a PriorityNode which is a wrapper for a Node to save
	 * information which is used for the A*-ALgorithm
	 * 
	 * @param node               for which the data is
	 * @param totalCost          the total cost from the start to this node
	 * @param estimatedTotalCost the estimated cost from the start to the end
	 */

	public PriorityNode(Node node, double totalCost, double estimatedTotalCost) {
		this.node = node;
		this.totalCost = totalCost;
		this.estimatedTotalCost = estimatedTotalCost;
	}

	/**
	 * @return the actual Node object
	 */
	public Node getNode() {
		return this.node;
	}

	/**
	 * @return the total cost from the start to this node
	 */
	public double getTotalCost() {
		return this.totalCost;
	};

	/**
	 * @return the estimated cost from the start to the end
	 */
	public double getEstimatedTotalCost() {
		return this.estimatedTotalCost;
	}

	@Override
	public int compareTo(PriorityNode node) {
		return Double.compare(estimatedTotalCost, node.estimatedTotalCost);
	}
}
