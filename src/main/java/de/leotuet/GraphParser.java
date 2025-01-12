package de.leotuet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.leotuet.datastructures.Graph;

// https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVParser.html
// https://stackoverflow.com/questions/6698354/where-to-get-utf-8-string-literal-in-java
public class GraphParser {

	/**
	 * @param path String path to file location in resources folder
	 * @throws IOException when file is not Found at provided path
	 * @return the parsed Graph
	 */
	public static Graph csvToGraph(String path) throws IOException {
		Graph graph = new Graph();

		File csvFile = new File(path);
		CSVParser csvParser = CSVParser.parse(csvFile, StandardCharsets.UTF_8, CSVFormat.DEFAULT);

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

					double speedLimitInMetersPerSecond = UnitCalculator.kilometersPerHourToMetersPerSecond(speedLimit);
					graph.addEdge(fromKey, graph.getNode(toKey), length, speedLimitInMetersPerSecond);
					graph.setMaxSpeedLimit(speedLimitInMetersPerSecond);

					break;
				}

				default: {
					System.out.println("Unexpected record of type: " + type);
				}
			}
		}

		return graph;
	}

}
