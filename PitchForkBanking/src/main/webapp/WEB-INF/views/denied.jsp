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
<html>
<head>
<title>Access Denied</title>
</head>
<body>
	<h1>Access Denied!</h1>
	<c:if test="${!empty response}">
		<jsp:include page="common/response.jsp" />
	</c:if>
</body>
</html>
