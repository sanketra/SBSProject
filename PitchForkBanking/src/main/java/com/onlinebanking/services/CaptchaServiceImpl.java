package com.onlinebanking.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private static CaptchaService captchaServiceImpl = null;

	public static CaptchaService getInstance() {
		if (captchaServiceImpl == null) {
			captchaServiceImpl = new CaptchaServiceImpl();
		}
		return captchaServiceImpl;
	}

	@Override
	@Transactional
	public boolean verifyCaptcha(String challenge, String uresponse,
			String remoteAddress) {
		ReCaptchaImpl captcha = new ReCaptchaImpl();
		captcha.setPrivateKey("6LfLUv0SAAAAAM9or63lxIkOI5n9fStxa2qdhsY8");
		if (uresponse == null) {
			return false;
		}
		ReCaptchaResponse reCaptchaResponse = captcha.checkAnswer(
				remoteAddress, challenge, uresponse);

		if (reCaptchaResponse.isValid()) {
			System.out.println("true!");
			return true;
		}
		System.out.println("false!");

		return false;
		// TODO Auto-generated method stub

	}

}