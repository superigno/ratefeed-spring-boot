package com.pc.globalpos.ratefeed.runner;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.Email;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.service.EmailService;
import com.pc.globalpos.ratefeed.source.RateSource;

@Component
public class Runner implements ApplicationRunner {

	@Autowired
	EmailService emailService;

	@Autowired
	ApplicationProperties props;

	@Autowired
	RateSource<Envelope> rateSource;

	private void sendEmail() {
		Email email = new Email();
		email.setFrom("emailer1986@gmail.com");
		email.setTo("superigno24@gmail.com", "jleeclarin@gmail.com");
		email.setSubject("This is a test mail");
		email.setMessageText("This is a sample text message.");
		emailService.sendMail(email);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		String outputDir = props.getOutputDir();
		String filename = props.getFilename();

		try {
			rateSource.getFeed(props.getSourceUrl());
			rateSource.parse();
			rateSource.saveToFile(Paths.get(outputDir).resolve(filename));
			sendNewEmailThread();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sendNewEmailThread() {
		new Thread() {
			public void run() {
				sendEmail();
			}
		}.start();	
	}

}
