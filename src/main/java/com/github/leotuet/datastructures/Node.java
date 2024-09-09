package com.github.leotuet.datastructures;

import java.util.ArrayList;

public class Node {
	private int key;
	private double x;
	private double y;
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * Constructor for a Node
	 * 
	 * @param x -coordinate where the node is located
	 * @param y -coordinate where the node is located
	 */
	public Node(int key, double x, double y) {
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

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public int getKey() {
		return this.key;
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

}
