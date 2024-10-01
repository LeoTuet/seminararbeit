package de.leotuet.datastructures;

import java.util.ArrayList;

public class Node {
	private long key;
	private double longitude;
	private double latitude;
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * Constructor for a Node
	 * 
	 * @param key unique identifier for this Node
	 * @param latitude -coordinate where the node is located
	 * @param longitude -coordinate where the node is located
	 */
	public Node(long key, double latitude, double longitude) {
		this.key = key;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Adds Edge to Node
	 * 
	 * @param edge which is going to be added
	 */
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	/**
	 * @return longitude of Node
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @return latitude of Node
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @return the unique key of the Node
	 */
	public long getKey() {
		return this.key;
	}

	/**
	 * @return all Edges which are pointing from this node to others
	 */
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

}
