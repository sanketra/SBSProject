package com.onlinebanking.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
	
	@RequestMapping(value = {"/admin", "/admin/*"}, method = RequestMethod.GET)
	public String handleAdminDashboardRequests(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", auth.getName());
		return "adminHome";
	}
	
	@RequestMapping(value = "/admin/post/{to_do}", method = RequestMethod.POST)
	public String handleAdminPostRequests(HttpServletRequest request, HttpServletResponse response
			,Model model, final RedirectAttributes redirectAttributes
			,@PathVariable String to_do
			){
		
		return "";
	}
}
