<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page session="false"%>
<html>
<head>

<title>Employee List</title>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">
</head>
<body>
	<h3 align="center">Employee List</h3>

	<div class="container">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email Id</th>
					<th>Role</th>
					<th>Delete</th>
				</tr>
			</thead>
			<c:forEach items="${listEmployees}" var="user">
				<tr>
					<td>${user.fname}</td>
					<td>${user.lname}</td>
					<td>${user.emailId}</td>
					<td>${user.role}</td>
					<td><a href="<c:url value='remove/${user.userId}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
		<a href="${pageContext.request.contextPath}/admin/employee_registration"
			rel="nofollow">Register</a><br> <br>
	</div>



</body>
</html>