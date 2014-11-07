<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<title>PitchFork Bank - Edit Profile</title>
<!-- Validation script -->
<script>
	function validateForm() {
		// First Name
		var fname = document.forms["myForm"]["fname"].value;
		if (fname == null || fname == "") {
			alert("First name must be filled out");
			return false;
		}
		var fnamePattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (fnamePattern1.test(fname) == true) {
			alert('Special characters are not allowed in First Name');
			return false;
		}
		var fnamePattern2 = /^[a-zA-Z ]*$/;
		if (fnamePattern2.test(fname) != true) {
			alert('Only alphabets are allowed in Name');
			return false;
		}
		// Last Name
		var lname = document.forms["myForm"]["lname"].value;
		if (lname == null || lname == "") {
			alert("Last name must be filled out");
			return false;
		}
		var lnamePattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (lnamePattern1.test(lname) == true) {
			alert('Special characters are not allowed in Last Name');
			return false;
		}
		var lnamePattern2 = /^[a-zA-Z ]*$/;
		if (lnamePattern2.test(lname) != true) {
			alert('Only alphabets are allowed in Name');
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
			alert("Special characters are not allowed in Address");
			return false;
		}
		// City
		var city = document.forms["myForm"]["city"].value;
		if (city == null || city == "") {
			alert("City must be filled out");
			return false;
		}
		var cityPattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (cityPattern1.test(city) == true) {
			alert('Special characters are not allowed in City');
			return false;
		}
		var cityPattern2 = /^[a-zA-Z ]*$/;
		if (cityPattern2.test(city) != true) {
			alert('Only alphabets are allowed in City');
			return false;
		}
		// State
		var state = document.forms["myForm"]["state"].value;
		if (state == null || state == "") {
			alert("State must be filled out");
			return false;
		}
		var statePattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (statePattern1.test(state) == true) {
			alert('Special characters are not allowed in State');
			return false;
		}
		var statePattern2 = /^[a-zA-Z ]*$/;
		if (statePattern2.test(state) != true) {
			alert('Only alphabets are allowed in State');
			return false;
		}
		// Zipcode
		var zipcode = document.forms["myForm"]["zipcode"].value;
		if (zipcode == null || zipcode == "") {
			alert("Zipcode must be filled out");
			return false;
		}
		var zipPattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (zipPattern1.test(zipcode) == true) {
			alert('Special characters are not allowed in Zipcode');
			return false;
		}
		var zipPattern2 = /^[0-9]+$/;
		if (zipPattern2.test(zipcode) != true) {
			alert('Only numbers are allowed in Zipcode');
			return false;
		}
		// Phone Number
		var phoneno = document.forms["myForm"]["phoneno"].value;
		if (phoneno == null || phoneno == "") {
			alert("Phone number must be filled out");
			return false;
		}
		var phonePattern1 = /^[0-9]{3}\-?[0-9]{3}\-?[0-9]{4}$/;
		if (phonePattern1.test(phoneno) != true) {
			alert("Enter a valid Phone number");
			return false;
		}
		var phonePattern2 = /^[0-9]+$/;
		if (phonePattern2.test(phoneno) != true) {
			alert('Only numbers are allowed in Phone Number');
			return false;
		}
	}
</script>

<c:url var="submitAction" value="/user/profile/update"></c:url>
<div class="container">
	<form:form name="myForm" action="${submitAction}"
		onsubmit="return validateForm()" commandName="user"
		class="form-horizontal" method="POST">
		<table class="table borderless">
			<tr>
				<td>First Name</td>
				<td><form:input path="fname" class="input-xxlarge"
						placeholder="First Name" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><form:input path="lname" class="input-xxlarge"
						placeholder="Last Name" /></td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td><form:input path="emailId" class="input-xxlarge"
						placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><form:input path="address" class="input-xxlarge"
						placeholder="Address" /></td>
			</tr>
			<tr>
				<td>City</td>
				<td><form:input path="city" class="input-xxlarge"
						placeholder="City" /></td>
			</tr>
			<tr>
				<td>State</td>
				<td><form:input path="state" class="input-xxlarge"
						placeholder="State" /></td>
			</tr>
			<tr>
				<td>Zipcode</td>
				<td><form:input path="zipcode" class="input-xxlarge"
						placeholder="Zip code" /></td>
			</tr>
			<tr>
				<td>Phone Number</td>
				<td><form:input path="phoneno" class="input-xxlarge"
						placeholder="Phone Number" /></td>
			</tr>
			<tr>
				<td>Captcha</td>
				<td><script type="text/javascript">
					var RecaptchaOptions = {
						theme : 'clean'
					};
				</script> <script type="text/javascript"
						src="https://www.google.com/recaptcha/api/challenge?k=6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS">
					
				</script>
					<noscript>
						<iframe
							src="https://www.google.com/recaptcha/api/noscript?k=6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS"
							height="260" width="400"></iframe>
						<br>
						<textarea name="recaptcha_challenge_field" rows="3" cols="40">
       </textarea>
						<input type="hidden" name="recaptcha_response_field"
							value="manual_challenge">
					</noscript></td>
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
