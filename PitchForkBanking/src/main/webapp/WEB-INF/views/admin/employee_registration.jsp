<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>



<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
  <script>
  $(document).ready(function() {
    $("#datepicker").datepicker();
  });

  function validateForm() {
		// First Name
	    var fname = document.forms["myForm"]["fname"].value;
	    if (fname==null || fname=="") {
	        alert("First name must be filled out");
	        return false;
	    }
	    // Last Name
	    var lname = document.forms["myForm"]["lname"].value;
	    if (lname==null || lname=="") {
	        alert("Last name must be filled out");
	        return false;
	    }
	    // SSN
	    var ssn = document.forms["myForm"]["ssn"].value;
	    if (ssn==null || ssn=="") {
	        alert("SSN must be filled out");
	        return false;
	    }
	    var ssnPattern = /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/;
	    if(ssnPattern.test(ssn)!=true){
	    	alert("Enter a valid SSN");
	    	return false;
	    }
	    // Email_Id
	    var emailId = document.forms["myForm"]["emailId"].value;
	    if (emailId==null || emailId=="") {
	        alert("Email-Id must be filled out");
	        return false;
	    }
	    var emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    if(emailPattern.test(emailId)!=true){
	    	alert("Enter a valid Email-ID");
	    	return false;
	    }
	    // Address
	    var address = document.forms["myForm"]["address"].value;
	    if (address==null || address=="") {
	        alert("Address must be filled out");
	        return false;
	    }
	    var addressPattern = /[*|\":<>[\]{}`\\()';@&$]/;
	    if(addressPattern.test(address)==true){
	    	alert("Special characters cannot be included in the address");
	    	return false;
	    }
	    // City
	    var city = document.forms["myForm"]["city"].value;
	    if (city==null || city=="") {
	        alert("City must be filled out");
	        return false;
	    }
	    // State
	    var state = document.forms["myForm"]["state"].value;
	    if (state==null || state=="") {
	        alert("State must be filled out");
	        return false;
	    }
	    // Zipcode
	    var zipcode = document.forms["myForm"]["zipcode"].value;
	    if (zipcode==null || zipcode=="") {
	        alert("Zipcode must be filled out");
	        return false;
	    }
	    // Phone Number
	    var phoneno = document.forms["myForm"]["phoneno"].value;
	    if (phoneno==null || phoneno=="") {
	        alert("Phone number must be filled out");
	        return false;
	    }
	    // Answer 1
	    var answer1 = document.forms["myForm"]["answer1"].value;
	    if (answer1==null || answer1=="") {
	        alert("Answer Should be filled out");
	        return false;
	    }
	    // Answer 2
	    var answer2 = document.forms["myForm"]["answer2"].value;
	    if (answer2==null || answer2=="") {
	        alert("Answer Should be filled out");
	        return false;
	    }
	    // Answer 3
	    var answer3 = document.forms["myForm"]["answer3"].value;
	    if (answer3==null || answer3=="") {
	        alert("Answer Should be filled out");
	        return false;
	    }
	    // Role
	    var role = document.forms["myForm"]["role"].value;
	    if (role=="Select Role") {
	        alert("Select a role");
	        return false;
	    }
	 	// question 1
	    var ques1 = document.forms["myForm"]["ques1"].value;
	    if (ques1=="select") {
	        alert("Select a Question");
	        return false;
	    }
	 	// question 2
	    var ques2 = document.forms["myForm"]["ques2"].value;
	    if (ques2=="select") {
	        alert("Select a Question");
	        return false;
	    }
	 	// question 3
	    var ques3 = document.forms["myForm"]["ques3"].value;
	    if (ques3=="select") {
	        alert("Select a Question");
	        return false;
	    }
	 	// State
	    var state = document.forms["myForm"]["state"].value;
	    if (state=="select") {
	        alert("Select a State");
	        return false;
	    }
	    
	    var phonePattern = /^[0-9]{3}\-?[0-9]{3}\-?[0-9]{4}$/;
	    if(phonePattern.test(phoneno)!=true){
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
	max-width: 400px;
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
	<h1 align="center">Employee Registration</h1>
	<c:url var="addAction" value="/addEmployee"></c:url>
	<div class="container" align="right">
		<jsp:include page="/WEB-INF/views/common/response.jsp" />
		<form:form name="myForm" action="${addAction}" commandName="user" onsubmit="return validateForm()"
			class="form-signin">
			<form:hidden path="userId" />
			<table>
				<tr>
					<td><form:label path="emailId">
							<spring:message text="Email Id" />
						</form:label></td>
					<td><form:input path="emailId" class="input-xlarge"
							placeholder="Email Id" /></td>
				</tr>
				<tr>
					<td><form:label path="fname">
							<spring:message text="First Name" />
						</form:label></td>
					<td><form:input path="fname" class="input-xlarge"
							placeholder="First Name" /></td>
				</tr>
				<tr>
					<td><form:label path="lname">
							<spring:message text="Last Name" />
						</form:label></td>
					<td><form:input path="lname" class="input-xlarge"
							placeholder="Last Name" /></td>
				</tr>
				<tr>
					<td><form:label path="password">
							<spring:message text="Password" />
						</form:label></td>
					<td><form:input type="password" path="password" class="input-xlarge"
							placeholder="Password" /></td>
				</tr>
				
				 <%-- <tr>
					<td><form:label path="dob">
							<spring:message text="Date of Birth" />
						</form:label></td>
					<td><form:input id="datepicker" path="dob" class="input-xlarge" /></td>
				</tr>   --%> 
				
				<tr>
					<td><form:label path="address">
							<spring:message text="Address" />
						</form:label></td>
					<td><form:textarea rows="4" cols="50" path="address" class="input-xlarge"
							placeholder="Address" /></td>
				</tr>
				
				<tr>
					<td><form:label path="city">
							<spring:message text="City" />
						</form:label></td>
					<td><form:input path="city" class="input-xlarge"
							placeholder="City" /></td>
				</tr>
				
				<tr>
					<td><form:label path="state">
							<spring:message text="State" />
						</form:label></td>
					<td><form:select path="state" class="input-xlarge">
							<option value="select">Select State</option>
							<option value="Arizona">Arizona</option>
                            <option value="California">California</option>
                            <option value="New Mexico">New Mexico</option>
                            <option value="Nevada">Nevada</option></form:select></td>
				</tr>
				
				
				<tr>
					<td><form:label path="zipcode">
							<spring:message text="Zipcode" />
						</form:label></td>
					<td><form:input path="zipcode" class="input-xlarge"
							placeholder="Zipcode" /></td>
				</tr>
				<tr>
					<td><form:label path="ssn">
							<spring:message text="SSN" />
						</form:label></td>
					<td><form:input path="ssn" class="input-xlarge"
							placeholder="SSN" /></td>
				</tr>
				
				<tr>
					<td><form:label path="phoneno">
							<spring:message text="Phone No." />
						</form:label></td>
					<td><form:input path="phoneno" class="input-xlarge"
							placeholder="xxx-xxx-xxxx" /></td>
				</tr>
				
				<tr>
					<td><form:label path="role">
							<spring:message text="Role" />
						</form:label></td>
					<td><form:select path="role" class="input-xlarge">
							<option>Select Role</option>
							<option value="User">User</option>
                            <option value="Merchant">Merchant</option>
                          		</form:select></td>
				</tr>
				
				<tr>
					<td><form:label path="ques1">
							<spring:message text="Security Question 1  " />
						</form:label></td>
					<td><form:select path="ques1" class="input-xlarge">
							<option value="select">Select Question</option>
							<option value="What is your mothers maiden name">What is your mothers maiden name</option>
                            <option value="What is your pets name">What is your pets name</option>
                            <option value="What is your 1st grade teacher name">What is your 1st grade teacher name</option>
                            <option value="What is your best friends name">What is your best friends name</option></form:select></td>
				</tr>
				
				<tr>
					<td><form:label path="answer1">
							<spring:message text="Answer 1" />
						</form:label></td>
					<td><form:input path="answer1" class="input-xlarge"
							placeholder="Answer" /></td>
				</tr>
				
				<tr>
					<td><form:label path="ques2">
							<spring:message text="Security Question 2  " />
						</form:label></td>
					<td><form:select path="ques2" class="input-xlarge">
							<option value="select">Select Question</option>
							<option value="What is your mothers maiden name">What is your mothers maiden name</option>
                            <option value="What is your pets name">What is your pets name</option>
                            <option value="What is your 1st grade teacher name">What is your 1st grade teacher name</option>
                            <option value="What is your best friends name">What is your best friends name</option></form:select></td>
				</tr>
				
				<tr>
					<td><form:label path="answer2">
							<spring:message text="Answer 2" />
						</form:label></td>
					<td><form:input path="answer2" class="input-xlarge"
							placeholder="Answer" /></td>
				</tr>
				
				<tr>
					<td><form:label path="ques3">
							<spring:message text="Security Question 3  " />
						</form:label></td>
					<td><form:select path="ques3" class="input-xlarge">
							<option value="select">Select Question</option>
							<option value="What is your mothers maiden name">What is your mothers maiden name</option>
                            <option value="What is your pets name">What is your pets name</option>
                            <option value="What is your 1st grade teacher name">What is your 1st grade teacher name</option>
                            <option value="What is your best friends name">What is your best friends name</option></form:select></td>
				</tr>
				
				<tr>
					<td><form:label path="answer3">
							<spring:message text="Answer 3" />
						</form:label></td>
					<td><form:input path="answer3" class="input-xlarge"
							placeholder="Answer" /></td>
				</tr>
				
				
				<tr>
					<td rowspan="2"><c:if test="${!empty user.emailId}">
							<input type="submit" value="<spring:message text="Edit" />"
								class="btn btn-lg btn-primary" />
						</c:if> <c:if test="${empty user.emailId}">
							<input type="submit" value="<spring:message text="Register" />"
								class="btn btn-lg btn-primary" />
						</c:if></td>
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
	<h3 align="center">Registered Users</h3>
	<div class="container">
		<c:if test="${!empty listUsers}">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email Id</th>
						<th>Role</th>
						<th>Delete</th>
					</tr>
				</thead>
				<c:forEach items="${listUsers}" var="user">
					<tr>
						<td>${user.fname}</td>
						<td>${user.lname}</td>
						<td>${user.emailId}</td>
						<td>${user.role}</td>
						<td><a href="<c:url value='remove/${user.userId}' />">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>