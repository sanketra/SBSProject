<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>

<div class="container">
	<c:url var="selectAction" value="/admin/admin_newUsers"></c:url>
	<table class="table table-bordered">
		<thead>
			<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email Id</th>
					<th>Role</th>
					<th>Register</th>
					<th>Delete</th>
			</tr>
		</thead>
<tbody>
			<c:forEach items="${newUsers}" var="user">
				<tr>
					<td>${user.fname}</td>
					<td>${user.lname}</td>
					<td>${user.emailId}</td>
					<td>${user.role}</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="approve" value="${user.userId}" />
							<input type="submit" value="Approve" class="btn btn-success" />
						</form>
					</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="decline" value="${user.userId}" />
							<input type="submit" value="Decline" class="btn btn-danger" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>
