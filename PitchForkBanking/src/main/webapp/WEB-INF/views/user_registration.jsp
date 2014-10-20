<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Registration</title>
<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    <link href="<c:url value="/resources/css/bootstrap-responsive.css" />"rel="stylesheet">
</head>
<body>
<h1 align="center">
	Register
</h1>

<c:url var="addAction" value="/user/add" ></c:url>
<div class="container">
<form:form action="${addAction}" commandName="user" class="form-signin">
<table>
			<tr>
			<td>
			<form:label path="emailId">
				<spring:message text="Name"/>
			</form:label>
	        </td>
	        <td>
			<form:input path="emailId" class="form-control" placeholder="Email Id"/></td>
		    </tr>
		    <tr>
		    <td>
			<form:label path="password">
				<spring:message text="Password"/>
			</form:label>
		</td>
		<td>
			<form:input type='password' path="password" class="form-control" placeholder="Password"/></td>
			</tr>
			<tr>
			<td>
			<form:label path="fname">
				<spring:message text="First Name"/>
			</form:label>
		</td><td>
			<form:input path="fname" class="form-control" placeholder="First Name"/></td>
			</tr>
			<tr>
			<td>
			<form:label path="role">
				<spring:message text="Role"/>
			</form:label>
		</td><td>
			<form:input path="role" class="form-control" placeholder="Role"/></td>
			</tr>
			<tr>
			<td rowspan="2">
			<c:if test="${!empty user.emailId}">
				<input type="submit"
					value="<spring:message text="Edit" />" class="btn btn-lg btn-primary" />
			</c:if>
			<c:if test="${empty user.emailId}">
				<input type="submit"
					value="<spring:message text="Register" />" class="btn btn-lg btn-primary" />
			</c:if>
			</td>	
			</tr>
			</table>
</form:form>
</div>
<div class="container" align="center">
<a href="http://localhost:8080/PitchForkBanking/" rel="nofollow">Login into PitchForkBanking</a>
</div>
<br>
<br>
<h3>Registered Users</h3>
 <div class="col-md-6">
<c:if test="${!empty listUsers}">
	<table class="table table-bordered">
	<thead>
	<tr>
		<th width="120">User EmailId</th>
		<th width="120">User First Name</th>
		<th width="120">User Role</th>
		<th width="60">Edit</th>
		<th width="60">Delete</th>
	</tr>
	</thead>
	<c:forEach items="${listUsers}" var="user">
		<tr>
			<td>${user.emailId}</td>
			<td>${user.fname}</td>
			<td>${user.role}</td>
			<td><a href="<c:url value='/edit/${user.userId}' />" >Edit</a></td>
			<td><a href="<c:url value='/remove/${user.userId}' />" >Delete</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</div>
</body>
</html>
