<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<c:url var="submitAction" value="/user/profile/update"></c:url>
<div class="container">
	<form:form action="${submitAction}" commandName="user"
		class="form-horizontal" method="POST">
		<form:hidden path="userId" />
		<form:hidden path="password" />
		<form:hidden path="role" />
		<form:hidden path="enabled" />
		<table class="table">
			<tr>
				<td>First Name</td>
				<td><form:input path="fname" class="input-xlarge"
						placeholder="First Name" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><form:input path="lname" class="input-xlarge"
						placeholder="Last Name" /></td>
			</tr>
			<tr>
				<td>SSN</td>
				<td><form:input path="ssn" class="input-xlarge"
						placeholder="SSN" /></td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td><form:input path="emailId" class="input-xlarge"
						placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><form:input path="address" class="input-xlarge"
						placeholder="Address" /></td>
			</tr>
			<tr>
				<td>City</td>
				<td><form:input path="city" class="input-xlarge"
						placeholder="City" /></td>
			</tr>
			<tr>
				<td>State</td>
				<td><form:input path="state" class="input-xlarge"
						placeholder="State" /></td>
			</tr>
			<tr>
				<td>Zipcode</td>
				<td><form:input path="zipcode" class="input-xlarge"
						placeholder="Zip code" /></td>
			</tr>
			<tr>
				<td>Phone Number</td>
				<td><form:input path="phoneno" class="input-xlarge"
						placeholder="Phone Number" /></td>
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
				<td colspan="2"><c:if test="${!empty user.emailId}">
						<input type="submit" value="<spring:message text="Submit" />"
							class="btn btn-lg btn-primary" />
					</c:if></td>
			</tr>
		</table>
	</form:form>
</div>
