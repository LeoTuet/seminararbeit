package de.leotuet.performance;

import java.io.IOException;
import java.util.ArrayList;

import de.leotuet.AStar;
import de.leotuet.GraphParser;
import de.leotuet.UnitCalculator;
import de.leotuet.datastructures.Graph;

public class Benchmark {
	public static long getTotalTime(ArrayList<Long> times) {
		long totalNanoTime = 0;
		for (long time : times) {
			totalNanoTime = totalNanoTime + time;
		}

		return totalNanoTime;
	}

	public static void printSingleRunResults(ArrayList<Long> times) {
		long totalNanoTime = getTotalTime(times);

		double totalSeconds = UnitCalculator.nanoToStandard(totalNanoTime);
		System.out.println("First Runtime: " + UnitCalculator.nanoToStandard((times.get(0))));
		System.out.println("Last Runtime: " + UnitCalculator.nanoToStandard((times.get(times.size() - 1))));
		System.out.println("Total Runtime: " + totalSeconds);
		System.out.println("Average Runtime: " + totalSeconds / times.size());
	}

	public static ArrayList<Long> singleAStarRun(Graph graph, long startNodeKey, long endNodeKey, int runs) {
		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			long time = System.nanoTime();
			ArrayList<Long> result = AStar.run(graph, startNodeKey, endNodeKey, false);
			long runtime = System.nanoTime() - time;
			times.add(runtime);
			if (result == null) {
				times.clear();
				times.add(-1l);
				return times;
			}
		}

		return times;
	}

	public static ArrayList<Long> singleAStarTreeSetRun(Graph graph, long startNodeKey, long endNodeKey) {
		double runs = 1000000;

		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			long time = System.nanoTime();
			AStarTreeSet.run(graph, startNodeKey, endNodeKey);
			long runtime = System.nanoTime() - time;
			times.add(runtime);
		}

		return times;
	}

	public static ArrayList<Long> singleAStarFibonacciHeapRun(Graph graph, long startNodeKey, long endNodeKey) {
		double runs = 1000000;

		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			long time = System.nanoTime();
			AStarFibonacciHeap.run(graph, startNodeKey, endNodeKey);
			long runtime = System.nanoTime() - time;
			times.add(runtime);
		}

		return times;
	}

	public static void main(String[] args) throws IOException {
		Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

		// small dataset
		// var times = Benchmark.singleAStarRun(graph, 2090684017l, 60127233l, 1000000);
		var times = Benchmark.singleAStarTreeSetRun(graph, 2090684017l, 60127233l);
		// var times = Benchmark.singleAStarFibonacciHeapRun(graph, 2090684017l, 60127233l);

		// big dataset
		// var times = Benchmark.singleAStarRun(graph, 21005407l, 271985638l, 10000);
		// var times = Benchmark.singleAStarTreeSetRun(graph, 21005407l, 271985638l);
		// var times = Benchmark.singleAStarFibonacciHeapRun(graph, 21005407l, 271985638l);

		Benchmark.printSingleRunResults(times);
	}
}
