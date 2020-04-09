package com.pc.globalpos.ratefeed.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
public class FileUtils {
	
	public static void writeStringToFile(String string, Path path) throws IOException {
		Path parentDir = path.getParent();
		
		if (!Files.exists(parentDir)) {
			Files.createDirectories(parentDir);
		}
		
		Files.write(path, string.getBytes(), StandardOpenOption.CREATE);
	}

}
