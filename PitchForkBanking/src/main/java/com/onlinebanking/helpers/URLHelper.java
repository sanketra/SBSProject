package com.onlinebanking.helpers;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class URLHelper {
	
	public static boolean isGETRequest(HttpServletRequest request) {
		Boolean returnVal = false;
		
		if (request.getMethod().toString().equals("GET")) {
			returnVal = true;
		}
		
		return returnVal;
	}

	public static boolean isPOSTRequest(HttpServletRequest request) {
		Boolean returnVal = false;
		
		if (request.getMethod().toString().equals("POST")) {
			returnVal = true;
		}
		
		return returnVal;
	}
	
	public static HashMap<String, String> analyseRequest(
			HttpServletRequest request) {

		HashMap<String, String> uris = new HashMap<String, String>();

		String fullUri = request.getRequestURI();
		String appPath = "PitchForkBanking";
		String realUri = fullUri.replace(appPath, "");

		StringTokenizer st = new StringTokenizer(realUri, "/");

		for (int i = 1; i <= 4; i++) {
			String _s;
			_s = (st.hasMoreTokens()) ? st.nextToken() : "";
			uris.put("url_" + i, _s.trim());
		}

		String type = request.getParameter("type");
		type = (type == null) ? "html" : type;
		uris.put("type", type);

		return uris;
	}
	
	public static void logRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("*********************************");
		System.out.println("Session Id: " + session.getId());
		System.out.println("User Id: " + session.getAttribute("userId"));
		System.out.println("Email Id: " + session.getAttribute("emailId"));
		System.out.println("My account Id: " + session.getAttribute("account_id"));
		System.out.println("********************************");
	}
}
