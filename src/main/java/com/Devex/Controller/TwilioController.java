package com.Devex.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.Devex.Config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Controller
public class TwilioController {
	
//	@Autowired
//	private TwilioConfig twilioConfig;
//	
//	@PostConstruct
//	public void setup() {
//		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//	}
//	
}
