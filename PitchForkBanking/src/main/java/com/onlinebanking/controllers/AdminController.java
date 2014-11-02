package com.onlinebanking.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class AdminController {
	
	private UserService userService;
	@SuppressWarnings("unused")
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
	
	@RequestMapping(value="/admin/customerList", method = RequestMethod.GET)
	public String customerList(Model model){
		model.addAttribute("listUsers", this.userService.listCustomers());
		model.addAttribute("contentView", "admin_customerList");
		return "admin/admin_template";
	}
	
	//Incomplete======================Incorrect --- approve/decline -- it should be accept/delete blah blah
	@RequestMapping(value="/admin/admin_customerList", method = RequestMethod.POST)
	public String customerAccept(Model model, HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes attributes){
		Response status;
		if (request.getParameter("approve") != null) {
			status = this.transactionService.updateAccessRequest(request.getParameter("approve"), "approve");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/customerList";
		} else if (request.getParameter("decline") != null) {
			status = this.transactionService.updateAccessRequest(request.getParameter("decline"), "decline");
			attributes.addFlashAttribute("response", status);
			return "redirect:/admin/customerList";
		} 
		
		return "redirect:/admin/customerList";
	}

	
	
	@RequestMapping(value="/admin/employeeList")
	public String employeeList(Model model){
		model.addAttribute("listEmployees", this.userService.listEmployees());
		model.addAttribute("contentView", "admin_employeeList");
		return "admin/admin_template";
	}
	
	
	@RequestMapping(value="/admin/employee_registration")
	public String employeeRegitration(Model model){
		model.addAttribute("contentView", "employee_registration");
		return "admin/admin_template";
	}
	
	//Incomplete
	@RequestMapping(value="/admin/processRequests", method = RequestMethod.GET)
	public String accessRequests(Model model){
		
		List<UserRequest> pendingRequests = transactionService.getPendingRequests();
		List<UserRequest> approvedRequests = transactionService.getApprovedRequests();
		List<UserRequest> declinedRequests = transactionService.getDeclinedRequests();
		model.addAttribute("pendingUserRequests", pendingRequests);
		model.addAttribute("approvedUserRequests",approvedRequests);
		model.addAttribute("declinedUserRequests",declinedRequests);
		
		model.addAttribute("contentView", "admin_processRequests");
	
		return "admin/admin_template";

	}
	
		@RequestMapping(value="/admin/admin_processRequests", method = RequestMethod.POST)
		public String processRequests(Model model, HttpServletRequest request, HttpServletResponse response,
				final RedirectAttributes attributes){
			Response status;
			if (request.getParameter("approve") != null) {
				status = this.transactionService.updateAccessRequest(request.getParameter("approve"), "approve");
				attributes.addFlashAttribute("response", status);
				return "redirect:/admin/processRequests";
			} else if (request.getParameter("decline") != null) {
				status = this.transactionService.updateAccessRequest(request.getParameter("decline"), "decline");
				attributes.addFlashAttribute("response", status);
				return "redirect:/admin/processRequests";
			} 
			
			return "redirect:/admin/processRequests";
		}
	
}
