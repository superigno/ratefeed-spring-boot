package com.pc.globalpos.ratefeed.source;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public interface RateSource {
	
	public Object getFeed(URL url) throws IOException;
	public Object getFeed(Path path) throws IOException;
	public void saveFeed(Object feed, Path path) throws IOException;

}
