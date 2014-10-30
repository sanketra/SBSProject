package com.onlinebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class AdminController {
	
	private UserService userService;
	private AccountService accountService;
	private TransactionService transactionService;
	
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

	@RequestMapping(value = {"/admin", "/admin/*"}, method = RequestMethod.GET)
	public String handleAdminDashboardRequest(Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	model.addAttribute("name", auth.getName());
	model.addAttribute("contentView","admin_home");
	return "admin/admin_template";
	}
/*	
	@RequestMapping(value = "/admin/post/{to_do}", method = RequestMethod.POST)
	public String handleAdminPostRequests(HttpServletRequest request, HttpServletResponse response
			,Model model, final RedirectAttributes redirectAttributes
			,@PathVariable String to_do
			){
		
		return "";
	}
	*/
	
	@RequestMapping(value="/admin/customerList")
	public String customerList(Model model){
		model.addAttribute("listUsers", this.userService.listCustomers());
		model.addAttribute("contentView", "admin_customerList");
		return "admin/admin_template";
	}
	
	@RequestMapping(value="/admin/employeeList")
	public String employeeList(Model model){
		model.addAttribute("listEmployees", this.userService.listEmployees());
		model.addAttribute("contentView", "admin_employeeList");
		return "admin/admin_template";
	}
	
	//Incomplete
	@RequestMapping(value="/admin/processRequests")
	public String processRequests(Model model){
		
		
		model.addAttribute("contentView", "admin_processRequests");
		return "admin/admin_template";
	}
	
}
