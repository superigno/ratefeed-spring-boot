package com.pc.globalpos.ratefeed.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;

@Configuration
public class ApplicationConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	
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
    	props.setSourceName(env.getRequiredProperty("ratefeed.source.name"));
    	props.setSourceUrl(env.getRequiredProperty("ratefeed.source.url"));
    	props.setSourceType(env.getRequiredProperty("ratefeed.source.type"));
    	props.setOutputDir(env.getRequiredProperty("ratefeed.dir.output"));
    	props.setFilename(getFilename());
    	props.setBaseCurrency(env.getRequiredProperty("ratefeed.currency.basecurrency"));
    	return props;
    }
	
	private String getFilename() {
		String filename = env.getRequiredProperty("ratefeed.format.filename");
		String filenameDateFormat = env.getRequiredProperty("ratefeed.format.filename.date");
		String strDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(filenameDateFormat);
			strDate = sdf.format(new Date());
		} catch (Exception e) {
			logger.debug("Invalid date format: {}", filenameDateFormat);
			return filename;
		}
		return filename.replace(filenameDateFormat, strDate);		
	}
}
