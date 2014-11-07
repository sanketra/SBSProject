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
			// TODO: Please configure to relative path.
			// Also update log4j.xml to log to a file specified by relative path.
			PropertyConfigurator.configure("C:\\Users\\user\\git\\SBSProject\\PitchForkBanking\\src\\main\\resources\\log4j.properties");																				
		}
		return instance;	
	}
	
	public void logRequest(HttpServletRequest request, String event	) {
		HttpSession session = request.getSession();
		log.info("*********************************");
		log.info("Email Id: " + session.getAttribute("emailId"));
		log.info("Event: " + event										);
		log.info("********************************");
	}

}
