package com.github.leotuet;

import java.io.IOException;

import com.github.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.csvToGraph("/graph.csv");

        AStar aStar = new AStar(graph);

        Benchmark benchmark = new Benchmark();
        benchmark.runBenchmark(aStar, 802816696l, 34030327l);

        String result = aStar.run(802816696l, 34030327l);
        System.out.println(result);

        benchmark.printAverage();
    }
}