package com.onlinebanking.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
			Properties props = new Properties();
			try {
				props.load(new FileInputStream("log4j.properties"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PropertyConfigurator.configure(props);
			// TODO: Please configure to relative path.
			// Also update log4j.xml to log to a file specified by relative path.
			//PropertyConfigurator.configure("C:\\Users\\Administrator\\Documents\\GitHub\\git\\SBSProject\\PitchForkBanking\\src\\main\\resources\\log4j.properties");
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
