package com.pc.globalpos.ratefeed.service;

import com.pc.globalpos.ratefeed.model.Email;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
public interface EmailService {
	
	public void sendMail(final Email email);
	public void sendMailWithAttachment(final Email email, final String attachmentName, final String filePath) throws Exception;

}
