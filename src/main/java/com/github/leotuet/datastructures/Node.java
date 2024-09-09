package com.github.leotuet.datastructures;

import java.util.ArrayList;

public class Node {
	private int key;
	private float x;
	private float y;
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	/**
	 * Constructor for a Node
	 * 
	 * @param x -coordinate where the node is located
	 * @param y -coordinate where the node is located
	 */
	public Node(int key, float x, float y) {
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

	public void getLocation() {
		System.out.println(this.key + ": x: " + this.x + ", y: " + this.y);
	}

}
