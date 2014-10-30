package com.onlinebanking.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.helpers.URLHelper;
import com.onlinebanking.helpers.ValidationHelper;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
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
		URLHelper.logRequest(request);
		HttpSession session = request.getSession();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		User u = this.userService.getUserByEmailId(auth.getName());
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("emailId", u.getEmailId());
		// TODO: Account
		model.addAttribute("accounts",
				this.accountService.getUserAccounts(u.getUserId()));
		model.addAttribute("fname", u.getFname());
		return "user/home";
	}

	@RequestMapping(value = { "/user/*", "/user/*/*", "/user/*/*/*" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String handleDashboardRequest(Model model,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes attributes) {
		URLHelper.logRequest(request);

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

				status = this.userService.isValidAccount(Integer
						.parseInt(toAccount));
				if (status.getStatus().equals("error")) {
					attributes.addFlashAttribute("response", new Response(
							"error", status.getMessage()));
					return "redirect:/user/transfer";
				}
				String toUserId = this.accountService
						.getAccountById(Integer.parseInt(toAccount)).getUser()
						.getUserId();
				User toUser = this.userService.getUserById(toUserId);

				// Validating the to_account & user details
				if (!toUser.getEmailId().equalsIgnoreCase(toEmailId)
						|| !toUser.getFname().concat(" " + toUser.getLname())
								.equals(name)) {
					attributes.addFlashAttribute("response", new Response(
							"error", "Incorrect account details!!"));
					return "redirect:/user/transfer";
				}

				if (toAccount.equals(fromAccount)) {
					attributes.addFlashAttribute("response", new Response(
							"error",
							"Cannot transfer to currently selected account!!"));
					return "redirect:/user/transfer";
				}

				String amount = request.getParameter("amount");
				status = this.transactionService.createTransaction(fromAccount,
						toAccount, amount, TransactionType.TRANSFER);

				if (status.getStatus().equals("success")) {
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", status);
				}
				return "redirect:/user/transfer";
			} else if (urls.get("url_2").toString().equals("credit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				String amount = request.getParameter("amount").toString();
				status = this.transactionService.createTransaction(fromAccount,
						fromAccount, amount, TransactionType.CREDIT);
				if (status.getStatus().equals("success")) {
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", status);
				}
				return "redirect:/user/credit";
			} else if (urls.get("url_2").toString().equals("debit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				String amount = request.getParameter("amount").toString();
				status = this.transactionService.createTransaction(fromAccount,
						fromAccount, amount, TransactionType.DEBIT);
				if (status.getStatus().equals("success")) {
					attributes.addFlashAttribute("response", status);
				} else {
					attributes.addFlashAttribute("response", status);
				}
				return "redirect:/user/debit";
			} else if (urls.get("url_2").toString().equals("authorize")) {
				// TODO: Authorize flow.
				return "redirect:/user/authorize";
			}
		}

		// Handle all get requests
		if (urls.get("url_2").toString().equals("transfer")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "transfer");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("credit")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "credit");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("debit")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "debit");
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
			return "user/template";
		} else if (urls.get("url_2").toString().equals("authorize")) {
			User u = this.userService.getUserByEmailId((String)session.getAttribute("emailId"));
			model.addAttribute("role", u.getRole());
			// TODO: Transaction
			List<Requests> list  = this.transactionService.getAllRequestsToUser(u.getUserId());
			model.addAttribute("requests", list);
			model.addAttribute("contentView", "authorize");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("profile")) {
			String userType = userService.getUserRole((String) session
					.getAttribute("emailId"));
			model.addAttribute("role", userType);
			model.addAttribute("contentView", "profile");
			UserAppModel u = new UserAppModel(this.userService.getUserByEmailId((String) session.getAttribute("emailId")));
			model.addAttribute("user", u);
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
		URLHelper.logRequest(request);
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
			} else {
				// error
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
		return "user/template";

	}

	@RequestMapping(value = "/user/requestPayment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String merchantRequestPayment(HttpServletRequest request,
			Model model, final RedirectAttributes attributes) {
		URLHelper.logRequest(request);
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
		return "user/template";

	}

	@RequestMapping(value = "/user/profile/edit")
	public String userEdit(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String userType = userService.getUserRole((String) session
				.getAttribute("emailId"));
		model.addAttribute("role", userType);
		model.addAttribute("contentView", "editprofile");
		UserAppModel u = new UserAppModel(this.userService.getUserByEmailId((String) session.getAttribute("emailId")));
		model.addAttribute("user", u);

		return "user/template";
	}

	// For add and update person both
	@RequestMapping(value = "/user/profile/update", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String addUserProfile(@ModelAttribute("user") UserAppModel u,
			HttpServletRequest request, final RedirectAttributes attributes) {

		// get the responses from the user
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		// verify Captcha
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		// Redirect logic
		if (verifyStatus == true) {
			User user = this.userService.getUserById(u.getUserId());
			
			if (user == null) {
				attributes.addFlashAttribute("response", new Response("error",
						"Invalid user profile!"));
				return "redirect:/user/profile/edit";
			} else {
				// Existing User, call update
				user = ValidationHelper.getUserFromUserAppModel(u, user);
				this.userService.updateUser(user);
			}
		}
		// Wrong Captcha
		else {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
			return "redirect:/user/profile/edit";
		}
		attributes.addFlashAttribute("response", new Response("success",
				"Profile edited successflly!"));
		return "redirect:/user/profile";
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

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String listUsers(Model model) {
		// TODO: User
		model.addAttribute("user", new User());
		// TODO: Needs to be removed.
		model.addAttribute("listUsers", this.userService.listUsers());
		return "registration";
	}

	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.GET)
	public String passwordRecovery(Model model) {

		return "passwordRecovery";
	}

	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
	public String recoverPassword(HttpServletRequest request,
			final RedirectAttributes attributes) {
		// get email-id from user
		String emailId = request.getParameter("emailId").toString();
		// get user object
		User userObj = this.userService.getUserByEmailId(emailId);
		// Check if account exists
		if (userObj == null) {
			attributes.addFlashAttribute("response", new Response("error",
					"Account doesn't exist !, Try again"));
			return "redirect:/passwordRecovery";
		} else {
			// set user Id in the session
			HttpSession session = request.getSession();
			session.setAttribute("OTP-User-Id", userObj.getUserId());
			System.out.println(emailId);
			System.out.println(userObj.getUserId());
			// send otp
			otpService.sendOtp(this.userService.getUserByEmailId(emailId),
					emailId);
			return "setNewPassword";
		}
	}

	@RequestMapping(value = "/setNewPassword", method = RequestMethod.GET)
	public String setNewPassword(Model model) {

		return "setNewPassword";
	}

	@RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
	public String setNewPasswordPost(HttpServletRequest request,
			final RedirectAttributes attributes) {
		// get otp from user
		String newOtp = request.getParameter("One Time Password").toString();
		// send otp
		HttpSession session = request.getSession();
		String userId = session.getAttribute("OTP-User-Id").toString();
		Boolean result = otpService.verifyOtp(
				this.otpService.getUserotpById(userId), newOtp);
		if (result == true) {
			attributes.addFlashAttribute("response", new Response("success",
					"OTP verified, Password reset"));
		} else {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong OTP"));
		}
		return "redirect:/setNewPassword";
	}

	// For add and update person both
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User p,
			final RedirectAttributes attributes) {

		if (this.userService.getUserById(p.getUserId()) == null) {
			// new person, add it
			this.userService.addUser(p);
		} else {
			// existing person, call update
			this.userService.updateUser(p);
		}

		attributes.addFlashAttribute("response", new Response("success",
				"Account registration successful!!"));
		return "redirect:/registration";

	}

	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable("id") String id) {
		this.userService.removeUser(id);
		return "redirect:/registration";
	}

	@RequestMapping(value = "/header")
	public String header(HttpServletRequest request, Model model) {
		return "header";
	}
}
