<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<title>Home</title>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />"
	rel="stylesheet">
</head>
<body>
	<h1>Welcome to Pitch Fork Banking</h1>
	<P>
		<br> Hello :${fname}<br>
	</P>
	<br>
	<br>
	<table class="table table-bordered">
		<thead>
			<tr>
				<td>Bank Account ID</td>
				<td>Available Balance</td>
				<td>Account Type</td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${accounts}" var="_account">
				<tr>
					<td>${_account.accountNum}</td>
					<td><fmt:formatNumber value="${_account.amount}"
							type="currency" /></td>
					<td><c:choose>
							<c:when test="${_account.accountType == \"Checking\" }">Checking Account </c:when>
							<c:when test="${_account.accountType == \"Savings\" }">Saving Account </c:when>
							<c:otherwise> Undefined </c:otherwise>
						</c:choose></td>
					<td><a class="btn btn-success"
						href="${page.url_host}${page.url_apppath}profile?${_account.accountNum}">Select</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<br>
	<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>

	<form action="${logoutAction}" method="post">
		<input type="submit" value="Logout" />
	</form>
</body>
</html>