<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>

<aside id="side_bar">
	<ul id="emp_menu" class="nav nav-tabs nav-stacked">
		<li><a 
			href="${page.url_host}${page.url_apppath}user_details">User Details</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}req_Access">Request Access</a></li>
		<li><a
			href="${page.url_host}${page.url_apppath}account_details">Account</a></li>
		
	</ul>
</aside>

