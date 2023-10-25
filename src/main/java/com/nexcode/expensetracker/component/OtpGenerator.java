package com.nexcode.expensetracker.component;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {
	
	public static String generateOtp() {

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		String otpString = String.format("%06d", otp);

		return otpString;
	}
	
	
	public static Instant getOtpExpiredTime() {
		
	    Duration duration = Duration.ofMinutes(3); //3 minutes
	    Instant instant = Instant.now().plus(duration);
	    System.out.println(instant);
	    return instant;
	}
}
