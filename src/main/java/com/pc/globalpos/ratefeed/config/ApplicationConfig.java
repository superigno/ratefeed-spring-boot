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

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@Configuration
public class ApplicationConfig {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private Environment env;

	@Bean
	public Jaxb2Marshaller getMarshallerUnmarshaller() {
		final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan("com.pc.globalpos.ratefeed.model");
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		map.put(Marshaller.JAXB_ENCODING, "UTF-8");
		jaxb2Marshaller.setMarshallerProperties(map);
		return jaxb2Marshaller;
	}

	@Bean
	public ApplicationProperties loadApplicationProperties() {
		final ApplicationProperties props = new ApplicationProperties.Builder()
				.setMailFrom(env.getRequiredProperty("ratefeed.mail.from"))
				.setMailTo(env.getRequiredProperty("ratefeed.mail.to"))
				.setMailSubject(env.getRequiredProperty("ratefeed.mail.subject"))
				.setBaseDir(env.getRequiredProperty("ratefeed.dir.base"))
				.setOutputDirs(env.getRequiredProperty("ratefeed.dir.output").split(","))
				.setSourceName(env.getRequiredProperty("ratefeed.source.name"))
				.setSourceType(env.getRequiredProperty("ratefeed.source.type"))
				.setSourceUrl(env.getRequiredProperty("ratefeed.source.url")).setFilename(getFilename())
				.setRunTimeIntervalInMinute(
						env.getRequiredProperty("ratefeed.config.runtime.intervalinminute", Integer.class))
				.setRetryLimit(env.getRequiredProperty("ratefeed.config.retry.limit", Integer.class))
				.setRetryIntervalInMinute(
						env.getRequiredProperty("ratefeed.config.retry.intervalinminute", Integer.class))
				.setBaseCurrency(env.getRequiredProperty("ratefeed.currency.basecurrency")).build();

		return props;
	}

	private String getFilename() {
		String filename = env.getRequiredProperty("ratefeed.format.filename");
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(filename);
			filename = sdf.format(new Date());
		} catch (Exception e) {
			logger.debug("Invalid filename date format: {}", filename);			
		}
		return filename;
	}
}
