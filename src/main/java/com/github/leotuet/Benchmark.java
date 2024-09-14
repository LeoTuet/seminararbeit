package com.github.leotuet;

import java.util.ArrayList;

public class Benchmark {
	ArrayList<Long> times = new ArrayList<Long>();

	interface Runnable<T> {
		T run(long a, long b);
	}

	public <T> void runBenchmark(Runnable<T> Runnable, long a, long b) {
		for (int i = 0; i < 1000; i++) {
			long startTime = System.nanoTime();
			Runnable.run(a, b);
			times.add(System.nanoTime() - startTime);
		}
	}

	public long getAverage() {
		long sum = 0;
		for (long time : times) {
			sum += time;
		}
		return sum / times.size();
	}

	public void printAverage() {
		System.out.println("Average runtime: " + this.getAverage() / Math.pow(10, 9) + "s");
	}
}
