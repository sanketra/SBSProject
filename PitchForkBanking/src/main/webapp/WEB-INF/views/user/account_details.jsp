<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%              
  response.setHeader("pragma", "no-cache");              
  response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");             
  response.setHeader("Expires", "0");  
%>
<html>
<head>
<title>Home</title>
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
	<h1>Welcome to Pitch Fork Banking</h1>
	<P><br> Hello :${user.fname}<br></P>
	<br>
	<br>
	<div >
				<jsp:include page="user_sidebar.jsp" />
			</div>
	<c:url var="editAction" value="/user/edit" ></c:url>
<table class="table">
	<tr>
		<td>Email Id</td>
		<td>${user.emailId}</td>
	</tr>
	<tr>
		<td>First Name</td>
		<td>${user.fname}</td>
	</tr>
	<tr>
		<td>Role</td>
		<td>${user.role}</td>
	</tr>
			<tr>
			<td rowspan="3">
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
	<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>
	
	<form action="${logoutAction}" method="post">
		<input type="submit" value="Logout" />
	</form>
</body>
</html>
