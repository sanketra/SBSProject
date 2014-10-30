package com.onlinebanking.controllers;

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
import com.onlinebanking.models.UserRequest;
import com.onlinebanking.services.TransactionService;

@Controller
public class EmployeeController {
	private TransactionService transactionService;
	
	@Autowired(required = true)
	@Qualifier(value = "transactionService")
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
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
		model.addAttribute("userRequest", new UserRequest());
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
	public String employeeTransaction(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		model.addAttribute("contentView", "acc_details");
		return "employee/emp_template";
	}
	
}
