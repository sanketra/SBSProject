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
<title>Request Access</title>
</head>
<body>

<div class = "container">
<c:url var="selectAction" value="/admin/admin_accountTransactions"></c:url>
<form action="${selectAction}" class="form-signin" method="POST">
<table class="table table-bordered">
		<thead>
			<tr>
				<th>Account Number</th>
				<th>Account Holder</th>
				<th>Select</th>
			</tr>
		</thead>
		<c:forEach items="${userAccounts}" var="accounts">
			<tr>
					<td>${accounts.accountNum}</td>
					<td>${accounts.user.fname} ${accounts.user.lname}</td>
					<td><input type="radio" name="account_id"
							value="${accounts.accountNum}" /></td>
			</tr>
			</c:forEach>
</table>
<input type="submit" value="Select" class="btn btn-lg btn-primary" />
</form>	
</div>	
</body>
</html>