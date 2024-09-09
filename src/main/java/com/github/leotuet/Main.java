package com.github.leotuet;

import java.io.IOException;

import com.github.leotuet.datastructures.Graph;

public class Main {
    public static void main(String[] args) throws IOException {
        GraphParser graphParser = new GraphParser();
        Graph graph = graphParser.csvToGraph("/Graph.csv");

        AStar aStar = new AStar();
        aStar.run(graph);
    }
}