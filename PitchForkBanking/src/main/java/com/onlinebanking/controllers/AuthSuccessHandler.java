package com.onlinebanking.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.onlinebanking.models.Role;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0,
			HttpServletResponse arg1, Authentication arg2) throws IOException,
			ServletException {
		String redirectUrl = "/PitchForkBanking/user/home";
		
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(Role.ADMIN)) {
				redirectUrl = "/PitchForkBanking/admin";
			}
			else if(authority.getAuthority().equals(Role.EMPLOYEE))
			{
				redirectUrl = "/PitchForkBanking/employee/emp_home";
			}
			else {
				redirectUrl = "/PitchForkBanking/user/home";
			}
		}
		
		arg1.sendRedirect(redirectUrl);
	}
}
