package de.leotuet.performance;

import java.io.IOException;

import de.leotuet.GraphParser;
import de.leotuet.datastructures.Graph;

public class Performance {
	public static void main(String[] args) throws IOException {
		Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

		// small dataset
		// Benchmark.singleAStarRun(graph, 2090684017l, 60127233l);
		Benchmark.singleAStarTreeSetRun(graph, 2090684017l, 60127233l);
		// Benchmark.singleAStarFibonacciHeapRun(graph, 2090684017l, 60127233l);

		// big dataset
		// Benchmark.singleAStarRun(graph, 21005407l, 271985638l);
		// Benchmark.singleAStarTreeSetRun(graph, 21005407l, 271985638l);
		// Benchmark.singleAStarFibonacciHeapRun(graph, 21005407l, 271985638l);
	}
}
