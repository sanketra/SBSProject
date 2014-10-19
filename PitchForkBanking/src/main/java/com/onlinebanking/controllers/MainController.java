package com.onlinebanking.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.onlinebanking.models.User;
import com.onlinebanking.services.UserService;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String handleSuccessFullAuthRequest(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String handleRequest(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", auth.getName());
		return "home";
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
		return "user";
	}
	
	//For add and update person both
	@RequestMapping(value= "/user/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User p){
		
		if(this.userService.getUserById(p.getId()) == null){
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
        return "user";
    }
}
