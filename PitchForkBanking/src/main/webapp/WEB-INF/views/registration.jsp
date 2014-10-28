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
	<h1 align="center">User Registration</h1>
	<c:url var="addAction" value="/add"></c:url>
	<div class="container">
		<form:form action="${addAction}" commandName="user"
			class="form-signin">
			<table>
				<tr>
					<td><form:label path="userId">
							<spring:message text="User ID" />
						</form:label></td>
					<td><form:input path="userId" class="input-xlarge"
							placeholder="User Id" /></td>
				</tr>
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