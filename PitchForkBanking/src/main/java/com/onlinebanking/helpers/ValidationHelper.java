package com.onlinebanking.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.AccountAppModel;
import com.onlinebanking.models.EmployeeRegistrationModel;
import com.onlinebanking.models.TransactionAppModel;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRegistrationModel;
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

	// Properties not set here are assumed to be never editable.
	// Please refrain from adding them here.
	public static User getUserFromUserAppModel(UserAppModel a, User u) {
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		return u;
	}

	@SuppressWarnings("deprecation")
	public static User getUserFromUserRegistrationModel(UserRegistrationModel a, User u) {
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		u.setDob(new Date(a.getDob()));
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		u.setPassword(a.getPassword());
		u.setSsn(a.getSsn());
		u.setRole(a.getRole());
		u.setQues1(a.getQues1());
		u.setQues2(a.getQues2());
		u.setQues3(a.getQues3());
		u.setAnswer1(a.getAnswer1());
		u.setAnswer2(a.getAnswer2());
		u.setAnswer3(a.getAnswer3());
		return u;
	}
	
	public static User getUserFromEmployeeRegistrationModel(EmployeeRegistrationModel a, User u) {
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		Date dob = new Date();
		try {
			dob = new SimpleDateFormat("MM/DD/YYYY", Locale.ENGLISH).parse(a.getDob());
		} catch (ParseException e) {
			System.out.print("Taking today's date.");
		}
		u.setDob(dob);
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		u.setPassword(a.getPassword());
		u.setSsn(a.getSsn());
		u.setRole(a.getRole());
		u.setQues1(a.getQues1());
		u.setQues2(a.getQues2());
		u.setQues3(a.getQues3());
		u.setAnswer1(a.getAnswer1());
		u.setAnswer2(a.getAnswer2());
		u.setAnswer3(a.getAnswer3());
		u.setEnabled(a.getEnabled());
		return u;
	}

	// Properties not set here are assumed to be never editable.
	// Please refrain from adding them here.
	public static Account getAccountFromAccountAppModel(AccountAppModel acc,
			Account a) {
		a.setAmount(acc.getAmount());
		return a;
	}

	public static List<AccountAppModel> getAccountAppModelListFromAccountList(
			List<Account> acclist) {
		List<AccountAppModel> list = new ArrayList<AccountAppModel>();

		for (Account acc : acclist) {
			list.add(new AccountAppModel(acc));
		}

		return list;
	}
	
	public static Response validateUserRequest(UserRequest userRequest, User toUser)
	{
		try
		{
			if (userRequest.getFname() == null || userRequest.getFname() == "") {
				return new Response("error", "first name not entered");
			}

			if (userRequest.getLname() == null || userRequest.getLname() == "") {
				return new Response("error", "last name not entered");
			}
			if (userRequest.getEmailId() == null
					|| userRequest.getEmailId() == "") {
				return new Response("error", "last name not entered");
			}
			if(toUser == null)
			{
				return new Response("error", "user email id not correct");
			}
			if (!toUser.getFname().equalsIgnoreCase(userRequest.getFname()) || !toUser.getLname().equalsIgnoreCase(userRequest.getLname())) {
				return new Response("error", "user details not correct");
			}
			return new Response("success", "");
			
		} catch (Exception e) {
			return new Response("error",
					"Exception occurred. Could not complete request");
		}
	}
	
	public static Response validateTransactionAppModel(TransactionAppModel transactionAppModel, AccountHome accountHome)
	{
		Response status = validateAmount(transactionAppModel.getTransactionAmount());
		if (status.getStatus().equals("error")) {
			return status;
		}
		try
		{
			Account toAccId = accountHome.findById(Integer.parseInt(transactionAppModel.getToAccountNum()));
			Account fromAccId = accountHome.findById(Integer.parseInt(transactionAppModel.getFromAcountNum()));
			if(toAccId == null || fromAccId == null)
			{
				return new Response("error", "account number does not exist");
			}
			return new Response("success","");
		}
		catch(NumberFormatException nfe)
		{
			return new Response("error", "invalid account number");
		}
	}
}
