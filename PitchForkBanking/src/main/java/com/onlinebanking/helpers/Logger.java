package com.onlinebanking.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.onlinebanking.controllers.UserController;

public class Logger {

	public static Logger instance;
	private static final Log log = LogFactory.getLog(UserController.class);

	private Logger() {
	}

	public static Logger getinstance() {
		if (instance == null) {
			instance = new Logger();
			PropertyConfigurator.configure("C:\\MyFiles\\FALL 2014\\SS\\Project\\PitchForkBanking\\src\\main\\resources\\log4j.properties");
		}
		return instance;
	}
	
	public void logRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		log.info("*********************************");
		log.info("Session Id: " + session.getId());
		log.info("User Id: " + session.getAttribute("userId"));
		log.info("Email Id: " + session.getAttribute("emailId"));
		log.info("My account Id: " + session.getAttribute("account_id"));
		log.info("********************************");
	}

}
