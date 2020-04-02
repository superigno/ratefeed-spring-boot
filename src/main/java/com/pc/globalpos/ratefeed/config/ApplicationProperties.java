package com.pc.globalpos.ratefeed.config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties {
	
	@Value("${ratefeed.source}")
	private String ratefeedSource;

	public String getRatefeedSource() {
		return ratefeedSource;
	}

	public void setRatefeedSource(String ratefeedSource) {
		this.ratefeedSource = ratefeedSource;
	}
	
}
