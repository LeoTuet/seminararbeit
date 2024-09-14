package com.github.leotuet.datastructures;

public class DiscoveredNode implements Comparable<DiscoveredNode> {
	private Node node;
	private double totalCost;
	private double estimatedTotalCost;
	private long predecessorNodeKey;

	/**
	 * Constructor for a DiscoveredNode which is a wrapper for a Node to save
	 * information which is used for the A*-ALgorithm
	 * 
	 * @param node               for which the data is
	 * @param totalCost          the total cost from the start to this node
	 * @param estimatedTotalCostToEnd the estimated cost from the start to the end
	 */

	public DiscoveredNode(Node node, double totalCost, double estimatedTotalCostToEnd, long predecessorNodeKey) {
		this.node = node;
		this.totalCost = totalCost;
		this.estimatedTotalCost = estimatedTotalCostToEnd;
		this.predecessorNodeKey = predecessorNodeKey;
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
	public double getEstimatedTotalCostToEnd() {
		return this.estimatedTotalCost;
	}

	/**
	 * @return the key of the node which precedes this one on the current discovered shortest path 
	 */
	public long getPredecessorNodeKey() {
		return this.predecessorNodeKey;
	}

	@Override
	public int compareTo(DiscoveredNode node) {
		return Double.compare(estimatedTotalCost, node.estimatedTotalCost);
	}
}
