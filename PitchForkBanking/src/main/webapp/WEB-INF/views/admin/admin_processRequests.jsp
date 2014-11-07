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
	<c:url var="selectAction" value="/admin/admin_processRequests"></c:url>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Employee Name</th>
				<th>User First Name</th>
				<th>User Last Name</th>
				<th>Request Type</th>
				<th>Status</th>
				<th>Accept</th>
				<th>Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pendingUserRequests}" var="_requestp">
				<tr>
					<td>${_requestp.employeeName}</td>
					<td>${_requestp.fname} </td>
					<td>${_requestp.lname} </td>
					<td>${_requestp.requestType}</td>
					<td>${_requestp.status} </td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="approve" value="${_requestp.requestId}" />
							<input type="submit" value="Approve" class="btn btn-success" />
						</form>
					</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="decline" value="${_requestp.requestId}" />
							<input type="submit" value="Decline" class="btn btn-danger" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>

<h3 align="center">Approved Requests</h3>
<div class="container">
	<c:if test="${!empty approvedUserRequests}">
		<table class="table table-bordered">
			<thead>
			<tr>
				<th>Employee Name</th>
				<th>User First Name</th>
				<th>User Last Name</th>
				<th>Request Type</th>
				<th>Status</th>
			</tr>
		</thead>
			<c:forEach items="${approvedUserRequests}" var="_requesta">
			<tr>
					<td>${_requesta.employeeName}</td>
					<td>${_requesta.fname} </td>
					<td>${_requesta.lname} </td>
					<td>${_requesta.requestType}</td>
					<td>${_requesta.status} </td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
</div>


<h3 align="center">Declined Requests</h3>
<div class="container">
	<c:if test="${!empty declinedUserRequests}">
		<table class="table table-bordered">
			<thead>
			<tr>
				<th>Employee Name</th>
				<th>User First Name</th>
				<th>User Last Name</th>
				<th>Request Type</th>
				<th>Status</th>
			</tr>
		</thead>
			<c:forEach items="${declinedUserRequests}" var="_requestd">
			<tr>
					<td>${_requestd.employeeName}</td>
					<td>${_requestd.fname} </td>
					<td>${_requestd.lname} </td>
					<td>${_requestd.requestType}</td>
					<td>${_requestd.status} </td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
</div>

