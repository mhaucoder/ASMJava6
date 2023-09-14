package edu.poly.services;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class MailerHelper {

	public String[] parseStringEmailToArray(String emailString) {
		String[] arrEmail = null;
		if (emailString.length() > 0) {
			emailString = removeSpace(emailString);
			arrEmail = emailString.split(",");
		}
		return arrEmail;
	}

	private String removeSpace(String string) {
		return string.replaceAll(" ", "");
	}

	public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(convFile);
		return convFile;
	}
}
