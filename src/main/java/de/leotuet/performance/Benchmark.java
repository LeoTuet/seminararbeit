package de.leotuet.performance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;

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

		double totalSeconds = UnitCalculator.nanosecondsToSeconds(totalNanoTime);
		System.out.println("First Runtime: " + UnitCalculator.nanosecondsToSeconds((times.get(0))));
		System.out.println("Last Runtime: " + UnitCalculator.nanosecondsToSeconds((times.get(times.size() - 1))));
		System.out.println("Total Runtime: " + totalSeconds);
		System.out.println("Average Runtime: " + totalSeconds / times.size());
	}

	public static ArrayList<Long> run(int runs, Supplier<ArrayList<Long>> aStar) {
		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			long time = System.nanoTime();
			ArrayList<Long> result = aStar.get();
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

	public static void main(String[] args) throws IOException {
		Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

		// small dataset
		var startNodeKey = 2090684017l;
		var endNodeKey = 60127233l;
		var runs = 1000000;

		// big dataset
		// var startNodeKey = 21005407l;
		// var endNodeKey = 271985638l;
		// var runs = 10000;

		var times = Benchmark.run(runs, () -> AStar.run(graph, startNodeKey, endNodeKey, false));
		// var times = Benchmark.run(1000000, () -> AStarTreeSet.run(graph, startNodeKey, endNodeKey));
		// var times = Benchmark.run(1000000, () -> AStarFibonacciHeap.run(graph, startNodeKey, endNodeKey));

		Benchmark.printSingleRunResults(times);
	}

}
