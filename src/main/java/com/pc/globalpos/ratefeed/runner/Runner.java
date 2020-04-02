package com.pc.globalpos.ratefeed.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.Email;
import com.pc.globalpos.ratefeed.service.EmailService;

//@Component
public class Runner implements ApplicationRunner {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ApplicationProperties props;
	
	private void sendEmail() {
		Email email = new Email();
		email.setFrom("emailer1986@gmail.com");
		email.setTo("superigno24@gmail.com");
		email.setSubject("This is a test mail");
		email.setMessageText("This is a sample text message.");
		emailService.sendMail(email);		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Props: "+props.getRatefeedSource());
		sendEmail();			
	}
	

}
