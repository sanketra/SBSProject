package com.onlinebanking.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.TransactionAppModel;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.CaptchaService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class EmployeeController {
	private TransactionService transactionService;
	private UserService userService;
	private CaptchaService captchaService;
	
	@Autowired(required = true)
	@Qualifier(value = "transactionService")
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "captchaService")
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = {"/employee", "/employee/*"}, method = RequestMethod.GET)
	public String handleAdminDashboardRequests(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", auth.getName());
		model.addAttribute("contentView","emp_home");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/user_details")
	public String employeUserDetails(Model model){
		List<UserRequest> userRequests = transactionService.getApprovedProfileRequestsFromUser();
		model.addAttribute("userList", userRequests);
		model.addAttribute("contentView", "user_details");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/editUserProfile", method = RequestMethod.POST)
	public String editUserProfile(HttpServletRequest request, Model model, final RedirectAttributes attributes){
		if(request.getParameter("email_Id")!=null)
		{
		if(request.getParameter("submit").equalsIgnoreCase("delete"))
		{
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			userService.removeUser(u.getUserId());
			attributes.addFlashAttribute("response", new Response("success", "deleted user"));
			return "redirect:/employee/user_details";
		}
		else if(request.getParameter("submit").equalsIgnoreCase("update"))
		{
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			UserAppModel userAppModel = new UserAppModel(u);
			model.addAttribute("userProfile", userAppModel);
			model.addAttribute("contentView", "updateUserProfile");
			return "employee/emp_template";
		}
		else
		{
			return "redirect:/employee/user_details";
		}
		}
		else
		{
			attributes.addFlashAttribute("response", new Response("error", "Select row to proceed"));
			return "redirect:/employee/user_details";
		}
		
		}
	
	
	@RequestMapping(value="/employee/updateUserProfile", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("userProfile") UserAppModel userAppModel, HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		User u = userService.getUserById(userAppModel.getUserId());
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		// verify Captcha
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		// redirect logic
		if (verifyStatus == true) {
			u.setFname(userAppModel.getFname());
			u.setLname(userAppModel.getLname());
			u.setEmailId(userAppModel.getEmailId());
			u.setAddress(userAppModel.getAddress());
			u.setCity(userAppModel.getCity());
			u.setState(userAppModel.getState());
			u.setZipcode(userAppModel.getZipcode());
			u.setPhoneno(userAppModel.getPhoneno());
			userService.updateUser(u);
		}
		// Wrong Captcha
		else {
			model.addAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
			model.addAttribute("userProfile", userAppModel);
			model.addAttribute("contentView", "updateUserProfile");
			return "employee/emp_template";
		}
		attributes.addFlashAttribute("response", new Response("success",
				"Profile updated successflly!"));
		return "redirect:/employee/user_details";
	}
	
	@RequestMapping(value="/employee/req_Access", method = RequestMethod.GET)
	public String employeeRequest(Model model){
		
		List<UserRequest> userRequests = transactionService.getAllPendingRequests();
		model.addAttribute("userRequest", new UserRequest());
		model.addAttribute("pendingUserRequests", userRequests);
		model.addAttribute("contentView","requestAccess");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/submitRequest", method = RequestMethod.POST)
	public String submitEmployeeRequest(@ModelAttribute("userRequest") UserRequest userRequest, HttpServletRequest request, final RedirectAttributes attributes) {
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		if (verifyStatus == true) {
		Response result = transactionService.addRequest(userRequest);
		
		attributes.addFlashAttribute("response", result);
		}
		
		else
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
		
		return "redirect:/employee/req_Access";
	}
	
	@RequestMapping(value="/employee/account_details")
	public String employeeTransaction(Model model){
		List<UserRequest> userRequests = transactionService.getApprovedTransactionRequestsFromUser();
		model.addAttribute("userList", userRequests);
		model.addAttribute("contentView", "acc_details");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/viewUserTransactions", method = RequestMethod.POST)
	public String viewUserTransactions(HttpServletRequest request, Model model)
	{
		List<Transaction> transactions = new ArrayList<Transaction>();
		try
		{
			String accountId = request.getParameter("account_id");
			if(accountId!=null && !accountId.equals(""))
			{
				transactions = transactionService.getAllTransactionsForAccountId(Integer.parseInt(accountId));
			}
		}
		catch(Exception e)
		{
			
		}
		model.addAttribute("transactionList", transactions);
		model.addAttribute("contentView", "viewUserTransactions");
		
		
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/editUserTransaction", method = RequestMethod.POST)
	public String editUserTransaction(HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		if(request.getParameter("submit").equalsIgnoreCase("delete"))
		{
			String transactionId = request.getParameter("transaction_id");
			Transaction transaction = transactionService.getTransaction(transactionId);
			transactionService.deleteTransaction(transaction);
			attributes.addFlashAttribute("response", new Response("success", "deleted transaction"));
			return "redirect:/employee/account_details";
		}
		else if(request.getParameter("submit").equalsIgnoreCase("update"))
		{
			String transactionId = request.getParameter("transaction_id");
			Transaction transaction = transactionService.getTransaction(transactionId);
			TransactionAppModel transactionAppModel = new TransactionAppModel(transaction);
			model.addAttribute("userTransaction", transactionAppModel);
			model.addAttribute("contentView", "updateUserTransactions");
			return "employee/emp_template";
		}
		else
		{
			return "redirect:/employee/account_details";
		}
	}
	
	@RequestMapping(value="/employee/updateUserTransaction", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("userTransaction") TransactionAppModel transactionAppModel, HttpServletRequest request,Model model, final RedirectAttributes attributes)
	{
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		if (verifyStatus == true)
		{
		Transaction transaction = transactionService.getTransaction(transactionAppModel.getTransactionId());
		try
		{
			transaction.setTransactionAmount(Double.parseDouble(transactionAppModel.getTransactionAmount()));
		}
		catch(NumberFormatException nfe)
		{
			model.addAttribute("response", new Response("error",
					"Please enter proper value in amount"));
			model.addAttribute("contentView", "updateUserTransactions");
			return "employee/emp_template";
		}
		transaction.setTransactionStatus(transactionAppModel.getTransactionStatus());
		transactionService.updateTransaction(transaction);
		attributes.addFlashAttribute("response", new Response("success", "updated transaction"));
		}
		else
		{
			model.addAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
			model.addAttribute("contentView", "updateUserTransactions");
			return "employee/emp_template";
		}
		return "redirect:/employee/account_details";
	}
}
