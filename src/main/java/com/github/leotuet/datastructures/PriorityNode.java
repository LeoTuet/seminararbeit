package com.github.leotuet.datastructures;

public class PriorityNode implements Comparable<PriorityNode> {
	private Node node;
	private double costToNode;
	private double estimatedCostToEnd;

	public PriorityNode(Node node, double costToNode, double estimatedCost) {
		this.node = node;
		this.costToNode = costToNode;
		this.estimatedCostToEnd = estimatedCost;
	}

	public Node getNode() {
		return this.node;
	}

	public double getCostToNode() {
		return this.costToNode;
	};

	public double getEstimatedCostToEnd() {
		return this.estimatedCostToEnd;
	}

	@Override
	public int compareTo(PriorityNode node) {
		return Double.compare(estimatedCostToEnd, node.estimatedCostToEnd);
	}
}
