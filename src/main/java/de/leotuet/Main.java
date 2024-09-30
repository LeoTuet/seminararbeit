package de.leotuet;

import java.io.IOException;

import de.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

        String result = AStar.run(graph, 2090684017l, 5505532744l);
        // String result = AStar.run(graph, 1230460387l, 271985705l);
        System.out.println(result);
    }
}