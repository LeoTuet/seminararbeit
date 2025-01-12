package de.leotuet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class StringExporter {
	public final static String PATHS_FILE_PATH = "./src/main/resources/paths.json";
	public final static String ROUTE_FILE_PATH = "./src/main/resources/route.json";
	public final static String HEATMAP_FILE_PATH = "./src/main/resources/heatmap.json";

	public static void saveToFile(String path, String content) {
		try (PrintWriter out = new PrintWriter(path)) {
			out.println(content);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
