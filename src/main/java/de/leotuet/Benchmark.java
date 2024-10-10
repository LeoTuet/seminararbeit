package de.leotuet;

import java.util.ArrayList;

import de.leotuet.datastructures.Graph;

public class Benchmark {
	private static void printSingleRunResults(ArrayList<Long> times) {
		long totalNanoTime = 0;
		for (long time : times) {
			totalNanoTime = totalNanoTime + time;
		}
		double totalSeconds = UnitCalculator.nanoToStandard(totalNanoTime);
		System.out.println("First Runtime: " + UnitCalculator.nanoToStandard((times.get(0))));
		System.out.println("Last Runtime: " + UnitCalculator.nanoToStandard((times.get(times.size() - 1))));
		System.out.println("Total Runtime: " + totalSeconds);
		System.out.println("Average Runtime: " + totalSeconds / times.size());
	}

	public static void singleAStarRun(Graph graph, long startNodeKey, long endNodeKey) {
		double runs = 10000;

		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < runs; i++) {
			long time = System.nanoTime();
			AStar.run(graph, startNodeKey, endNodeKey, false);
			long runtime = System.nanoTime() - time;
			times.add(runtime);
		}

		printSingleRunResults(times);

	}
}
