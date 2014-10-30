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
<c:url var="submitAction" value="/employee/submitRequest"></c:url>
<div class="container" align="right">
<form:form name="requestForm" action = "${submitAction}" commandName="userRequest" method="POST">
<table class = "table">
<tr>
	<td>First Name:</td>
	<td><form:input path="fname" class="input-xlarge"
						placeholder="First Name" /></td>
	
</tr>
<tr>
	<td>Last Name:</td>
	<td><form:input path="lname" class="input-xlarge"
						placeholder="Last Name" /></td>
</tr>
<tr>
	<td>Email Id:</td>
	<td><form:input path="emailId" class="input-xlarge"
						placeholder="Email Id" /></td>
</tr>
<tr>
	<td>Request Type</td>
	<td>	
		<form:select name="mydropdown" path="requestType">
			<option value="Profile">Profile</option>
			<option value="Transaction">Transaction</option>
		</form:select>
	</td>
	
</tr>
<tr>
	<td><input type="submit" value="<spring:message text="Submit" />"
							class="btn btn-lg btn-primary" /></td>
</tr>
</table>
</form:form>
</div>
<br>
<br>
<h3 align="center">Pending Requests</h3>
<div class="container">
	<c:if test="${!empty pendingUserRequests}">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email Id</th>
					<th>Request Type</th>
				</tr>
			</thead>
			<c:forEach items="${pendingUserRequests}" var="userRequest">
			<tr>
					<td>${userRequest.fname}</td>
					<td>${userRequest.lname}</td>
					<td>${userRequest.emailId}</td>
					<td>${userRequest.requestType}</td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
</div>




