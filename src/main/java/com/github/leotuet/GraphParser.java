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

	/**
	 * @param path String path to file location in resources folder
	 * @throws IOException when file is not Found at provided path
	 * @return the parsed Graph
	 */
	public Graph csvToGraph(String path) throws IOException {
		// Method is non static because this.getClass() is not-static and therefore can
		// also not be called in the main method therefore the param must be a String
		// path and cannot be the InputStream
		Graph graph = new Graph();

		InputStream csvStream = this.getClass().getResourceAsStream(path);
		CSVParser csvParser = CSVParser.parse(csvStream, StandardCharsets.UTF_8, CSVFormat.DEFAULT);

		for (CSVRecord record : csvParser) {
			String[] values = record.values();
			String type = values[0];
			switch (type) {
				// Node Record
				case "n": {
					long key = Long.parseLong(values[1]);
					double x = Double.parseDouble(values[2]);
					double y = Double.parseDouble(values[3]);

					graph.putNode(key, x, y);

					break;
				}

				// Edge Record
				case "e": {
					long fromKey = Long.parseLong(values[1]);
					long toKey = Long.parseLong(values[2]);
					double length = Double.parseDouble(values[3]);
					int speedLimit = Integer.parseInt(values[4]);

					graph.addEdge(fromKey, graph.getNode(toKey), length, speedLimit);
					graph.setMaxSpeedLimit(speedLimit);

					break;
				}
			}

		}

		return graph;
	}

}
