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

.borderless tbody tr td, .borderless thead tr th {
	border: none;
}
</style>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap-responsive.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />"
	rel="stylesheet">

</head>
<body>
	<div class="container-fluid" align="right">
		<jsp:include page="../common/header.jsp" />
	</div>
	<br>
	<br>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="sidebar.jsp" />
			</div>
			<div class="span10">
				<article class="span10">
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