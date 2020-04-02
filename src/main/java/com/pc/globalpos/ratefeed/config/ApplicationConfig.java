package com.pc.globalpos.ratefeed.config;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;

@Configuration
public class ApplicationConfig {
	
	@Autowired
    private Environment env;
	
	@Bean
	public Jaxb2Marshaller getMarshallerUnmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan("com.pc.globalpos.ratefeed.model");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		map.put(Marshaller.JAXB_ENCODING, "UTF-8");
		jaxb2Marshaller.setMarshallerProperties(map);
		return jaxb2Marshaller;
	}
	
	@Bean
    public ApplicationProperties loadApplicationProperties() {
    	ApplicationProperties props = new ApplicationProperties();
    	props.setRatefeedSource(env.getRequiredProperty("ratefeed.source"));
    	return props;
    }
}
