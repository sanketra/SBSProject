<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

.borderless tbody tr td, .borderless thead tr th {
	border: none;
}
</style>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid" align="right">
		<jsp:include page="../common/header.jsp" />
	</div>
	<div class="container-fluid" align="left">
		<c:if test="${!empty response}">
			<jsp:include page="../common/response.jsp" />
		</c:if>
	</div>
	<br>
	<br>
	<div class="container">
	<c:url var="selectAction" value="/user/profile"></c:url>
	<form action="${selectAction}" class="form-horizontal" method="POST">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Bank Account ID</th>
					<th>Available Balance</th>
					<th>Account Type</th>
					<th>Select</th>
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
						<td><input type="radio" name="account_id"
							value="${_account.accountNum}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="submit" value="Select" class="btn btn-lg btn-primary" />
	</form>
	</div>
	<br>
	<br>
	<div class="container-fluid" align="left">
		<jsp:include page="../common/footer.jsp" />
	</div>
</body>
</html>
