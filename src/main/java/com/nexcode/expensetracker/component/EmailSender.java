package com.nexcode.expensetracker.component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EmailSender{

	private final JavaMailSender mailSender;

	public void send(String to, String email) throws MessagingException{

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Here is your Otp");
			helper.setFrom("violawhite215@gmail.com");
			mailSender.send(mimeMessage);

	}

	public static String buildEmailForm(String name, String otp) {
		 return "<p>Hello " + name + ",<br><br></p>"
		            + "    <p>You are receiving this email because you have requested OTP (one-time password).</p>"
		            + "    <p>Your OTP is: <strong>" + otp + "</strong><br><br></p>" 
		            + "    <p>Warm regards,<br> NexTracker Team</p>";
		 }

}
