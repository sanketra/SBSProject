package com.onlinebanking.helpers;

import java.util.Random;

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
}
