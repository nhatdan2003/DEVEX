package com.Devex.Sevice;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Devex.Config.TwilioConfig;
import com.Devex.DTO.MailOtpDTO;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpResponseDTO;
import com.Devex.DTO.OtpStatus;
import com.Devex.DTO.OtpValidationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.MessagingException;

@Service
public class OTPService {
	@Autowired
	private TwilioConfig twilioConfig;
	
	@Autowired
	private MailerService emailService;
	
	Map<String, String> otpMap = new HashMap<>();

	public OtpResponseDTO sendSMS(OtpRequestDTO otpRequest) {
		OtpResponseDTO otpResponse = null;
		try {
			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
			String otp = generateOtp();
			String otpMessage = "DEVEX: Ma xac thuc tai khoan cua ban la " + otp
					+ " Khong chia se ma nay voi nguoi khac.";
			Message message = Message.creator(to, from, otpMessage).create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponse = new OtpResponseDTO(OtpStatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			// TODO: handle exception
			otpResponse = new OtpResponseDTO(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponse;
	}
	
	public void sendMailOtp(String email) {
		String otp = generateOtp();
		System.out.println(otp);
		try {
			emailService.sendMailOtpSignUp(email, otp);
			otpMap.put(email, otp);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public boolean validateOtp(OtpValidationRequest otpValidationRequest) {
		String phone = null;
		String otp = null;
		
		for (Map.Entry<String, String> entry : otpMap.entrySet()) {
			phone = entry.getKey();
            otp = entry.getValue();
        }
		if (otpValidationRequest.getUsername().equals(phone) && otpValidationRequest.getOtpNumber().equals(otp)) {
			otpMap.remove(phone, otpValidationRequest.getOtpNumber());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validateMailOtp(MailOtpDTO mailOtp) {
		String email = null;
		String otp = null;
		for (Map.Entry<String, String> entry : otpMap.entrySet()) {
			email = entry.getKey();
            otp = entry.getValue();
        }
		if (mailOtp.getEmail().equals(email) && mailOtp.getOtp().equals(otp)) {
			otpMap.remove(email, mailOtp.getOtp());
			return true;
		} else {
			return false;
		}
	}
	
	private String generateOtp() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}

}
