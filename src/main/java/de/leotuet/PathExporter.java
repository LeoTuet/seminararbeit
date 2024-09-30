package de.leotuet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PathExporter {
	final static String EXPLORED_PATHS_FILE_PATH = "./src/main/resources/explored-paths.json";
	final static String ROUTE_FILE_PATH = "./src/main/resources/route.json";

	public static void saveToFile(String path, String content) {
		try (PrintWriter out = new PrintWriter(path)) {
			out.println(content);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
