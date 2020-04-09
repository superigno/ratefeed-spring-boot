package com.pc.globalpos.ratefeed.source;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
public interface RateSource<T> {
	
	public T getFeed(final String url) throws IOException;
	public String parse(T feed) throws IOException;
	public void saveToFile(String strFeed, final Path path) throws IOException;

}
