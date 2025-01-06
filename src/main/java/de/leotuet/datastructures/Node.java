package de.leotuet.datastructures;

import java.util.ArrayList;

public class Node {
	private final long key;
	private final double longitude;
	private final double latitude;
	private final ArrayList<Edge> edges = new ArrayList<>();

	/**
	 * Constructs a Node.
	 * 
	 * @param key unique identifier for this node
	 * @param latitude coordinate where the node is located
	 * @param longitude coordinate where the node is located
	 */
	public Node(long key, double latitude, double longitude) {
		this.key = key;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Adds an edge to node.
	 * 
	 * @param edge which is going to be added
	 */
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	/**
	 * @return longitude of node
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @return latitude of node
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @return the unique key of the node
	 */
	public long getKey() {
		return this.key;
	}

	/**
	 * @return all edges which are pointing from this node to others
	 */
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

}
