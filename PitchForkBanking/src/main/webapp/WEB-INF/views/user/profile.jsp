<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="editAction" value="/user/profile/edit"></c:url>
<div class="container">
	<form:form action="${editAction}" commandName="user"
		class="form-horizontal">
		<table class="table table-striped">
			<tr>
				<td>First Name</td>
				<td>${user.fname}</td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td>${user.lname}</td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td>${user.emailId}</td>
			</tr>
			<tr>
				<td>Address</td>
				<td>${user.address}</td>
			</tr>
			<tr>
				<td>City</td>
				<td>${user.city}</td>
			</tr>
			<tr>
				<td>State</td>
				<td>${user.state}</td>
			</tr>
			<tr>
				<td>Zipcode</td>
				<td>${user.zipcode}</td>
			</tr>
			<tr>
				<td>Phone Number</td>
				<td>${user.phoneno}</td>
			</tr>
			<tr>
				<td colspan="2"><c:if test="${!empty user.emailId}">
						<input type="submit"
							value="<spring:message text="    Edit    " />"
							class="btn btn-lg btn-primary" />
					</c:if> <c:if test="${empty user.emailId}">
						<input type="submit" value="<spring:message text="  Submit  " />"
							class="btn btn-lg btn-primary" />
					</c:if></td>
			</tr>
		</table>
	</form:form>
</div>
