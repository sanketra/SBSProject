package com.onlinebanking.helpers;

import java.util.Random;

import com.onlinebanking.models.UserRequest;

public class ValidationHelper {

	public static int generateRandomNumber(int min, int max) {
		Random randomGenerator = new Random();
		int randomNum = randomGenerator.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static ValidationStatus validateAmount(String amt) {
		try {
			double amountD = Double.parseDouble(amt);
			if (amountD < 1) {
				return new ValidationStatus(false, "Amount should be greater than 0.");
			}
		} catch (NumberFormatException e) {
			return new ValidationStatus(false, "Amount is not valid.");
		}
		return new ValidationStatus(true, "");
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
