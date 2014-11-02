<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account details</title>
</head>
<body>
<div class = "container">
<c:url var="selectAction" value="/admin/admin_customerList"></c:url>
<form action="${selectAction}" class="form-signin" method="POST">
<table class="table table-bordered">
		<thead>
			<tr>
				<th>Role</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email Id</th>
				<th>Select</th>
			</tr>
		</thead>
		<c:forEach items="${listUsers}" var="user">
			<tr>
					<td>${user.role}</td>
					<td>${user.fname}</td>
					<td>${user.lname}</td>
					<td>${user.emailId}</td>
					<td><input type="radio" name="email_Id"
							value="${user.emailId}" /></td>
			</tr>
			</c:forEach>
</table>
<input type="submit" name="submit" value="Update" class="btn btn-lg btn-primary" />
<input type="submit" name="submit" value="Delete" class="btn btn-lg btn-primary" />
</form>	
</div>	

</body>
</html>