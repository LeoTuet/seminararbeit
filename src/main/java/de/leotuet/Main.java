package de.leotuet;

import java.io.IOException;

import de.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

        // small dataset
        var startNodeKey = 2090684017l;
        var endNodeKey = 60127233l;

        // big dataset
        // var startNodeKey = 21005407l;
        // var endNodeKey = 271985638l;

        var result = AStar.run(graph, startNodeKey, endNodeKey, false);
        System.out.println(result);
    }

}