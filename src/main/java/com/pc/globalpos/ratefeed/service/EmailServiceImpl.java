package com.pc.globalpos.ratefeed.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pc.globalpos.ratefeed.model.Email;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(final Email email) {
		logger.info("Sending email...");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject(email.getSubject());
		simpleMailMessage.setFrom(email.getFrom());
		simpleMailMessage.setTo(email.getTo());
		simpleMailMessage.setText(email.getMessageText());
		javaMailSender.send(simpleMailMessage);
		logger.info("Email sent.");
	}

	public void sendMailWithAttachment(final Email email, final String fileDesc, final String filePath)
			throws Exception {
		logger.info("Sending email...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setSubject(email.getSubject());
		mimeMessageHelper.setFrom(email.getFrom());
		mimeMessageHelper.setTo(email.getTo());
		mimeMessageHelper.setText(email.getMessageText());
		FileSystemResource file = new FileSystemResource(new File(filePath));
		mimeMessageHelper.addAttachment(fileDesc, file);
		javaMailSender.send(mimeMessage);
		logger.info("Email sent.");
	}

}
