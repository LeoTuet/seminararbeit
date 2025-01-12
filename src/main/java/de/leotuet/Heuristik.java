package de.leotuet;

import de.leotuet.datastructures.Node;

public class Heuristik {

	/**
	 * Calculates the great circle distance between two nodes, {@code fromNode} and {@code toNode}. The great circle distance is the
	 * shortest distance between two points on the surface of a sphere.
	 *
	 * @param fromNode the starting node
	 * @param toNode the destination node
	 * @return the distance in kilometers
	 */
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

	/**
	 * Estimates the time required to travel in a straight line from {@code fromNode} to {@code toNode}. This estimation uses the
	 * great circle distance and the {@code speedLimit}.
	 *
	 * @param speedLimit the speed limit in meters per second
	 * @param fromNode the starting node
	 * @param toNode the destination node
	 * @return the estimated travel time in seconds
	 */
	public static double estimateTime(double speedLimit, Node fromNode, Node toNode) {
		double greatCircleDistance = estimateGreatCircleDistance(fromNode, toNode);
		return UnitCalculator.kilometersToMeters(greatCircleDistance) / speedLimit;
	}

}
