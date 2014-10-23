package com.onlinebanking.controllers;

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

import com.onlinebanking.models.Account;
import com.onlinebanking.models.User;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.UserService;

@Controller
public class UserController {
	private UserService userService;
	private AccountService accountService;
	
	@Autowired(required=true)
	@Qualifier(value="accountService")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String handleSuccessFullAuthRequest(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/user_home", method = RequestMethod.GET)
	public String handleRequest(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = this.userService.getUserByEmailId(auth.getName());
		Account a = new Account();
		a.setAccountNum(1002);
		a.setAccountType("Checking");
		a.setAmount(1000);
		a.setUser(u);
		//this.accountService.addAccount(a);
		model.addAttribute("accounts", this.accountService.getUserAccounts(u.getUserId()));
		session.setAttribute("emailId", u.getEmailId());
		model.addAttribute("fname", u.getFname());
		return "user/user_home";
	}
	
	@RequestMapping(value = {"/user_home/*", "/user_home/*/*"}, method = RequestMethod.GET)
	public String handleDashboardRequest(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		model.addAttribute("user", this.userService.getUserByEmailId((String)session.getAttribute("emailId")));
		return "user/account_details";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, Model model){
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout(){
		return "logout";
	}
	
	@RequestMapping(value="/denied")
	public String denied(){
		return "denied";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", this.userService.listUsers());
		return "user_registration";
	}
	
	//For add and update person both
	@RequestMapping(value= "/user/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User p){
		
		if(this.userService.getUserById(p.getUserId()) == null){
			//new person, add it
			this.userService.addUser(p);
		}else{
			//existing person, call update
			this.userService.updateUser(p);
		}
		
		return "redirect:/registration";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") String id){
        this.userService.removeUser(id);
        return "redirect:/registration";
    }
 
    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") String id, Model model){
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("listUsers", this.userService.listUsers());
        return "user_home";
    }
    
	@RequestMapping(value="/header")
	public String header(HttpServletRequest request, Model model){
		return "header";
	}
	
}
