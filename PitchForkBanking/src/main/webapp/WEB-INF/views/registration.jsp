<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>



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
		// Answer 1
		var answer1 = document.forms["myForm"]["answer1"].value;
		if (answer1 == null || answer1 == "") {
			alert("Answer Should be filled out");
			return false;
		}
		// Answer 2
		var answer2 = document.forms["myForm"]["answer2"].value;
		if (answer2 == null || answer2 == "") {
			alert("Answer Should be filled out");
			return false;
		}
		// Answer 3
		var answer3 = document.forms["myForm"]["answer3"].value;
		if (answer3 == null || answer3 == "") {
			alert("Answer Should be filled out");
			return false;
		}
		// Role
		var role = document.forms["myForm"]["role"].value;
		if (role == "Select Role") {
			alert("Select a role");
			return false;
		}
		// question 1
		var ques1 = document.forms["myForm"]["ques1"].value;
		if (ques1 == "select") {
			alert("Select a Question");
			return false;
		}
		// question 2
		var ques2 = document.forms["myForm"]["ques2"].value;
		if (ques2 == "select") {
			alert("Select a Question");
			return false;
		}
		// question 3
		var ques3 = document.forms["myForm"]["ques3"].value;
		if (ques3 == "select") {
			alert("Select a Question");
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

<title>User Registration</title>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 800px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<link
	href="<c:url value="/resources/dist/css/bootstrap-responsive.css" />"
	rel="stylesheet">
</head>
<body>
	<h1 align="center">User Registration</h1>
	<c:url var="addAction" value="/add"></c:url>
	<div class="container" align="center">
		<c:if test="${!empty response}">
			<jsp:include page="common/response.jsp" />
		</c:if>
		<form:form name="myForm" action="${addAction}" commandName="user"
			onsubmit="return validateForm()" class="form-signin">
			<form:hidden path="userId" />
			<table>
				<tr>
					<td>Email Id</td>
					<td><form:input path="emailId" class="input-xxlarge"
							placeholder="Email Id" /></td>
				</tr>
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
					<td>Password<br/>(Must have 1 Special character,1 digit,1 UpperCase and 1 lowercase alphabet)</td>
					<td><form:input type="password" path="password"
							class="input-xxlarge" placeholder="Password: between 8 -15 chars only" /></td>
				</tr>
				<tr>
					<td>Confirm Password</td>
					<td><form:input type="password" path="confirmPassword"
							class="input-xxlarge" placeholder="Confirm Password" /></td>
				</tr>
				<tr>
					<td>Date of Birth</td>
					<td><form:input id="datepicker" path="dob"
							class="input-xxlarge" placeholder="mm/dd/yyyy" /></td>
				</tr>

				<tr>
					<td>Address</td>
					<td><form:textarea rows="4" cols="50" path="address"
							class="input-xxlarge" placeholder="Only letters and digits. No spaces"/></td>
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
							<option value="District of Columbia">District of
								Columbia</option>
							<option value="Puerto Rico">Puerto Rico</option>
							<option value="Guam">Guam</option>
							<option value="American Samoa">American Samoa</option>
							<option value="U.S. Virgin Islands">U.S. Virgin Islands</option>
							<option value="Northern Mariana Islands">Northern
								Mariana Islands</option>
						</form:select></td>
				</tr>


				<tr>
					<td>Zipcode</td>
					<td><form:input path="zipcode" class="input-xxlarge"
							placeholder="5 digits only" /></td>
				</tr>
				<tr>
					<td>SSN</td>
					<td><form:input path="ssn" class="input-xxlarge"
							placeholder="9 digits only" /></td>
				</tr>

				<tr>
					<td>Phone Number</td>
					<td><form:input path="phoneno" class="input-xxlarge"
							placeholder="10 digits only" /></td>
				</tr>

				<tr>
					<td>Role</td>
					<td><form:select path="role" class="input-xxlarge">
							<option>Select Role</option>
							<option value="User">User</option>
							<option value="Merchant">Merchant</option>
						</form:select></td>
				</tr>

				<tr>
					<td>Security Question 1</td>
					<td><form:select path="ques1" class="input-xxlarge">
							<option value="select">Select Question</option>
							<option
								value="What is the last name of the teacher who gave you your first failing grade?">What
								is the last name of the teacher who gave you your first failing
								grade?</option>
							<option
								value="What is the first name of the person you first kissed?">What
								is the first name of the person you first kissed?</option>
							<option
								value="What is the name of the place your wedding reception was held?">What
								is the name of the place your wedding reception was held?</option>
							<option
								value="In what city or town did you meet your spouse/partner?">In
								what city or town did you meet your spouse/partner?</option>
							<option value="What was the make and model of your first car?">What
								was the make and model of your first car?</option>
						</form:select></td>
				</tr>

				<tr>
					<td>Answer 1</td>
					<td><form:input path="answer1" class="input-xxlarge"
							placeholder="Answer(Alphabets and digits)" /></td>
				</tr>

				<tr>
					<td>Security Question 2</td>
					<td><form:select path="ques2" class="input-xxlarge">
							<option value="select">Select Question</option>
							<option value="What is your mothers maiden name">What is
								your mothers maiden name</option>
							<option value="What is your pets name">What is your pets
								name</option>
							<option value="What is your 1st grade teacher name">What
								is your 1st grade teacher name</option>
							<option value="What is your best friends name">What is
								your best friends name</option>
						</form:select></td>
				</tr>

				<tr>
					<td>Answer 2</td>
					<td><form:input path="answer2" class="input-xxlarge"
							placeholder="Answer(Alphabets and digits)" /></td>
				</tr>

				<tr>
					<td>Security Question 3</td>
					<td><form:select path="ques3" class="input-xxlarge">
							<option value="select">Select Question</option>
							<option
								value="What was the name of your elementary / primary school?">What
								was the name of your elementary / primary school?</option>
							<option
								value="In what city or town does your nearest sibling live?">In
								what city or town does your nearest sibling live?</option>
							<option
								value="What was the name of your first stuffed animal or doll or action figure?">What
								was the name of your first stuffed animal or doll or action
								figure?</option>
							<option value="What time of the day were you born?">What
								time of the day were you born?</option>
							<option value="What was your favorite place to visit as a child?">What
								was your favorite place to visit as a child?</option>
						</form:select></td>
				</tr>

				<tr>
					<td>Answer 3</td>
					<td><form:input path="answer3" class="input-xxlarge"
							placeholder="Answer(Alphabets and digits)" /></td>
				</tr>


				<tr>
					<td rowspan="2">
							<input type="submit" value="<spring:message text="Register" />"
								class="btn btn-lg btn-primary" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	<div class="container" align="center">
		<a href="${pageContext.request.contextPath}/login">Login into
			PitchForkBanking</a>
	</div>
	<br>
	<br>
</body>
</html>