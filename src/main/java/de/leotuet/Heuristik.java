package de.leotuet;

import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class Heuristik {
	public static double estimateDistance(Node fromNode, Node toNode) {
		double deltaX = fromNode.getX() - toNode.getX();
		double deltaY = fromNode.getY() - toNode.getY();
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	public static double estimateTime(Graph graph, Node fromNode, Node toNode) {
		double euclideanDistance = estimateDistance(fromNode, toNode);
		return euclideanDistance / graph.getMaxSpeedLimit();
	}
}
