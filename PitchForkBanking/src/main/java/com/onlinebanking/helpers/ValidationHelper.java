package com.onlinebanking.helpers;

import java.util.Random;

import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRequest;

public class ValidationHelper {

	public static int generateRandomNumber(int min, int max) {
		Random randomGenerator = new Random();
		int randomNum = randomGenerator.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static Response validateAmount(String amt) {
		try {
			double amountD = Double.parseDouble(amt);
			if (amountD < 1) {
				return new Response("error", "Amount should be greater than 0.");
			}
		} catch (NumberFormatException e) {
			return new Response("error", "Amount is not valid.");
		}
		return new Response("success", "Amount is valid");
	}
	
	public static User getUserFromUserAppModel(UserAppModel a, User u) {
		u.setUserId(a.getUserId());
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setSsn(a.getSsn());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		u.setAnswer1(a.getAnswer1());
		u.setAnswer2(a.getAnswer2());
		u.setAnswer3(a.getAnswer3());
		u.setQues1(a.getQues1());
		u.setQues2(a.getQues2());
		u.setQues3(a.getQues3());
		return u;
}
	public static Response validateUserRequest(UserRequest userRequest)
	{
		try
		{
			if(userRequest.getFname() == null || userRequest.getFname() == "")
			{
				return new Response("error", "first name not entered");
			}
			
			if(userRequest.getLname() == null || userRequest.getLname() == "")
			{
				return new Response("error", "last name not entered");
			}
			if(userRequest.getEmailId() == null || userRequest.getEmailId() == "")
			{
				return new Response("error", "last name not entered");
			}
			return new Response("success", "");
		}
		catch(Exception e)
		{
			return new Response("error", "Exception occurred. Could not complete request");
		}	
	}
}
