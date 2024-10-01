package de.leotuet;

import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class Heuristik {
	// https://www.mathsjournal.com/pdf/2021/vol6issue5/PartA/6-4-19-973.pdf
	public static double estimateGreatCircleDistance(Node fromNode, Node toNode) {
		double halfDeltaLatitude = Math.toRadians(toNode.getLatitude() - fromNode.getLatitude()) / 2;
		double halfDeltaLongitude = Math.toRadians(toNode.getLongitude() - fromNode.getLongitude()) / 2;

		double fromLatitude = Math.toRadians(fromNode.getLatitude());
		double toLatitude = Math.toRadians(toNode.getLatitude());

		double earthRadius = 6371;
		double underSquareRoot = Math.pow(Math.sin(halfDeltaLatitude), 2)
				+ Math.cos(fromLatitude) * Math.cos(toLatitude) * Math.pow(halfDeltaLongitude, 2);

		return 2 * earthRadius * Math.asin(Math.sqrt(underSquareRoot));
	}

	public static double estimateTime(Graph graph, Node fromNode, Node toNode) {
		double greatCircleDistance = estimateGreatCircleDistance(fromNode, toNode);
		return UnitCalculator.kilometersToMeters(greatCircleDistance) / graph.getMaxSpeedLimit();
	}
}
