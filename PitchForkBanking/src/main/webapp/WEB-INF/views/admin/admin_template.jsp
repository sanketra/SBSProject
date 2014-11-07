<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 600px;
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
	height: 600px;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">

</head>
<body id="body-site">
	<div class="container-fluid" align="right">
		<jsp:include page="../admin/admin_header.jsp" />
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="admin_sidebar.jsp" />
			</div>
			<div class="span10">
				<article id="content" class="span10">
					<c:if test="${!empty response}">
						<jsp:include page="../common/response.jsp" />
					</c:if>
					<jsp:include page="${contentView}.jsp" />
				</article>

			</div>
		</div>
	</div>
	<div class="container-fluid" align="left">
		<jsp:include page="../common/footer.jsp" />
	</div>
</body>
</html>