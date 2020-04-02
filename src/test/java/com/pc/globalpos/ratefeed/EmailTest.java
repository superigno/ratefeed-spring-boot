package com.pc.globalpos.ratefeed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.Application;
import com.pc.globalpos.ratefeed.model.Email;
import com.pc.globalpos.ratefeed.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EmailTest {

	@Autowired
	private EmailService emailService;

	@Test
	public void sendEmail() {
		Email email = new Email();
		email.setTo("test@gmail.com");
		email.setSubject("This is a test mail");
		email.setMessageText("This is a sample text message.");
		emailService.sendMail(email);
	}

}
