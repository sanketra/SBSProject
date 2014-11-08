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
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script>
$(function() {
$( "#datepicker" ).datepicker();
});

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

	// Role
	var role = document.forms["myForm"]["role"].value;
	if (role == "Select Role") {
		alert("Select a role");
		return false;
	}
	// State
	var state = document.forms["myForm"]["state"].value;
	if (state == "select") {
		alert("Select a State");
		return false;
	}

	var phonePattern = /^[0-9]{3}\-?[0-9]{3}\-?[0-9]{4}$/;
	if (phonePattern.test(phoneno) != true) {
		alert("Enter a valid Phone number");
		return false;
	}
}
</script>

<h1 align="center">Employee Registration</h1>
<c:url var="addAction" value="/admin/admin_employeeRegistration"></c:url>
<div class="container" align="center">
	<form:form name="myForm" action="${addAction}" commandName="user"
		onsubmit="return validateForm()" class="form-horizontal">
		<form:hidden path="userId" />
		<table>
			<tr>
				<td>Email id</td>
				<td><form:input path="emailId" class="input-xxlarge"
						placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>First name</td>
				<td><form:input path="fname" class="input-xxlarge"
						placeholder="First Name" /></td>
			</tr>
			<tr>
				<td>Last name</td>
				<td><form:input path="lname" class="input-xxlarge"
						placeholder="Last Name" /></td>
			</tr>
			<tr>
				<td>Date of Birth</td>
				<td><form:input id="datepicker" path="dob"
						class="input-xxlarge" placeholder="mm/dd/yyyy" /></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><form:textarea rows="4" cols="50" path="address"
						class="input-xxlarge" placeholder="Address" /></td>
			</tr>
			<tr>
				<td>City</td>
				<td><form:input path="city" class="input-xxlarge"
						placeholder="City" /></td>
			</tr>

			<tr>
				<td>State</td>
				<td><form:select path="state" class="input-xxlarge">
						<option value="select">Select State</option>
						<option value="Alabama">Alabama</option>
						<option value="Alaska">Alaska</option>
						<option value="Arizona">Arizona</option>
						<option value="Arkansas">Arkansas</option>
						<option value="California">California</option>
						<option value="Colorado">Colorado</option>
						<option value="Connecticut">Connecticut</option>
						<option value="Delaware">Delaware</option>
						<option value="Florida">Florida</option>
						<option value="Georgia">Georgia</option>
						<option value="Hawaii">Hawaii</option>
						<option value="Idaho">Idaho</option>
						<option value="Illinois">Illinois</option>
						<option value="Indiana">Indiana</option>
						<option value="Iowa">Iowa</option>
						<option value="Kansas">Kansas</option>
						<option value="Kentucky">Kentucky</option>
						<option value="Louisiana">Louisiana</option>
						<option value="Maine">Maine</option>
						<option value="Maryland">Maryland</option>
						<option value="Massachusetts">Massachusetts</option>
						<option value="Michigan">Michigan</option>
						<option value="Minnesota">Minnesota</option>
						<option value="Mississippi">Mississippi</option>
						<option value="Missouri">Missouri</option>
						<option value="Montana">Montana</option>
						<option value="Nebraska">Nebraska</option>
						<option value="Nevada">Nevada</option>
						<option value="New Hampshire">New Hampshire</option>
						<option value="New Jersey">New Jersey</option>
						<option value="New Mexico">New Mexico</option>
						<option value="New York">New York</option>
						<option value="North Carolina">North Carolina</option>
						<option value="North Dakota">North Dakota</option>
						<option value="Ohio">Ohio</option>
						<option value="Oklahoma">Oklahoma</option>
						<option value="Oregon">Oregon</option>
						<option value="Pennsylvania">Pennsylvania</option>
						<option value="Rhode Island">Rhode Island</option>
						<option value="South Carolina">South Carolina</option>
						<option value="South Dakota">South Dakota</option>
						<option value="Tennessee">Tennessee</option>
						<option value="Texas">Texas</option>
						<option value="Utah">Utah</option>
						<option value="Vermont">Vermont</option>
						<option value="Virginia">Virginia</option>
						<option value="Washington">Washington</option>
						<option value="West Virginia">West Virginia</option>
						<option value="Wisconsin">Wisconsin</option>
						<option value="Wyoming">Wyoming</option>
						<option value="District of Columbia">District of Columbia</option>
						<option value="Puerto Rico">Puerto Rico</option>
						<option value="Guam">Guam</option>
						<option value="American Samoa">American Samoa</option>
						<option value="U.S. Virgin Islands">U.S. Virgin Islands</option>
						<option value="Northern Mariana Islands">Northern Mariana
							Islands</option>
					</form:select></td>
			</tr>


			<tr>
				<td>Zipcode</td>
				<td><form:input path="zipcode" class="input-xxlarge"
						placeholder="Zipcode" /></td>
			</tr>
			<tr>
				<td>SSN</td>
				<td><form:input path="ssn" class="input-xxlarge"
						placeholder="SSN" /></td>
			</tr>

			<tr>
				<td>Phone no.</td>
				<td><form:input path="phoneno" class="input-xxlarge"
						placeholder="xxx-xxx-xxxx" /></td>
			</tr>
			<tr>
				<td>Captcha</td>
				<td><script type="text/javascript">
					var RecaptchaOptions = {
						theme : 'clean'
					};
				</script> <script type="text/javascript"
						src="https://www.google.com/recaptcha/api/challenge?k=6LfLUv0SAAAAABLMjAJRm4vBHmiwQtn7ySuzfULk">
					
				</script>
					<noscript>
						<iframe
							src="https://www.google.com/recaptcha/api/noscript?k=6LfLUv0SAAAAABLMjAJRm4vBHmiwQtn7ySuzfULk"
							height="260" width="400"></iframe>
						<br>
						<textarea name="recaptcha_challenge_field" rows="3" cols="40">
       </textarea>
						<input type="hidden" name="recaptcha_response_field"
							value="manual_challenge">
					</noscript></td>
			</tr>

			<tr>
				<td rowspan="2"><input type="submit"
					value="<spring:message text="Register" />"
					class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>

