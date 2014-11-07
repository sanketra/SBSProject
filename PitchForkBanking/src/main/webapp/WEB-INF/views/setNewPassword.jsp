<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title>Forgot Password</title>
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
		<c:if test="${!empty response}">
			<jsp:include page="common/response.jsp" />
		</c:if>
		<c:url var="newPasswordUrl" value="/setNewPassword"></c:url>
		<form class="form-signin" action="${newPasswordUrl}" method="POST">
			<h4 class="form-signin-heading">Set New Password</h4>
			<br> <input type='text' name='One Time Password'
				class="input-block-level" placeholder="Enter One Time Password" />
			<br>
			<h5>${question1}?</h5>
			<br> <input type='text' name='Answer1' class="input-block-level"
				placeholder="Enter your answer here" /> <br>
			<h5>${question2}?</h5>
			<br> <input type='text' name='Answer2' class="input-block-level"
				placeholder="Enter your answer here" /> <br>
			<h5>${question3}?</h5>
			<br> <input type='text' name='Answer3' class="input-block-level"
				placeholder="Enter your answer here" /> <br> <input
				type='password' name='New Password' class="input-block-level"
				placeholder="New Password" /> <br> <input type='password'
				name='Re-Enter New Password' class="input-block-level"
				placeholder="Re-Enter New Password" /> <input type="submit"
				value="Verify" class="btn btn-large btn-primary" />
		</form>
	</div>
</body>
</html>