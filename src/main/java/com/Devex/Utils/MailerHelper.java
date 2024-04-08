package com.Devex.Utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class MailerHelper {

	public String[] parseStringEmailToArray(String emailstring) {
		String[] arremail = null;
		if(emailstring.length() > 0) {
			emailstring = removeSpace(emailstring);
			arremail = emailstring.split(",");
		}
		return arremail;
	}
	
	private String removeSpace(String string) {
		return string.trim().replaceAll(" ", "");
	}
	
	public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException{
		File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(convertFile);
		return convertFile;
	}
	
}
