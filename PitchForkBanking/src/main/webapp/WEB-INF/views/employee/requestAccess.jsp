<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form:form method="POST" action = "employee/submitRequest" commandName="requestUser">
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
		
		<form:select name="mydropdown" path="type">
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




