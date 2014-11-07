package com.onlinebanking.helpers;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
	//referred to stack overflow to split url
	public static HashMap<String, String> analyseRequest(
			HttpServletRequest request) {
		//store parts of uri in hash map
		HashMap<String, String> uris = new HashMap<String, String>();
		
		String fullUri = request.getRequestURI();
		String appPath = "PitchForkBanking";
		String realUri = fullUri.replace(appPath, "");

		StringTokenizer st = new StringTokenizer(realUri, "/");
		//create tokens separated by /
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
}
