package com.onlinebanking.services;

import com.onlinebanking.models.User;
import com.onlinebanking.models.Userotp;

public interface OtpService {

	public void sendOtp(User user, String emailId);

	public boolean verifyOtp(Userotp otpObj, String newOtp);

	Userotp getUserotpById(String id);

}