package de.leotuet.datastructures;

import java.util.ArrayList;

public class Node {
	private long key;
	private double x;
	private double y;
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * Constructor for a Node
	 * 
	 * @param key unique identifier for this Node
	 * @param x   -coordinate where the node is located
	 * @param y   -coordinate where the node is located
	 */
	public Node(long key, double x, double y) {
		this.key = key;
		this.x = x;
		this.y = y;
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
	 * @return x location of Node
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return y location of Node
	 */
	public double getY() {
		return this.y;
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
