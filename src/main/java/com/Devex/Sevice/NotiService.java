package com.Devex.Sevice;

import com.Devex.Entity.MailInfo;
import jakarta.mail.MessagingException;

public interface NotiService {
		
	void sendNotification(String userFrom,String userTo,String link, String type, String object);
	
	void sendHistory(String userFrom,String userTo,String link, String type, String object);
}
