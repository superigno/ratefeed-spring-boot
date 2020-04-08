package com.pc.globalpos.ratefeed.main;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.Email;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.service.EmailService;
import com.pc.globalpos.ratefeed.source.RateSource;

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
	@Retryable(value = {
			Exception.class }, maxAttemptsExpression = "#{@loadApplicationProperties.getRetryLimit() + 1}", backoff = @Backoff(delayExpression = "#{@loadApplicationProperties.getRetryIntervalInMinute() * 60000}"))
	public void initialize() throws Exception {
		
		try {
			rateSource.getFeed(props.getSourceUrl());
			rateSource.parse();
			rateSource.saveToFile(Paths.get(props.getOutputDir()).resolve(props.getFilename()));
		} catch (Exception e) {
			logger.trace("Error in getting rate feed: ", e);
			if (retryCtr < props.getRetryLimit()) {
				final String msg = String.format("Unable to get rate feed. Retrying %d/%d in %d minute(s)", ++retryCtr,
						props.getRetryLimit(), props.getRetryIntervalInMinute());
				logger.error(msg);
				sendEmailInNewThread(msg);
			}
			throw e;
		}
		
	}

	@Recover
	private void fallback() {
		final String msg = String.format("Failed to get rate feed from %s after %d retries", props.getSourceUrl(),
				props.getRetryLimit());
		logger.error(msg);
		retryCtr = 0;
		sendEmailInNewThread(msg);
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
