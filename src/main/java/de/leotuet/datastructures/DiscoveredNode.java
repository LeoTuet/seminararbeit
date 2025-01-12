package de.leotuet.datastructures;

public class DiscoveredNode implements Comparable<DiscoveredNode> {
	private final Node node;
	private final double totalCost;
	private final double estimatedTotalCost;
	private final long predecessorNodeKey;

	/**
	 * Constructs a DiscoveredNode which is a wrapper for a Node to save information which is used for the A*-ALgorithm.
	 * 
	 * @param node for which information is being stored
	 * @param totalCost from the start to this node
	 * @param estimatedTotalCostToEnd from the start node to the end node, passing through this node
	 * @param predecessorNodeKey of the node that precedes this one in the shortest path
	 */

	public DiscoveredNode(Node node, double totalCost, double estimatedTotalCostToEnd, long predecessorNodeKey) {
		this.node = node;
		this.totalCost = totalCost;
		this.estimatedTotalCost = estimatedTotalCostToEnd;
		this.predecessorNodeKey = predecessorNodeKey;
	}

	/**
	 * @return the actual Node object wrapped by this DiscoveredNode
	 */
	public Node getNode() {
		return this.node;
	}

	/**
	 * @return the total cost from the start node to this node
	 */
	public double getTotalCost() {
		return this.totalCost;
	};

	/**
	 * @return the estimated total cost from the start node to the end node, passing through this node
	 */
	public double getEstimatedTotalCostToEnd() {
		return this.estimatedTotalCost;
	}

	/**
	 * @return the key of the node that precedes this one in the shortest path
	 */
	public long getPredecessorNodeKey() {
		return this.predecessorNodeKey;
	}

	/**
	 * @param node to compare to
	 * @return whether this node's estimated total cost is smaller, equal, or larger
	 */
	@Override
	public int compareTo(DiscoveredNode node) {
		return Double.compare(this.estimatedTotalCost, node.estimatedTotalCost);
	}

}
