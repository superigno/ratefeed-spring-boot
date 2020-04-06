package com.pc.globalpos.ratefeed.source;

import java.io.IOException;
import java.nio.file.Path;

public interface RateSource<T> {
	
	public T getFeed(final String url) throws IOException;
	public void parse() throws IOException;
	public void saveToFile(final Path path) throws IOException;

}
