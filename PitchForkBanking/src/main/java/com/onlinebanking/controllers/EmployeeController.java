package com.onlinebanking.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.PKI;
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
	private MessageSource messageSource;
	
	
	@Autowired(required = true)
	@Qualifier(value = "messageSource")
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

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
	public String editUserProfile(HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		if(request.getParameter("email_Id")!=null)
		{
			String selectedOperation = request.getParameter("submit");
			String selectedRecord = request.getParameter("email_Id");
			HttpSession session = request.getSession();
			session.setAttribute("selectedOperation", selectedOperation);
			session.setAttribute("selectedRecord", selectedRecord);
			String randomString = PKI.generateRandomString();
			model.addAttribute("randomString", randomString);
			model.addAttribute("contentView", "publicKeyVerificationProfile");
			return "employee/emp_template";
		}
		else
		{
			attributes.addFlashAttribute("response", new Response("error", "Select row to proceed"));
			return "redirect:/employee/user_details";
		}
		
	}
	

	
	@RequestMapping(value="/employee/verifiedEncryptedTextUserProfile", method = RequestMethod.POST)
	public String verifyEncryptedText(HttpServletRequest request, Model model, final RedirectAttributes attributes) 
	{
		String randomString = request.getParameter("randomString");
		String encryptedtext = request.getParameter("encrypedString");
		boolean isCorrect = true;
		try
		{
			isCorrect = userService.verifyByDecrypting(randomString, encryptedtext);
		}
		catch(Exception e)
		{
			isCorrect = false;
		}
		if(isCorrect)
		{
			HttpSession session = request.getSession();
			String operation = (String)session.getAttribute("selectedOperation");
			String emailId = (String)session.getAttribute("selectedRecord");
			session.removeAttribute("selectedOperation");
			session.removeAttribute("selectedRecord");
			if(operation!=null && !operation.isEmpty())
			{
				if(operation.equalsIgnoreCase("delete"))
				{
					
					User u = userService.getUserByEmailId(emailId);
					if(u!=null)
					{
						userService.removeUser(u.getUserId());
						attributes.addFlashAttribute("response", new Response("success", "deleted user"));
						return "redirect:/employee/user_details";
					}
				}
				else if(operation.equalsIgnoreCase("update"))
				{
					User u = userService.getUserByEmailId(emailId);
					if(u!=null)
					{
						UserAppModel userAppModel = new UserAppModel(u);
						transactionService.deleteProfileRequest(u);
						model.addAttribute("userProfile", userAppModel);
						model.addAttribute("contentView", "updateUserProfile");
						return "employee/emp_template";
					}
				}
			}
			attributes.addFlashAttribute("response", new Response("error", "Select row to proceed"));
			return "redirect:/employee/user_details"; 
		}
		else
		{
			model.addAttribute("response", new Response("error", "Encrypted string not proper"));
			String newRandomString = PKI.generateRandomString();
			model.addAttribute("randomString", newRandomString);
			model.addAttribute("contentView", "publicKeyVerificationProfile"); 
			return "employee/emp_template";
		}
	}
	
	@RequestMapping(value="/employee/updateUserProfile", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("userProfile") @Valid UserAppModel userAppModel,BindingResult bindingResult, HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{

		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
 		        String message = messageSource.getMessage(fieldError, null);
		        model.addAttribute("response", new Response("error",message));
		        model.addAttribute("userProfile", userAppModel);
				model.addAttribute("contentView", "updateUserProfile");
				return "employee/emp_template";
		    }
		}
		
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
	public String submitEmployeeRequest(@ModelAttribute("userRequest") @Valid UserRequest userRequest,BindingResult bindingResult,Model model, HttpServletRequest request, final RedirectAttributes attributes) {
		
		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
 		        String message = messageSource.getMessage(fieldError, null);
		        model.addAttribute("response", new Response("error",message));
		        model.addAttribute("userRequest", userRequest);
				model.addAttribute("contentView", "requestAccess");
				return "employee/emp_template";
		    }
		}
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
	public String employeeTransaction(Model model, HttpServletRequest request){
		List<UserRequest> userRequests = transactionService.getApprovedTransactionRequestsFromUser();
		HttpSession session = request.getSession();
		session.removeAttribute("selectedAccountId");
		model.addAttribute("userList", userRequests);
		model.addAttribute("contentView", "acc_details");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/viewUserTransactions", method = RequestMethod.POST)
	public String viewUserTransactions(HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		List<Transaction> transactions = new ArrayList<Transaction>();
		try
		{
			String accountId = request.getParameter("account_id");
			if(accountId == null || accountId.isEmpty())
			{
				HttpSession session = request.getSession();
				accountId = (String)session.getAttribute("selectedAccountId");
				if(accountId == null || accountId.isEmpty())
				{
					attributes.addFlashAttribute("response", new Response("error", "Select account to proceed"));
					return "redirect:/employee/account_details";
				}
			}

			transactions = transactionService.getAllTransactionsForAccountId(Integer.parseInt(accountId));
			HttpSession session = request.getSession();
			session.setAttribute("selectedAccountId", accountId);
			transactionService.deleteTransactionRequest(Integer.parseInt(accountId));
			model.addAttribute("transactionList", transactions);
			model.addAttribute("contentView", "viewUserTransactions");
			return "employee/emp_template";	
		}
		catch(Exception e)
		{
			attributes.addFlashAttribute("response", new Response("error", "Select account to proceed"));
			return "redirect:/employee/account_details";
			
		}	
	}
	
	@RequestMapping(value="/employee/editUserTransaction", method = RequestMethod.POST)
	public String editUserTransaction(HttpServletRequest request, Model model, final RedirectAttributes attributes)
	{
		String transactionId = request.getParameter("transaction_id");
		if(transactionId==null || transactionId.isEmpty())			
		{
			model.addAttribute("response", new Response("error", "Select transaction to proceed"));
			return viewUserTransactions(request,model,attributes);
		}
		try
		{
			String selectedOperation = request.getParameter("submit");
			HttpSession session = request.getSession();
			session.setAttribute("selectedOperation", selectedOperation);
			session.setAttribute("selectedTransaction", transactionId);
			String randomString = PKI.generateRandomString();
			model.addAttribute("randomString", randomString);
			model.addAttribute("contentView", "publicKeyVerificationTransaction");
			return "employee/emp_template";
		}
		catch(Exception e)
		{
			attributes.addFlashAttribute("response", new Response("error", "Error occurred"));
			return "redirect:/employee/account_details";
		}

	}
	
	
	@RequestMapping(value="/employee/verifyEncryptedTextTransaction", method = RequestMethod.POST)
	public String verifyEncryptedTextTransaction(HttpServletRequest request, Model model, final RedirectAttributes attributes) 
	{
		String randomString = request.getParameter("randomString");
		String encryptedtext = request.getParameter("encrypedString");
		boolean isCorrect = true;
		try
		{
			isCorrect = userService.verifyByDecrypting(randomString, encryptedtext);
		}
		catch(Exception e)
		{
			isCorrect = false;
		}
		if(isCorrect)
		{
			HttpSession session = request.getSession();
			String operation = (String)session.getAttribute("selectedOperation");
			String transactionId = (String)session.getAttribute("selectedTransaction");
			if(operation!=null && !operation.isEmpty())
			{
				if(operation.equalsIgnoreCase("delete"))
				{
					
					Transaction transaction = transactionService.getTransaction(transactionId);
					if(transaction!=null)
					{	
						try
						{
							transactionService.deleteTransaction(transaction);
							attributes.addFlashAttribute("response", new Response("success", "deleted transaction"));
							session.removeAttribute("selectedOperation");
							session.removeAttribute("selectedTransaction");
							session.removeAttribute("selectedAccountId");
							return "redirect:/employee/account_details";
						}
						catch(Exception e)
						{
							attributes.addFlashAttribute("response", new Response("error", "error occurred while deleting"));
							System.out.println(e);
							return viewUserTransactions(request,model,attributes);
						}
					}
					else
					{
						attributes.addFlashAttribute("response", new Response("error", "error occurred while deleting"));
						return "redirect:/employee/viewUserTransactions";
					}
				}
				else if(operation.equalsIgnoreCase("update"))
				{
					Transaction transaction = transactionService.getTransaction(transactionId);
					if(transaction!=null)
					{
						
						TransactionAppModel transactionAppModel = new TransactionAppModel(transaction);
						model.addAttribute("userTransaction", transactionAppModel);
						model.addAttribute("contentView", "updateUserTransactions");
						return "employee/emp_template";
					}
					else
					{
						attributes.addFlashAttribute("response", new Response("error", "error occurred while fetching transaction"));
						return "redirect:/employee/viewUserTransactions";
					}
				}
			}
			attributes.addFlashAttribute("response", new Response("error", "Select row to proceed"));
			return "redirect:/employee/viewUserTransactions"; 
		}
		else
		{
			model.addAttribute("response", new Response("error", "Encrypted string not proper"));
			String newRandomString = PKI.generateRandomString();
			model.addAttribute("randomString", newRandomString);
			model.addAttribute("contentView", "publicKeyVerificationTransaction"); 
			return "employee/emp_template";
		}
	}

	
	@RequestMapping(value="/employee/updateUserTransaction", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("userTransaction") @Valid TransactionAppModel transactionAppModel,BindingResult bindingResult, HttpServletRequest request,Model model, final RedirectAttributes attributes)
	{
		for (Object object : bindingResult.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
 		        String message = messageSource.getMessage(fieldError, null);
		        model.addAttribute("response", new Response("error",message));
		        model.addAttribute("userTransaction", transactionAppModel);
				model.addAttribute("contentView", "updateUserTransactions");
				return "employee/emp_template";
		    }
		}
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
				HttpSession session = request.getSession();
				session.removeAttribute("selectedOperation");
				session.removeAttribute("selectedTransaction");
				session.removeAttribute("selectedAccountId");
				attributes.addFlashAttribute("response", new Response("success", "updated transaction"));
				return "redirect:/employee/account_details";
			}
			else
			{
				model.addAttribute("response", new Response("error",
						"Wrong captcha, please try again!"));
				model.addAttribute("userTransaction", transactionAppModel);
				model.addAttribute("contentView", "updateUserTransactions");
				return "employee/emp_template";
			}
		}
		catch(Exception e)
		{
			attributes.addFlashAttribute("response", new Response("error", "error updating transactions"));
			model.addAttribute("userTransaction", transactionAppModel);
			model.addAttribute("contentView", "updateUserTransactions");
			return "employee/emp_template";
		}

	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex, 
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView("denied");
		model.addObject("Response", new Response("error", "Exception"));
		return "redirect:/denied";
	}
}

	
