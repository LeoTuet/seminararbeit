package de.leotuet;

import java.io.IOException;

import de.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

        // small dataset
        // String result = AStar.run(graph, 2090684017l, 60127233l, true);

        // big dataset
        String result = AStar.run(graph, 21005407l, 271985638l, true);

        System.out.println(result);

    }
}