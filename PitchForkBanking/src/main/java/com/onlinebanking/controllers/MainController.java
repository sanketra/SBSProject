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

import com.onlinebanking.models.Customer;
import com.onlinebanking.services.CustomerService;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private CustomerService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(CustomerService ps){
		this.personService = ps;
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
	public String listPersons(Model model) {
		model.addAttribute("person", new Customer());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	//For add and update person both
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Customer p){
		
		if(this.personService.getPersonById(p.getId()) == null){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/registration";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") String id){
        this.personService.removePerson(id);
        return "redirect:/registration";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") String id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
}
