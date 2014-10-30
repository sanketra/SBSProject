package com.onlinebanking.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.TransactionService;

@Controller
public class EmployeeController {
	private TransactionService transactionService;
	private AccountService accountService;
	
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
	
	@RequestMapping(value = {"/employee", "/employee/*"}, method = RequestMethod.GET)
	public String handleAdminDashboardRequests(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", auth.getName());
		model.addAttribute("contentView","emp_home");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/user_details")
	public String employeeAcc(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		model.addAttribute("contentView", "user_details");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/req_Access", method = RequestMethod.GET)
	public String employeeRequest(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<UserRequest> userRequests = transactionService.getAllPendingRequests();
		model.addAttribute("userRequest", new UserRequest());
		model.addAttribute("pendingUserRequests", userRequests);
		model.addAttribute("contentView","requestAccess");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/submitRequest", method = RequestMethod.POST)
	public String submitEmployeeRequest(@ModelAttribute("userRequest") UserRequest userRequest, final RedirectAttributes attributes) {
		
		Response result = transactionService.addRequest(userRequest);
		
		attributes.addFlashAttribute("response", result);
		
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
	public String editUserTransaction(HttpServletRequest request, Model model)
	{
		if(request.getParameter("submit").equalsIgnoreCase("delete"))
		{
			
			
		}
		else if(request.getParameter("submit").equalsIgnoreCase("update"))
		{
			String transactionId = request.getParameter("transaction_id");
			Transaction transaction = transactionService.getTransaction(transactionId);
			model.addAttribute("transaction", transaction);
			model.addAttribute("contentView", "updateUserTransactions");
			return "employee/emp_template";
		}
		return "redirect:/employee/viewUserTransactions";
	}
	
	@RequestMapping(value="/employee/updateUserTransaction", method = RequestMethod.POST)
	public String updateUserTransaction(@ModelAttribute("transaction") Transaction transaction, final RedirectAttributes attributes)
	{
		transactionService.updateTransaction(transaction);
		attributes.addAttribute("response", new Response("success", "updated transaction"));
		return "redirect:/employee/viewUserTransactions";
	}
}
