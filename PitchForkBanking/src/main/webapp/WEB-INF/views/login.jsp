<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title>Login Page</title>
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">
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
<%--  <link href="<c:url value="/resources/css/bootstrap-responsive.css" />"rel="stylesheet"> --%>
</head>
<body>
	<div class="container">
		<c:url var="loginUrl" value="/j_spring_security_check"></c:url>
		<form class="form-signin" action="${loginUrl}" method="POST">
			<h2 class="form-signin-heading">Pitch Fork Banking</h2>
			<input type='text' name='emailId' class="input-block-level"
				placeholder="Email address" /> <input type='password'
				name='password' class="input-block-level" placeholder="Password" />
			<input name="submit" type="submit" value="Login"
				class="btn btn-large btn-primary" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/registration" rel="nofollow">Register</a>
		</form>
	</div>
</body>
</html>