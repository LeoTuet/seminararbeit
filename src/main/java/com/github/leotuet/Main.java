package com.github.leotuet;

import java.io.IOException;

import com.github.leotuet.datastructures.Graph;

public class Main {
    public static void main(String[] args) throws IOException {
        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.csvToGraph("/graph.csv");

        AStar aStar = new AStar(graph);
        long startTime = System.nanoTime();
        String result = aStar.run(245477l, 2963494464l);
        long stopTime = System.nanoTime();

        System.out.println(result);
        System.out.println("Runtime: " + (stopTime - startTime) / Math.pow(10, 9) + "s");
    }
}