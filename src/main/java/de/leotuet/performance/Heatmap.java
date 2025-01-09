package de.leotuet.performance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.leotuet.GraphParser;
import de.leotuet.Heuristik;
import de.leotuet.PathExporter;
import de.leotuet.UnitCalculator;
import de.leotuet.datastructures.Graph;
import de.leotuet.datastructures.Node;

public class Heatmap {

	private static double angleBetweenNodesFlat(Node fromNode, Node toNode) {
		double fromLongitude = Math.toRadians(fromNode.getLongitude());
		double fromLatitude = Math.toRadians(fromNode.getLatitude());
		double toLongitude = Math.toRadians(toNode.getLongitude());
		double toLatitude = Math.toRadians(toNode.getLatitude());

		// Convert to Cartesian coordinates (using equirectangular projection)
		double fromX = fromLongitude * Math.cos(fromLatitude);
		double fromY = fromLatitude;
		double toX = toLongitude * Math.cos(toLatitude);
		double toY = toLatitude;

		double deltaX = toX - fromX;
		double deltaY = toY - fromY;
		double angle = Math.atan2(deltaY, deltaX);

		return (Math.toDegrees(angle) + 360) % 360;
	}

	private static List<Long> getHeatmapNodes(Graph graph, double radiusDeltaInKm, double angleAccuracyInKm, long centerNodeKey) {
		Node center = graph.getNode(centerNodeKey);
		HashMap<Double, HashMap<Double, Long>> heatmapNodes = new HashMap<>();

		for (Node node : graph) {
			double radius = Heuristik.estimateGreatCircleDistance(center, node);
			double radiusRange = Math.floor(radius / radiusDeltaInKm) * radiusDeltaInKm;

			double angle = angleBetweenNodesFlat(center, node);
			double circumference = 2.0 * Math.PI * radiusRange;
			double angleDelta = Math.floor(360.0 / (circumference / angleAccuracyInKm));
			double angleRange = Math.floor(angle / angleDelta) * angleDelta;

			if (heatmapNodes.get(radiusRange) == null) {
				heatmapNodes.put(radiusRange, new HashMap<>());
				heatmapNodes.get(radiusRange).put(angleRange, node.getKey());
			} else {
				Long anglePosition = heatmapNodes.get(radiusRange).get(angleRange);
				if (anglePosition == null) {
					heatmapNodes.get(radiusRange).put(angleRange, node.getKey());
				}
			}
		}

		return Arrays.asList(heatmapNodes.values().stream().flatMap(map -> map.values().stream()).toArray(Long[]::new));
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

		long centerNodeKey = 281230953l;
		List<Long> nodes = getHeatmapNodes(graph, 10, 5, centerNodeKey);

		ArrayList<String> results = new ArrayList<>();
		for (Long node : nodes) {
			var times = Benchmark.singleAStarRun(graph, centerNodeKey, node, 10);
			double averageTime = UnitCalculator.nanoToStandard(Benchmark.getTotalTime(times) / times.size());
			results.add("[" + node + "," + averageTime + "]");
		}

		PathExporter.saveToFile(PathExporter.HEATMAP_FILE_PATH, results.toString());
	}
}