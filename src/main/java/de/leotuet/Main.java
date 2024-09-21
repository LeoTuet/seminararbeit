package de.leotuet;

import java.io.IOException;

import de.leotuet.datastructures.Graph;

public class Main {

    public static void main(String[] args) throws IOException {
        Graph graph = GraphParser.csvToGraph("src/main/resources/graph.csv");

        AStar aStar = new AStar(graph);
        String result = aStar.run(802816696l, 34030327l);
        System.out.println(result);
    }
}