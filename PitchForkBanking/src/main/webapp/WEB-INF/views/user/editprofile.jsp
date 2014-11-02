<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<!-- Validation script -->
<title>PitchFork Bank - Edit Profile</title>
<script>
	function validateForm() {
		// First Name
		var fname = document.forms["myForm"]["fname"].value;
		if (fname == null || fname == "") {
			alert("First name must be filled out");
			return false;
		}
		// Last Name
		var lname = document.forms["myForm"]["lname"].value;
		if (lname == null || lname == "") {
			alert("Last name must be filled out");
			return false;
		}
		// SSN
		var ssn = document.forms["myForm"]["ssn"].value;
		if (ssn == null || ssn == "") {
			alert("SSN must be filled out");
			return false;
		}
		var ssnPattern = /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/;
		if (ssnPattern.test(ssn) != true) {
			alert("Enter a valid SSN");
			return false;
		}
		// Email_Id
		var emailId = document.forms["myForm"]["emailId"].value;
		if (emailId == null || emailId == "") {
			alert("Email-Id must be filled out");
			return false;
		}
		var emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if (emailPattern.test(emailId) != true) {
			alert("Enter a valid Email-ID");
			return false;
		}
		// Address
		var address = document.forms["myForm"]["address"].value;
		if (address == null || address == "") {
			alert("Address must be filled out");
			return false;
		}
		var addressPattern = /[*|\":<>[\]{}`\\()';@&$]/;
		if (addressPattern.test(address) == true) {
			alert("Special characters cannot be included in the address");
			return false;
		}
		// City
		var city = document.forms["myForm"]["city"].value;
		if (city == null || city == "") {
			alert("City must be filled out");
			return false;
		}
		// State
		var state = document.forms["myForm"]["state"].value;
		if (state == null || state == "") {
			alert("State must be filled out");
			return false;
		}
		// Zipcode
		var zipcode = document.forms["myForm"]["zipcode"].value;
		if (zipcode == null || zipcode == "") {
			alert("Zipcode must be filled out");
			return false;
		}
		// Phone Number
		var phoneno = document.forms["myForm"]["phoneno"].value;
		if (phoneno == null || phoneno == "") {
			alert("Phone number must be filled out");
			return false;
		}
		var phonePattern = /^[0-9]{3}\-?[0-9]{3}\-?[0-9]{4}$/;
		if (phonePattern.test(phoneno) != true) {
			alert("Enter a valid Phone number");
			return false;
		}
	}
</script>

<c:url var="submitAction" value="/user/profile/update"></c:url>
<div class="container">
	<form:form name="myForm" action="${submitAction}"
		onsubmit="return validateForm()" commandName="user"
		class="form-horizontal" method="POST">
		<form:hidden path="userId" />
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
					<noscript>
						<iframe
							src="http://www.google.com/recaptcha/api/noscript?k=6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS"
							height="260" width="400" frameborder="0"></iframe>
						<br>
						<textarea name="recaptcha_challenge_field" rows="3" cols="40">
       </textarea>
						<input type="hidden" name="recaptcha_response_field"
							value="manual_challenge">
					</noscript>
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
