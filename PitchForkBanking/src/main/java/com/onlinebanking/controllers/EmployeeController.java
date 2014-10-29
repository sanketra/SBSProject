package com.onlinebanking.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
//import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {
	
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
	
	@RequestMapping(value="/employee/req_Access")
	public String employeeRequest(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		model.addAttribute("contentView","requestAccess");
		return "employee/emp_template";
	}
	
	@RequestMapping(value="/employee/account_details")
	public String employeeTransaction(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		model.addAttribute("contentView", "acc_details");
		return "employee/emp_template";
	}
	
	
}
