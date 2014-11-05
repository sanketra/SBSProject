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

<h1 align="center">Employee Registration</h1>
	<c:url var="addAction" value="/admin/admin_employeeRegistration"></c:url>
	<div class="container" align="center">
<%-- 		<c:if test="${!empty response}">
			<jsp:include page="common/response.jsp" />
		</c:if> --%>
		<form:form name="myForm" action="${addAction}" commandName="user"
			onsubmit="return validateForm()" class="form-horizontal">
			<form:hidden path="userId" />
			<table>
				<tr>
					<td><form:label path="emailId">
							<spring:message text="Email Id" />
						</form:label></td>
					<td><form:input path="emailId" class="input-xxlarge"
							placeholder="Email Id" /></td>
				</tr>
				<tr>
					<td><form:label path="fname">
							<spring:message text="First Name" />
						</form:label></td>
					<td><form:input path="fname" class="input-xxlarge"
							placeholder="First Name" /></td>
				</tr>
				<tr>
					<td><form:label path="lname">
							<spring:message text="Last Name" />
						</form:label></td>
					<td><form:input path="lname" class="input-xxlarge"
							placeholder="Last Name" /></td>
				</tr>
				<tr>
					<td><form:label path="password">
							<spring:message text="Password" />
						</form:label></td>
					<td><form:input type="password" path="password"
							class="input-xxlarge" placeholder="Password" /></td>
				</tr>
				<tr>
					<td><form:label path="dob">
							<spring:message text="Date and Time" />
						</form:label></td>
					<td><form:input id="datepicker" path="dob"
							class="input-xxlarge" /></td>
				</tr>

				<tr>
					<td><form:label path="address">
							<spring:message text="Address" />
						</form:label></td>
					<td><form:textarea rows="4" cols="50" path="address"
							class="input-xxlarge" placeholder="Address" /></td>
				</tr>

				<tr>
					<td><form:label path="city">
							<spring:message text="City" />
						</form:label></td>
					<td><form:input path="city" class="input-xxlarge"
							placeholder="City" /></td>
				</tr>

				<tr>
					<td><form:label path="state">
							<spring:message text="State" />
						</form:label></td>
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
					<td><form:label path="zipcode">
							<spring:message text="Zipcode" />
						</form:label></td>
					<td><form:input path="zipcode" class="input-xxlarge"
							placeholder="Zipcode" /></td>
				</tr>
				<tr>
					<td><form:label path="ssn">
							<spring:message text="SSN" />
						</form:label></td>
					<td><form:input path="ssn" class="input-xxlarge"
							placeholder="SSN" /></td>
				</tr>

				<tr>
					<td><form:label path="phoneno">
							<spring:message text="Phone No." />
						</form:label></td>
					<td><form:input path="phoneno" class="input-xxlarge"
							placeholder="xxx-xxx-xxxx" /></td>
				</tr>

				<tr>
					<td><form:label path="role">
							<spring:message text="Role" />
						</form:label></td>
					<td><form:select path="role" class="input-xxlarge">
							<option>Select Role</option>
							<option value="Employee">Regular Employee</option>
							<option value="Manager">Manager</option>
						</form:select></td>
				</tr>

				<tr>
					<td><form:label path="ques1">
							<spring:message text="Security Question 1  " />
						</form:label></td>
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
					<td><form:label path="answer1">
							<spring:message text="Answer 1" />
						</form:label></td>
					<td><form:input path="answer1" class="input-xxlarge"
							placeholder="Answer" /></td>
				</tr>

				<tr>
					<td><form:label path="ques2">
							<spring:message text="Security Question 2  " />
						</form:label></td>
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
					<td><form:label path="answer2">
							<spring:message text="Answer 2" />
						</form:label></td>
					<td><form:input path="answer2" class="input-xxlarge"
							placeholder="Answer" /></td>
				</tr>

				<tr>
					<td><form:label path="ques3">
							<spring:message text="Security Question 3  " />
						</form:label></td>
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
					<td><form:label path="answer3">
							<spring:message text="Answer 3" />
						</form:label></td>
					<td><form:input path="answer3" class="input-xxlarge"
							placeholder="Answer" /></td>
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
					<td rowspan="2">
							<input type="submit" value="<spring:message text="Register" />"
								class="btn btn-lg btn-primary" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>

