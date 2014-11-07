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


<h3 align="center">Pending Requests</h3>
<div class="container">
	<c:url var="selectAction" value="/admin/admin_newAccountRequests"></c:url>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>User First Name</th>
				<th>User Last Name</th>
				<th>Request Type</th>
				<th>Status</th>
				<th>Accept</th>
				<th>Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pendingAdditionalAccountRequests}" var="_requestu">
				<tr>
					<td>${_requestu.fname} </td>
					<td>${_requestu.lname} </td>
					<td>${_requestu.requestType}</td>
					<td>${_requestu.status} </td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="approve" value="${_requestu.requestId}" />
							<input type="submit" value="Approve" class="btn btn-success" />
						</form>
					</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="decline" value="${_requestu.requestId}" />
							<input type="submit" value="Decline" class="btn btn-danger" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>

