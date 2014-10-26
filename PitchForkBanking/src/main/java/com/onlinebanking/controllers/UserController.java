package com.onlinebanking.controllers;

import java.util.Date;
import java.util.HashMap;

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

import com.onlinebanking.helpers.URLHelper;
import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.User;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class UserController {
	private UserService userService;
	private AccountService accountService;
	private TransactionService transactionService;
	
	@Autowired(required=true)
	@Qualifier(value="transactionService")
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

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
	
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String handleRequest(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = this.userService.getUserByEmailId(auth.getName());
		model.addAttribute("accounts", this.accountService.getUserAccounts(u.getUserId()));
		session.setAttribute("emailId", u.getEmailId());
		model.addAttribute("fname", u.getFname());
		return "user/home";
	}
	
	@RequestMapping(value = {"/user/*", "/user/*/*", "/user/*/*/*"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String handleDashboardRequest(Model model, HttpServletRequest request, HttpServletResponse response) {
		URLHelper.logRequest(request);
		
		HashMap<String, String> urls = URLHelper.analyseRequest(request);
		HttpSession session = request.getSession();
		
		// Handle all post requests
		if (URLHelper.isPOSTRequest(request)) {
			String name = request.getParameter("name").toString();
			String toAccount = request.getParameter("account_to").toString();
			String toEmailId = request.getParameter("emailId").toString();
			int amount = Integer.parseInt(request.getParameter("amount").toString());
			String from = session.getAttribute("account_id").toString();
			System.out.println(name +" " + toAccount + " " + toEmailId + " " + amount + " " + from);
			Transaction t = new Transaction();
			t.setAccountByFromAcountNum(this.accountService.getAccountById(Integer.parseInt(from)));
			t.setAccountByToAccountNum(this.accountService.getAccountById(Integer.parseInt(toAccount)));
			t.setTransactionAmount(amount);
			t.setTransactionStatus("success");
			t.setTransactionType("credit");
			t.setTransactionTime(new Date());
			this.transactionService.addTransaction(t);
			return "redirect:/user/transfer";
		}
		
		
		// Handle all get requests
		if (urls.get("url_2").toString().equalsIgnoreCase("transfer")) {
			model.addAttribute("contentView", "transfer");
			return "user/template";
		} else {
			int account_id = 0;
			
			if (urls.get("url_3") != null && !urls.get("url_3").toString().equals("")) {
				account_id = Integer.parseInt(urls.get("url_3"));
				System.out.println("My Account: " + account_id);
				session.setAttribute("account_id", account_id);
			}
			
			model.addAttribute("contentView", "profile");
			model.addAttribute("user", this.userService.getUserByEmailId((String)session.getAttribute("emailId")));
			return "user/template";
		}
	}
	
	@RequestMapping(value="/user/profile/edit")
	public String userEdit(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		model.addAttribute("contentView", "editprofile");
		model.addAttribute("user", this.userService.getUserByEmailId((String)session.getAttribute("emailId")));
		return "user/template";
	}
	
	//For add and update person both
	@RequestMapping(value= "/user/profile/update", method = {RequestMethod.GET, RequestMethod.POST})
	public String addUserProfile(@ModelAttribute("user") User p){
		
		if(this.userService.getUserById(p.getUserId()) == null){
			//new person, add it
			this.userService.addUser(p);
		}else{
			//existing person, call update
			this.userService.updateUser(p);
		}
		
		return "redirect:/user/profile";
		
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
		return "registration";
	}
	
	//For add and update person both
	@RequestMapping(value= "/add", method = RequestMethod.POST)
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
    
	@RequestMapping(value="/header")
	public String header(HttpServletRequest request, Model model){
		return "header";
	}
}
