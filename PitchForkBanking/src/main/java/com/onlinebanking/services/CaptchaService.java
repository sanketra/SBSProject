package com.onlinebanking.services;

public interface CaptchaService {

	public boolean verifyCaptcha(String challenge, String uresponse, String remoteAddress);
	
}