package de.leotuet;

import java.io.IOException;

import de.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

        var start = System.nanoTime();
        // kleine
        String result = AStar.run(graph, 2090684017l, 60127233l, true);
        // große
        // 21005407 -> München Stachus, 271985638 -> Rothenburg ob der Tauber Kirchenplatz
        // String result = AStar.run(graph, 21005407l, 271985638l, true);
        System.out.println("Runtime: " + (System.nanoTime() - start) / Math.pow(10, 9));
        System.out.println(result);
    }
}