package com.pc.globalpos.ratefeed.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {
	
	public static void writeStringToFile(String string, Path path) throws IOException {
		Files.write(path, string.getBytes(), StandardOpenOption.CREATE);
	}

}
