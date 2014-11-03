<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>
<script>
	function validateForm() {
		// First Name
		var fname = document.forms["requestForm"]["fname"].value;
		if (fname == null || fname == "") {
			alert("First name must be filled out");
			return false;
		}
		// Last Name
		var lname = document.forms["requestForm"]["lname"].value;
		if (lname == null || lname == "") {
			alert("Last name must be filled out");
			return false;
		}

		// Email_Id
		var emailId = document.forms["requestForm"]["emailId"].value;
		if (emailId == null || emailId == "") {
			alert("Email-Id must be filled out");
			return false;
		}
		var emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if (emailPattern.test(emailId) != true) {
			alert("Enter a valid Email-ID");
			return false;
		}
		
	}
</script>

<c:url var="submitAction" value="/employee/submitRequest"></c:url>
<div class="container" align="right">
<form:form name="requestForm" action = "${submitAction}" onsubmit="return validateForm()" commandName="userRequest" method="POST">
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
				<td>Captcha</td>
				<td>
					<%
						ReCaptcha c = ReCaptchaFactory
									.newReCaptcha(
											"6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS",
											"6LdU5vsSAAAAAPAyZqM1Bx3Kh12wdMvimkjC5Xqpyour_private_key",
											false);
							out.print(c.createRecaptchaHtml(null, null));
					%>
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




