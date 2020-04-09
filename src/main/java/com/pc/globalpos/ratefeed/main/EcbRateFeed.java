package com.pc.globalpos.ratefeed.main;

import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pc.globalpos.ratefeed.config.ApplicationConfig;
import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.Email;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.service.EmailService;
import com.pc.globalpos.ratefeed.source.RateSource;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@Component
public class EcbRateFeed {

	private static final Logger logger = LoggerFactory.getLogger(EcbRateFeed.class);
	private int retryCtr = 0;

	@Autowired
	EmailService emailService;

	@Autowired
	ApplicationProperties props;

	@Autowired
	RateSource<Envelope> rateSource;

	@Scheduled(fixedRateString = "#{@loadApplicationProperties.getRunTimeIntervalInMinute() * 60000}")
	@Retryable(value = {Exception.class }, maxAttemptsExpression = "#{@loadApplicationProperties.getRetryLimit() + 1}", backoff = @Backoff(delayExpression = "#{@loadApplicationProperties.getRetryIntervalInMinute() * 60000}"))
	public void initialize() throws Exception {
		try {
			getFeedParseAndSave();
		} catch (Exception e) {
			logger.trace("Rate feed error: ", e);
			sendEmailOnRetry(e.getMessage());
			throw e;
		}		
	}

	@Recover
	private void fallback() {
		final String msg = String.format("Failed to completely process rate feed from %s after %d retries. See full logs for details.", props.getSourceUrl(), props.getRetryLimit());
		logger.error(msg);
		retryCtr = 0;
		sendEmailInNewThread(msg);
	}
	
	private void getFeedParseAndSave() throws IOException {
		final Envelope feed = rateSource.getFeed(props.getSourceUrl());
		final String strFeed = rateSource.parse(feed);		
		saveToOutputDirectories(strFeed);
	}

	private void saveToOutputDirectories(final String strFeed) throws IOException {
		final String[] outputDirs = props.getOutputDirs();	
		final String filename = ApplicationConfig.getFilename(props.getFilenameFormat());
		for (String outputDir : outputDirs) {
			rateSource.saveToFile(strFeed, Paths.get(outputDir.trim()).resolve(filename));			
		}
	}
	
	private void sendEmailOnRetry(String message) {
		if (retryCtr < props.getRetryLimit()) {
			final String msg = String.format("%s. Retrying %d/%d in %d minute(s)", message, ++retryCtr, props.getRetryLimit(), props.getRetryIntervalInMinute());
			logger.error(msg);
			sendEmailInNewThread(msg);
		}
	}

	private void sendEmailInNewThread(String message) {
		new Thread() {
			public void run() {
				try {
					logger.info("Sending email notification...");
					Email email = new Email();
					email.setFrom(props.getMailFrom());
					email.setTo(props.getMailTo().split(","));
					email.setSubject(props.getMailSubject());
					email.setMessageText(message);
					emailService.sendMail(email);
					logger.info("Email sent");
				} catch (Exception e) {
					logger.trace("Error in sending email: ", e);
					logger.error("Error in sending email");
				}
			}
		}.start();
	}

}
