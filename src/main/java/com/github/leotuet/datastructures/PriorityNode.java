package com.github.leotuet.datastructures;

public class PriorityNode implements Comparable<PriorityNode> {
	private Node node;
	private double costToNode;
	private double estimatedCostToEnd;

	/**
	 * Constructor for a PriorityNode which is a wrapper for a Node to save
	 * information which is used for the A*-ALgorithm
	 * 
	 * @param node          for which the data is
	 * @param costToNode    the total cost to this node from the start
	 * @param estimatedCost the estimated cost to the end
	 */

	public PriorityNode(Node node, double costToNode, double estimatedCost) {
		this.node = node;
		this.costToNode = costToNode;
		this.estimatedCostToEnd = estimatedCost;
	}

	/**
	 * @return the actual Node object
	 */
	public Node getNode() {
		return this.node;
	}

	/**
	 * @return the total cost to this node from the start
	 */
	public double getCostToNode() {
		return this.costToNode;
	};

	/**
	 * @return the estimated cost to the end
	 */
	public double getEstimatedCostToEnd() {
		return this.estimatedCostToEnd;
	}

	@Override
	public int compareTo(PriorityNode node) {
		return Double.compare(estimatedCostToEnd, node.estimatedCostToEnd);
	}
}
