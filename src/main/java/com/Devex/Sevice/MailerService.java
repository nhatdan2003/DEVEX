package com.Devex.Sevice;


import com.Devex.DTO.MailInfo;

import jakarta.mail.MessagingException;

public interface MailerService {

	void send(MailInfo mail) throws MessagingException;
	
	void send(String to, String subject, String body) throws MessagingException;
	
	void sendMailToFormat(String type, Object user) throws MessagingException;
	
	
	void queue(MailInfo mail);
	
	void queue(String to, String subjecct, String body);

	void sendMailOtpSignUp(String mail, String otp) throws MessagingException;
	
}
