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
		return "employee/emp_home";
	}
	
	@RequestMapping(value="/employee/account_details")
	public String employeeAcc(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		return "employee/acc_details";
	}
	
	@RequestMapping(value="/employee/req_Access")
	public String employeeRequest(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		return "employee/requestAccess";
	}
	
	
}
