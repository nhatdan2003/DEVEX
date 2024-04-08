package com.Devex.Sevice;

import com.Devex.Entity.MailInfo;

import jakarta.mail.MessagingException;

public interface MailSevice {
		
	void send(MailInfo mail) throws MessagingException;
	void send(String to, String subejct , String body) throws MessagingException;
	void queue(MailInfo mail);
	void queue(String to, String subejct , String body);

}
