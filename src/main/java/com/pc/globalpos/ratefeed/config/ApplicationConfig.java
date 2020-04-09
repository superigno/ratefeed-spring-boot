package com.pc.globalpos.ratefeed.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.util.EncryptDecryptUtils;

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
				.setSourceUrl(env.getRequiredProperty("ratefeed.source.url"))
				.setFilenameFormat(env.getRequiredProperty("ratefeed.format.filename"))
				.setRunTimeIntervalInMinute(
						env.getRequiredProperty("ratefeed.config.runtime.intervalinminute", Integer.class))
				.setRetryLimit(env.getRequiredProperty("ratefeed.config.retry.limit", Integer.class))
				.setRetryIntervalInMinute(
						env.getRequiredProperty("ratefeed.config.retry.intervalinminute", Integer.class))
				.setBaseCurrency(env.getRequiredProperty("ratefeed.currency.basecurrency")).build();

		return props;
	}

	public static String getFilename(String filenameWithDateFormat) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(filenameWithDateFormat);
			filenameWithDateFormat = sdf.format(new Date());
		} catch (Exception e) {
			logger.debug("Invalid filename date format: {}", filenameWithDateFormat);
		}
		return filenameWithDateFormat;
	}

	@Bean
	public JavaMailSender javaMailSender() {
		final String host = env.getRequiredProperty("ratefeed.mail.host");
		final String port = env.getRequiredProperty("ratefeed.mail.port");
		final String username = env.getRequiredProperty("ratefeed.mail.username");
		final String password = EncryptDecryptUtils.decrypt(env.getRequiredProperty("ratefeed.mail.password"));
		final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		javaMailSender.setPort(Integer.parseInt(port));
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setJavaMailProperties(getMailProperties());
		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", env.getRequiredProperty("ratefeed.mail.properties.mail.smtp.auth"));
		properties.setProperty("mail.smtp.starttls.enable",
				env.getRequiredProperty("ratefeed.mail.properties.mail.smtp.starttls.enable"));
		return properties;
	}
}
