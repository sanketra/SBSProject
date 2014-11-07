package com.onlinebanking.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.helpers.ValidationHelper;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.EmployeeRegistrationModel;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.TransactionAppModel;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.CaptchaService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class AdminController {

	private UserService userService;
	private AccountService accountService;
	private TransactionService transactionService;
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
	@Qualifier(value = "accountService")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService ps) {
		this.userService = ps;
	}

	@RequestMapping(value = { "/admin", "/admin/*" }, method = RequestMethod.GET)
	public String handleAdminDashboardRequest(Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		model.addAttribute("name", auth.getName());
		model.addAttribute("contentView", "admin_home");
		return "admin/admin_template";
	}

	/*
	 * @RequestMapping(value = "/admin/post/{to_do}", method =
	 * RequestMethod.POST) public String
	 * handleAdminPostRequests(HttpServletRequest request, HttpServletResponse
	 * response ,Model model, final RedirectAttributes redirectAttributes
	 * ,@PathVariable String to_do ){
	 * 
	 * return ""; }
	 */

	@RequestMapping(value = "/admin/newUsers", method = RequestMethod.GET)
	public String newUsers(Model model) {
		List<User> newUsers = userService.listNewUsers();
		model.addAttribute("newUsers", newUsers);
		model.addAttribute("contentView", "admin_newUsers");

		return "admin/admin_template";
	}

	@RequestMapping(value = "/admin/admin_newUsers", method = RequestMethod.POST)
	public String customerApprove(Model model, HttpServletRequest request,
			HttpServletResponse response, final RedirectAttributes attributes) {
		Response status;
		if (request.getParameter("approve") != null) {
			status = this.userService.updateUserRegistrationFlag(
					request.getParameter("approve"), "approve");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/newUsers";
		} else if (request.getParameter("decline") != null) {
			status = this.userService.updateUserRegistrationFlag(
					request.getParameter("decline"), "decline");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/newUsers";
		}

		return "redirect:/admin/newUsers";
	}

	@RequestMapping(value = "/admin/processRequests", method = RequestMethod.GET)
	public String accessRequests(Model model) {

		List<UserRequest> pendingRequests = transactionService.getAllPendingUserAccessRequests();
	    List<UserRequest> approvedRequests = transactionService.getApprovedRequests();
		List<UserRequest> declinedRequests = transactionService.getDeclinedRequests();
		model.addAttribute("pendingUserRequests", pendingRequests);
		model.addAttribute("approvedUserRequests", approvedRequests);
		model.addAttribute("declinedUserRequests", declinedRequests);

		model.addAttribute("contentView", "admin_processRequests");

		return "admin/admin_template";

	}

	@RequestMapping(value = "/admin/admin_processRequests", method = RequestMethod.POST)
	public String processRequests(Model model, HttpServletRequest request,
			HttpServletResponse response, final RedirectAttributes attributes) {
		Response status;
		if (request.getParameter("approve") != null) {
			status = this.transactionService.updateAccessRequest(
					request.getParameter("approve"), "approve");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/processRequests";
		} else if (request.getParameter("decline") != null) {
			status = this.transactionService.updateAccessRequest(
					request.getParameter("decline"), "decline");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/processRequests";
		}

		return "redirect:/admin/processRequests";
	}
	
	@RequestMapping(value = "/admin/processNewAccountRequests", method = RequestMethod.GET)
	public String newAccountRequests(Model model) {

		List<UserRequest> pendingAdditionalAccountRequests = transactionService.getAllPendingAdditionalAccountRequests();
		model.addAttribute("pendingAdditionalAccountRequests", pendingAdditionalAccountRequests);
		model.addAttribute("contentView", "admin_newAccountRequests");

		return "admin/admin_template";

	}

	@RequestMapping(value = "/admin/admin_newAccountRequests", method = RequestMethod.POST)
	public String processNewAccountRequests(Model model, HttpServletRequest request,
			HttpServletResponse response, final RedirectAttributes attributes) {
		Response status;
		if (request.getParameter("approve") != null) {
			status = this.userService.updateNewAccountRequest(request.getParameter("approve"), "approve");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/processNewAccountRequests";
		} else if (request.getParameter("decline") != null) {
			status = this.userService.updateNewAccountRequest(request.getParameter("decline"), "decline");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/processNewAccountRequests";
		}
		
		return "redirect:/admin/processNewAccountRequests";
	}

	@RequestMapping(value = "/admin/customerList", method = RequestMethod.GET)
	public String customerList(Model model) {
		List<User> listUsers = this.userService.listCustomers();
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("contentView", "admin_customerList");
		return "admin/admin_template";
	}

	@RequestMapping(value = "/admin/admin_customerList", method = RequestMethod.POST)
	public String editCustomer(HttpServletRequest request, Model model,
			final RedirectAttributes attributes) {
		if (request.getParameter("submit").equalsIgnoreCase("delete")) {
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			userService.removeUser(u.getUserId());
			attributes.addFlashAttribute("response", new Response("success",
					"deleted user"));
			return "redirect:/admin/customerList";
		} else if (request.getParameter("submit").equalsIgnoreCase("update")) {
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			UserAppModel userAppModel = new UserAppModel(u);
			model.addAttribute("userProfile", userAppModel);
			model.addAttribute("contentView", "admin_updateUserProfile");
			return "admin/admin_template";
		} else {
			return "redirect:/admin/customerList";
		}
	}

	@RequestMapping(value = "/admin/admin_updateUserProfile", method = RequestMethod.POST)
	public String updateUserTransaction(
			@ModelAttribute("userProfile") UserAppModel userAppModel,
			HttpServletRequest request, Model model,
			final RedirectAttributes attributes) {
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
			model.addAttribute("contentView", "admin_updateUserProfile");
			return "admin/admin_template";
		}
		attributes.addFlashAttribute("response", new Response("success",
				"Profile updated successflly!"));
		return "redirect:/admin/customerList";
	}

	@RequestMapping(value = "/admin/employeeList", method = RequestMethod.GET)
	public String employeeList(Model model) {
		List<User> listEmployees = this.userService.listEmployees();
		model.addAttribute("listEmployees", listEmployees);
		model.addAttribute("contentView", "admin_employeeList");
		return "admin/admin_template";
	}

	@RequestMapping(value = "/admin/admin_employeeList", method = RequestMethod.POST)
	public String editEmployee(HttpServletRequest request, Model model,
			final RedirectAttributes attributes) {
		if (request.getParameter("submit").equalsIgnoreCase("delete")) {
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			userService.removeUser(u.getUserId());
			attributes.addFlashAttribute("response", new Response("success",
					"deleted employee"));
			return "redirect:/admin/employeeList";
		} else if (request.getParameter("submit").equalsIgnoreCase("update")) {
			String emailId = request.getParameter("email_Id");
			User u = userService.getUserByEmailId(emailId);
			UserAppModel userAppModel = new UserAppModel(u);
			model.addAttribute("userProfile", userAppModel);
			model.addAttribute("contentView", "admin_updateUserProfile");
			return "admin/admin_template";
		} else {
			return "redirect:/admin/employeeList";
		}
	}

	@RequestMapping(value = "/admin/accountTransactions", method = RequestMethod.GET)
	public String employeeTransaction(Model model) {
		List<Account> userAccounts = accountService.getAllUserAccounts();
		model.addAttribute("userAccounts", userAccounts);
		model.addAttribute("contentView", "admin_accountTransactions");
		return "admin/admin_template";
	}

	@RequestMapping(value = "/admin/admin_accountTransactions", method = RequestMethod.POST)
	public String viewUserTransactions(HttpServletRequest request, Model model) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			String accountId = request.getParameter("account_id");
			if (accountId != null && !accountId.equals("")) {
				transactions = transactionService
						.getAllTransactionsForAccountId(Integer
								.parseInt(accountId));
			}
		} catch (Exception e) {

		}
		model.addAttribute("transactionList", transactions);
		model.addAttribute("contentView", "admin_viewUserTransactions");

		return "admin/admin_template";
	}
	
	@RequestMapping(value="/admin/admin_editUserTransaction", method = RequestMethod.POST)
	public String editUserTransaction(HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		try
		{
			if(request.getParameter("submit").equalsIgnoreCase("delete"))
			{
				String transactionId = request.getParameter("transaction_id");
				Transaction transaction = transactionService.getTransaction(transactionId);
				transactionService.deleteTransaction(transaction);
				attributes.addFlashAttribute("response", new Response("success", "deleted transaction"));
				return "redirect:/admin/admin_accountTransactions";
			}
			else if(request.getParameter("submit").equalsIgnoreCase("update"))
			{
				String transactionId = request.getParameter("transaction_id");
				Transaction transaction = transactionService.getTransaction(transactionId);
				TransactionAppModel transactionAppModel = new TransactionAppModel(transaction);
				model.addAttribute("userTransaction", transactionAppModel);
				model.addAttribute("contentView", "admin_updateUserTransactions");
				return "admin/admin_template";
			}
			else
			{
				return "redirect:/admin/admin_accountTransactions";
			}
		}
		catch(Exception e)
		{
			attributes.addFlashAttribute("response", new Response("success", e.getMessage()));
			return "redirect:/admin/admin_accountTransactions";
		}
	}
	
	@RequestMapping(value="/admin/admin_updateUserTransaction", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("userTransaction") TransactionAppModel transactionAppModel, HttpServletRequest request,Model model, final RedirectAttributes attributes)
	{
		try
		{
			String challenge = request.getParameter("recaptcha_challenge_field");
			String uresponse = request.getParameter("recaptcha_response_field");
			String remoteAddress = request.getRemoteAddr();
			Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
					uresponse, remoteAddress);
			if (verifyStatus == true)
			{
			transactionService.updateTransaction(transactionAppModel);
			attributes.addFlashAttribute("response", new Response("success", "updated transaction"));
			}
			else
			{
				attributes.addFlashAttribute("response", new Response("error",
						"Wrong captcha, please try again!"));
				model.addAttribute("contentView", "admin_updateUserTransactions");
				return "admin/admin_template";
			}
			return "redirect:/admin/admin_accountTransactions";
		}
		catch(Exception e)
		{
			attributes.addFlashAttribute("response", new Response("success", e.getMessage()));
			return "redirect:/admin/admin_accountTransactions";
		}
	}

	@RequestMapping(value = "/admin/employeeRegistration", method = RequestMethod.GET)
	public String employeeRegitration(Model model) {
		model.addAttribute("user", new EmployeeRegistrationModel());
		model.addAttribute("contentView", "admin_employeeRegistration");
		return "admin/admin_template";
	}

	@RequestMapping(value = "/admin/admin_employeeRegistration", method = RequestMethod.POST)
	public String addNewEmployee(
			@ModelAttribute("user") @Valid EmployeeRegistrationModel p,
			HttpServletRequest request, BindingResult bindingResult,
			final RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			attributes.addFlashAttribute("response", new Response("error",
					bindingResult.getAllErrors().get(0).getDefaultMessage()));
			return "redirect:/admin/employeeRegistration";
		}

		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);

		if (verifyStatus == true) {
			
			User u = new User();
			u = ValidationHelper.getUserFromEmployeeRegistrationModel(p, u);
			userService.sendUniquePassword(u.getPassword(), u.getEmailId());
			
			if (this.userService.getUserById(p.getUserId()) == null) {
				// new person, add it
				this.userService.addUser(u);
			} else {
				// existing person, call update
				this.userService.updateUser(u);
			}

			attributes.addFlashAttribute("response", new Response("success",
					"Account registration successful!!"));
		}

		else
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));

		return "redirect:/admin/employeeRegistration";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex, 
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("denied");
		model.addObject("response", new Response("error", "Illegal Operation. Please go back & enter correct details."));
		return "redirect:/denied";
	}
}
