package com.onlinebanking.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoHelper {

	public static String getEncryptedString(String str) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(str);
	}
}
