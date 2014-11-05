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
<title>View User Transactions</title>
</head>
<body>

<div class = "container">
<c:url var="selectAction" value="/admin/admin_editUserTransaction"></c:url>
<form action="${selectAction}" class="form-signin" method="POST">
<table class="table table-bordered">
		<thead>
			<tr>
				<th>From Account</th>
				<th>To Account</th>
				<th>Transaction Type</th>
				<th>Amount</th>
				<th>Transaction Time</th>
				<th>Status</th>
				<th>Select</th>
			</tr>
		</thead>
		<c:forEach items="${transactionList}" var="transaction">
			<tr>
					<td>${transaction.accountByFromAcountNum.accountNum}</td>
					<td>${transaction.accountByToAccountNum.accountNum}</td>
					<td>${transaction.transactionType}</td>
					<td>${transaction.transactionAmount}</td>
					<td>${transaction.transactionTime}</td>
					<td>${transaction.transactionStatus}</td>
					<td><input type="radio" name="transaction_id"
							value="${transaction.transactionId}" /></td>
			</tr>
			</c:forEach>
</table>
<input type="submit" name="submit" value="Update" class="btn btn-lg btn-primary" />
<input type="submit" name="submit" value="Delete" class="btn btn-lg btn-primary" />
</form>	
</div>	
</body>
</html>