package com.onlinebanking.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.CryptoHelper;
import com.onlinebanking.helpers.Logger;
import com.onlinebanking.helpers.PKI;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.helpers.URLHelper;
import com.onlinebanking.helpers.ValidationHelper;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRegistrationModel;
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.CaptchaService;
import com.onlinebanking.services.OtpService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class UserController {
	private UserService userService;
	private CaptchaService captchaService;
	private AccountService accountService;
	private TransactionService transactionService;
	private OtpService otpService;
	private MessageSource messageSource;
	
	
	@Autowired(required = true)
	@Qualifier(value = "messageSource")
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired(required = true)
	@Qualifier(value = "otpService")
	public void setOtpService(OtpService otpService) {
		this.otpService = otpService;
	}

	@Autowired(required = true)
	@Qualifier(value = "captchaService")
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	@Autowired(required = true)
	@Qualifier(value = "transactionService")
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String handleSuccessFullAuthRequest(Model model) {
		return "login";
	}

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String handleRequest(Model model, HttpServletRequest request,
			HttpServletResponse response) {	
		HttpSession session = request.getSession();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		User u = this.userService.getUserByEmailId(auth.getName());
		if (u != null) {
			session.setAttribute("userId", u.getUserId());
			session.setAttribute("emailId", u.getEmailId());
		} else if (session.getAttribute("emailId") != null) {
			u = this.userService.getUserByEmailId((String) session.getAttribute("emailId"));
		}
		
		// If the account_id is already selected, remove it so that user can
		// select it again.
		if (session.getAttribute("account_id") != null) {
			session.removeAttribute("account_id");
		}

		model.addAttribute("accounts", ValidationHelper
				.getAccountAppModelListFromAccountList(this.accountService
						.getUserAccounts(u.getUserId())));
		model.addAttribute("fname", u.getFname());
		return "user/home";
	}

	@RequestMapping(value = { "/user/*", "/user/*/*", "/user/*/*/*" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String handleDashboardRequest(Model model,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes attributes) {							

		HashMap<String, String> urls = URLHelper.analyseRequest(request);
		HttpSession session = request.getSession();
		Response status;
		int account_id = 0;
		// Always check if the user has selected account id before going to next
		// page.
		if (request.getParameter("account_id") != null) {
			account_id = Integer.parseInt(request.getParameter("account_id"));
			session.setAttribute("account_id", account_id);
		} else if (session.getAttribute("account_id") == null) {
			attributes.addFlashAttribute("response", new Response("error",
					"Please select an account to proceed!!"));
			return "redirect:/user/home";
		}

		account_id = (Integer) session.getAttribute("account_id");
		// Now that user has an account id check if its a valid account of user.
		status = this.userService.isValidUserAccount(account_id, session
				.getAttribute("userId").toString());

		if (status.getStatus().equals("error")) {
			attributes.addFlashAttribute("response", new Response("error",
					status.getMessage()));
			return "redirect:/user/home";
		}

		// Handle all post requests
		if (URLHelper.isPOSTRequest(request)) {
			if (urls.get("url_2").toString().equals("transfer")) {
				String name = request.getParameter("name").toString();
				String toEmailId = request.getParameter("emailId").toString();
				String toAccount = request.getParameter("account_to")
						.toString();
				String fromAccount = session.getAttribute("account_id")
						.toString();
				// get the responses from the user
				String challenge = request
						.getParameter("recaptcha_challenge_field");
				String uresponse = request
						.getParameter("recaptcha_response_field");
				String remoteAddress = request.getRemoteAddr();
				// verify Captcha
				Boolean verifyStatus = this.captchaService.verifyCaptcha(
						challenge, uresponse, remoteAddress);
				if (verifyStatus == true) {
					status = this.userService.isValidAccount(Integer
							.parseInt(toAccount));
					if (status.getStatus().equals("error")) {
						attributes.addFlashAttribute("response", new Response(
								"error", status.getMessage()));
						return "redirect:/user/transfer";
					}
					String toUserId = this.accountService
							.getAccountById(Integer.parseInt(toAccount))
							.getUser().getUserId();
					User toUser = this.userService.getUserById(toUserId);

					// Validating the to_account & user details
					if (!toUser.getEmailId().equalsIgnoreCase(toEmailId)
							|| !toUser.getFname()
									.concat(" " + toUser.getLname())
									.equals(name)) {
						attributes.addFlashAttribute("response", new Response(
								"error", "Incorrect account details!!"));
						return "redirect:/user/transfer";
					}

					if (toAccount.equals(fromAccount)) {
						attributes
								.addFlashAttribute(
										"response",
										new Response("error",
												"Cannot transfer to currently selected account!!"));
						return "redirect:/user/transfer";
					}

					String amount = request.getParameter("amount");
					status = this.transactionService.createTransaction(
							fromAccount, toAccount, amount,
							TransactionType.TRANSFER);
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", new Response(
							"error", "Wrong captcha, please try again!"));
				}
				Logger.getinstance().logRequest(request, "Transfer");
				return "redirect:/user/transfer";
			} else if (urls.get("url_2").toString().equals("credit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				String amount = request.getParameter("amount").toString();
				// get the responses from the user
				String challenge = request
						.getParameter("recaptcha_challenge_field");
				String uresponse = request
						.getParameter("recaptcha_response_field");
				String remoteAddress = request.getRemoteAddr();
				// verify Captcha
				Boolean verifyStatus = this.captchaService.verifyCaptcha(
						challenge, uresponse, remoteAddress);
				if (verifyStatus == true) {
					status = this.transactionService.createTransaction(
							fromAccount, fromAccount, amount,
							TransactionType.CREDIT);
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", new Response(
							"error", "Wrong captcha, please try again!"));
				}
				Logger.getinstance().logRequest(request, "Credit	");
				return "redirect:/user/credit";
			} else if (urls.get("url_2").toString().equals("debit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				String amount = request.getParameter("amount").toString();
				// get the responses from the user
				String challenge = request
						.getParameter("recaptcha_challenge_field");
				String uresponse = request
						.getParameter("recaptcha_response_field");
				String remoteAddress = request.getRemoteAddr();
				// verify Captcha
				Boolean verifyStatus = this.captchaService.verifyCaptcha(
						challenge, uresponse, remoteAddress);
				if (verifyStatus == true) {
					status = this.transactionService.createTransaction(
							fromAccount, fromAccount, amount,
							TransactionType.DEBIT);
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", new Response(
							"error", "Wrong captcha, please try again!"));
				}
				Logger.getinstance().logRequest(request, "Debit");
				return "redirect:/user/debit";
			} else if (urls.get("url_2").toString().equals("authorize")) {
				// send otp
				otpService.sendOtp(
						this.userService.getUserByEmailId(session.getAttribute(
								"emailId").toString()),
						session.getAttribute("emailId").toString());
				if (request.getParameter("approve") != null) {
					session.setAttribute("requestId",
							request.getParameter("approve"));
					session.setAttribute("approveordecline", "approve");
				} else  {
					session.setAttribute("requestId",
							request.getParameter("decline"));
					session.setAttribute("approveordecline", "decline");
				}
				Logger.getinstance().logRequest(request, "Authorize		");
				return "verifyOtp";
			} else if (urls.get("url_2").toString().equals("requestaccount")) {
				status = this.transactionService.createAccountCreationRequest();
				attributes.addFlashAttribute("response", status);
				return "redirect:/user/requestaccount";
			}
		}

		// Handle all get requests
		if (urls.get("url_2").toString().equals("transfer")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "transfer");
			model.addAttribute("transfer", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("credit")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "credit");
			model.addAttribute("credit", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("debit")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "debit");
			model.addAttribute("debit", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("transactions")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			account_id = (Integer) session.getAttribute("account_id");
			// TODO: Transaction
			model.addAttribute("transactions", this.transactionService
					.getAllTransactionsForAccountId(account_id));
			model.addAttribute("contentView", "transactions");
			model.addAttribute("transaction", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("authorize")) {
			User u = this.userService.getUserByEmailId((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", u.getRole());
			List<UserRequest> list = this.transactionService
					.getPendingRequestsToUser(u.getUserId());
			model.addAttribute("requests", list);
			model.addAttribute("contentView", "authorize");
			model.addAttribute("authorize", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("profile")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "profile");
			UserAppModel u = new UserAppModel(
					this.userService.getUserByEmailId((String) session
							.getAttribute("emailId")));
			model.addAttribute("user", u);
			model.addAttribute("profile", "active");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("requestaccount")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "requestaccount");
			model.addAttribute("requestaccount", "active");
			return "user/template";
		} else {
			attributes.addFlashAttribute("response", new Response("error",
					"Please select an account to proceed!!"));
			return "redirect:/user/home";
		}	
	}

	@RequestMapping(value = "/user/payment", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String userPayment(HttpServletRequest request, Model model,
			final RedirectAttributes attributes) {
		Logger.getinstance().logRequest(request, "Payment");
		HttpSession session = request.getSession();
		Response status;
		int account_id = 0;
		// Always check if the user has selected account id before going to next
		// page.
		if (request.getParameter("account_id") != null) {
			account_id = Integer.parseInt(request.getParameter("account_id"));
			session.setAttribute("account_id", account_id);
		} else if (session.getAttribute("account_id") == null) {
			attributes.addFlashAttribute("response", new Response("error",
					"Please select an account to proceed!!"));
			return "redirect:/user/home";
		}

		account_id = (Integer) session.getAttribute("account_id");
		// Now that user has an account id check if its a valid account of user.
		status = this.userService.isValidUserAccount(account_id, session
				.getAttribute("userId").toString());

		if (status.getStatus().equals("error")) {
			attributes.addFlashAttribute("response", new Response("error",
					status.getMessage()));
			return "redirect:/user/home";
		}

		// Handle POST Request
		if (URLHelper.isPOSTRequest(request)) {
			if (request.getParameter("accept") != null) {
				status = this.transactionService.updatePaymentRequest(
						request.getParameter("accept"), "accept");
				attributes.addFlashAttribute("response", status);
				return "redirect:/user/payment";
			} else if (request.getParameter("decline") != null) {
				status = this.transactionService.updatePaymentRequest(
						request.getParameter("decline"), "decline");
				attributes.addFlashAttribute("response", status);
				return "redirect:/user/payment";
			}
		}

		// Handle GET request
		String userType = userService.getUserRole((String) session
				.getAttribute("emailId"));
		model.addAttribute("role", userType);
		// TODO: Transaction
		model.addAttribute("transactions", this.transactionService
				.getPaymentRequestForAccountId(account_id));
		model.addAttribute("contentView", "payment");
		model.addAttribute("payment", "active");
		return "user/template";

	}

	@RequestMapping(value = "/user/requestPayment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String merchantRequestPayment(HttpServletRequest request,
			Model model, final RedirectAttributes attributes) {
		Logger.getinstance().logRequest(request	, "Request Payment");
		HttpSession session = request.getSession();
		Response status;
		int account_id = 0;
		// Always check if the user has selected account id before going to next
		// page.
		if (request.getParameter("account_id") != null) {
			account_id = Integer.parseInt(request.getParameter("account_id"));
			session.setAttribute("account_id", account_id);
		} else if (session.getAttribute("account_id") == null) {
			attributes.addFlashAttribute("response", new Response("error",
					"Please select an account to proceed!!"));
			return "redirect:/user/home";
		}

		account_id = (Integer) session.getAttribute("account_id");
		// Now that user has an account id check if its a valid account of user.
		status = this.userService.isValidUserAccount(account_id, session
				.getAttribute("userId").toString());

		if (status.getStatus().equals("error")) {
			attributes.addFlashAttribute("response", new Response("error",
					status.getMessage()));

			return "redirect:/user/home";
		}

		String randomString = PKI.generateRandomString();
		model.addAttribute("randomString", randomString);

		// Handle all post requests
		if (URLHelper.isPOSTRequest(request)) {
			String name = request.getParameter("name").toString();
			String toEmailId = request.getParameter("emailId").toString();
			String toAccount = request.getParameter("account_to").toString();
			String fromAccount = session.getAttribute("account_id").toString();

			status = this.userService.isValidAccount(Integer
					.parseInt(toAccount));
			if (status.getStatus().equals("error")) {
				attributes.addFlashAttribute("response", new Response("error",
						status.getMessage()));
				return "redirect:/user/requestPayment";
			}
			String toUserId = this.accountService
					.getAccountById(Integer.parseInt(toAccount)).getUser()
					.getUserId();
			User toUser = this.userService.getUserById(toUserId);

			// Validating the to_account & user details
			if (!toUser.getEmailId().equalsIgnoreCase(toEmailId)
					|| !toUser.getFname().concat(" " + toUser.getLname())
							.equals(name)) {
				attributes.addFlashAttribute("response", new Response("error",
						"Incorrect account details!!"));
				return "redirect:/user/requestPayment";
			}

			if (toAccount.equals(fromAccount)) {
				attributes.addFlashAttribute("response", new Response("error",
						"Cannot request payment to your own account!!"));
				return "redirect:/user/requestPayment";
			}

			if (!verifyEncryptedText(request)) {
				attributes.addFlashAttribute("response", new Response("error",
						"private key not verified!!"));
				return "redirect:/user/requestPayment";
			}

			String amount = request.getParameter("amount");
			status = this.transactionService.requestPayment(toAccount,
					fromAccount, amount);

			if (status.getStatus().equals("success")) {
				attributes.addFlashAttribute("response", status);
			} else {
				attributes.addFlashAttribute("response", status);
			}

			return "redirect:/user/requestPayment";
		}

		String userType = userService.getUserRole((String) session
				.getAttribute("emailId"));
		model.addAttribute("role", userType);
		model.addAttribute("contentView", "requestPayment");
		model.addAttribute("requestpayment", "active");
		return "user/template";

	}

	@RequestMapping(value = "/user/profile/edit")
	public String userEdit(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String userType = userService.getUserRole((String) session
				.getAttribute("emailId"));
		model.addAttribute("role", userType);
		model.addAttribute("contentView", "editprofile");
		UserAppModel u = new UserAppModel(
				this.userService.getUserByEmailId((String) session
						.getAttribute("emailId")));
		model.addAttribute("user", u);
		model.addAttribute("profile", "active");
		return "user/template";
	}

	// For update user profile
	@RequestMapping(value = "/user/profile/update", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String updateUserProfile(
			@ModelAttribute("user") @Valid UserAppModel u,
			BindingResult bindingResult, HttpServletRequest request,
			final RedirectAttributes attributes) {
		
		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
 		        String message = messageSource.getMessage(fieldError, null);
		        attributes.addFlashAttribute("response", new Response("error",
						message));
		        return "redirect:/user/profile/edit";
		    }
		}
			
		

		
			
		

		// get the responses from the user
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		Response status;
		// verify Captcha
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		// Redirect logic
		if (verifyStatus == true) {
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			User user = this.userService.getUserById(userId);

			if (user == null) {
				attributes.addFlashAttribute("response", new Response("error",
						"Invalid user profile!"));
				return "redirect:/user/profile/edit";
			} else {
				// Existing User, call update
				user = ValidationHelper.getUserFromUserAppModel(u, user);
				status = this.userService.updateUser(user);

				if (status.getStatus().equals("error")) {
					attributes.addFlashAttribute("response", status);
					return "redirect:/user/profile/edit";
				} else {
					session.setAttribute("emailId", u.getEmailId());
					attributes.addFlashAttribute("response", status);
					return "redirect:/user/profile";
				}
			}
		} else {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
			return "redirect:/user/profile/edit";
		}
	}

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		return "logout";
	}

	@RequestMapping(value = "/denied")
	public String denied() {
		return "denied";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex, 
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("denied");
		model.addObject("response", new Response("error", "Illegal Operation. Please go back & enter correct details."));
		return "redirect:/denied";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String listUsers(Model model) {
		model.addAttribute("user", new UserRegistrationModel());
		return "registration";
	}

	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.GET)
	public String passwordRecovery(Model model) {

		return "passwordRecovery";
	}

	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
	public String recoverPassword(Model model, HttpServletRequest request,
			final RedirectAttributes attributes) {
		// get email-id from user
		String emailId = request.getParameter("emailId").toString();
		// get user object
		User userObj = this.userService.getUserByEmailId(emailId);
		// Check if account exists
		if (userObj == null) {
			attributes.addFlashAttribute("response", new Response("error",
					"Account doesn't exist!, Try again"));
			return "redirect:/passwordRecovery";
		} else {
			// set user Id in the session
			HttpSession session = request.getSession();
			session.setAttribute("OTP-User-Id", userObj.getUserId());
			// send OTP
			otpService.sendOtp(this.userService.getUserByEmailId(emailId),
					emailId);
			String userId = session.getAttribute("OTP-User-Id").toString();
			model.addAttribute("question1", this.userService
					.getUserById(userId).getQues1());
			model.addAttribute("question2", this.userService
					.getUserById(userId).getQues2());
			model.addAttribute("question3", this.userService
					.getUserById(userId).getQues3());
			session.setAttribute("attempt", "0");
			return "setNewPassword";
		}
	}

	@RequestMapping(value = "/setNewPassword", method = RequestMethod.GET)
	public String setNewPassword(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("OTP-User-Id").toString();
		model.addAttribute("question1", this.userService.getUserById(userId)
				.getQues1());
		model.addAttribute("question2", this.userService.getUserById(userId)
				.getQues2());
		model.addAttribute("question3", this.userService.getUserById(userId)
				.getQues3());
		return "setNewPassword";
	}

	@RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
	public String setNewPasswordPost(HttpServletRequest request,
			final RedirectAttributes attributes, Model model) {
		HttpSession session = request.getSession();
		int i = Integer.parseInt(session.getAttribute("attempt").toString());
		i++;
		session.setAttribute("attempt", Integer.toString(i));
		System.out.println(session.getAttribute("attempt"));
		// get otp and passwords from user
		String newOtp = request.getParameter("One Time Password").toString();
		String newPassword = request.getParameter("New Password").toString();
		String renternewPassword = request
				.getParameter("Re-Enter New Password").toString();
		// verify otp
		String userId = session.getAttribute("OTP-User-Id").toString();
		Boolean otpMatch = otpService.verifyOtp(
				this.otpService.getUserotpById(userId), newOtp);
		// verify password match
		Boolean passwordMatch = (newPassword.equals(renternewPassword));
		// verify security questions
		String ans1 = request.getParameter("Answer1").toString();
		String ans2 = request.getParameter("Answer2").toString();
		String ans3 = request.getParameter("Answer3").toString();
		Boolean questionMatch = (ans1.equals(this.userService
				.getUserById(userId).getAnswer1().toString())
				&& ans2.equals(this.userService.getUserById(userId)
						.getAnswer2().toString()) && ans3
				.equals(this.userService.getUserById(userId).getAnswer3()
						.toString()));
		// block user based on attempts
		if (i > 3) {
			attributes
					.addFlashAttribute(
							"response",
							new Response("error",
									"Number of attempts reached! you have been temporarily blocked"));
			return "redirect:/setNewPassword";
		}
		// logic
		if (otpMatch == true && passwordMatch == true && questionMatch == true) {
			User obj = this.userService.getUserById(userId);
			obj.setPassword(CryptoHelper.getEncryptedString(newPassword));
			this.userService.updateUser(obj);
			return "login";
		} else if (otpMatch == false) {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong OTP!, Try again"));
			return "redirect:/setNewPassword";
		} else {
			attributes
					.addFlashAttribute(
							"response",
							new Response(
									"error",
									"passwords do not match or incorrect answers. Please go back to generate new One-Time Password and try again"));
			return "redirect:/setNewPassword";
		}
	}

	// authorise verify otp
	@RequestMapping(value = "/verifyOtp", method = RequestMethod.GET)
	public String verifyOtp() {

		return "verifyOtp";
	}

	@RequestMapping(value = "/verifyOtp", method = RequestMethod.POST)
	public String verifyOtpPost(HttpServletRequest request,
			final RedirectAttributes attributes) {
		// verify otp
		String otpPassword = request.getParameter("otpPassword");
		HttpSession session = request.getSession();
		String id = this.userService.getUserByEmailId(
				session.getAttribute("emailId").toString()).getUserId();
		Boolean otpMatch = otpService.verifyOtp(
				this.otpService.getUserotpById(id), otpPassword);
		if (otpMatch == true) {
			if (session.getAttribute("approveordecline").toString()
					.equals("approve")) {
				this.transactionService.updateAccessRequest(session
						.getAttribute("requestId").toString(), "approve");
				attributes.addFlashAttribute("response", new Response(
						"success", "Payment accepted!"));
				return "redirect:/verifyOtp";
			} else {
				this.transactionService.updateAccessRequest(session
						.getAttribute("requestId").toString(), session
						.getAttribute("approveordecline").toString());
				attributes.addFlashAttribute("response", new Response("error",
						"Payment rejected!"));
				return "redirect:/verifyOtp";
			}
		} else {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong one time password"));
			return "redirect:/verifyOtp";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(
			@ModelAttribute("user") @Valid UserRegistrationModel p,
			BindingResult bindingResult, final RedirectAttributes attributes) {

		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
 		        String message = messageSource.getMessage(fieldError, null);
		        attributes.addFlashAttribute("response", new Response("error",
						message));
				return "redirect:/registration";
		    }
		}
			
		

		if (p.getUserId() != null
				&& this.userService.getUserById(p.getUserId()) != null) {
			// Error. User Id was reused to add user.
			attributes.addFlashAttribute("response", new Response("error",
					"Account registration failed!!"));
			return "redirect:/registration";
		} else {
			if (!p.getPassword().equals(p.getConfirmPassword())) {
				attributes.addFlashAttribute("response", new Response("error",
						"Password & confirm password should be same."));
				return "redirect:/registration";
			}

			User u = ValidationHelper.getUserFromUserRegistrationModel(p,
					new User());
			Response status = this.userService.addUser(u);
			attributes.addFlashAttribute("response", status);
			return "redirect:/registration";
		}
	}

	private boolean verifyEncryptedText(HttpServletRequest request) {
		String randomString = request.getParameter("randomString");
		String encryptedtext = request.getParameter("encrypedString");
		boolean isCorrect = true;
		try {
			isCorrect = userService.verifyByDecrypting(randomString,
					encryptedtext);
			return isCorrect;
		} catch (Exception e) {
			return false;
		}
	}
}
