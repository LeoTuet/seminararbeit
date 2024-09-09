package com.github.leotuet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.github.leotuet.datastructures.Graph;

// https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVParser.html
// https://stackoverflow.com/questions/6698354/where-to-get-utf-8-string-literal-in-java
public class GraphParser {

	public Graph csvToGraph(String path) throws IOException {
		Graph graph = new Graph();

		InputStream csvStream = getClass().getResourceAsStream(path);
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(";").build();
		CSVParser csvParser = CSVParser.parse(csvStream, StandardCharsets.UTF_8, csvFormat);

		for (CSVRecord record : csvParser) {
			String[] values = record.values();
			String type = values[0];
			System.out.println(type);
			switch (type) {
				case "n": {
					int key = Integer.parseInt(values[1]);
					float x = Float.parseFloat(values[2]);
					float y = Float.parseFloat(values[3]);
					graph.putNode(key, x, y);
					break;
				}

				case "e": {
					int fromKey = Integer.parseInt(values[1]);
					int toKey = Integer.parseInt(values[2]);
					float length = Float.parseFloat(values[3]);
					int speedLimit = Integer.parseInt(values[4]);
					graph.addEdge(fromKey, graph.getNode(toKey), length, speedLimit);
					break;
				}
			}

		}

		return graph;
	}

}
