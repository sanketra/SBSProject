<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<aside id="side_bar">
	<ul id="emp_menu" class="nav nav-tabs nav-stacked">
		<li><a 
			href="${page.url_host}${page.url_apppath}account_details">Account Details</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}req_Access">Request Access</a></li>
		<li><a
			href="${page.url_host}${page.url_apppath}acco">Account</a></li>
		
	</ul>
</aside>

</body>
</html>