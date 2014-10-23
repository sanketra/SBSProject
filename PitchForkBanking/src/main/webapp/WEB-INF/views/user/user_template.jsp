<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%              
  response.setHeader("pragma", "no-cache");              
  response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");             
  response.setHeader("Expires", "0");  
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<fmt:setBundle basename="en"></fmt:setBundle>

<link href="<c:url value="/resources/dist/css/bootstrap.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/dist/css/bootstrap-responsive.css" />"rel="stylesheet">

</head> 
<body id="body-site">
<br>
<br>
<br>
	<div id="content-container">
		<div id="content-area">
			<div class="row-fluid">
				<jsp:include page="user_sidebar.jsp" />
				<article id="content" class="span8">
				<jsp:include page="${contentView}.jsp" /> 
				</article>
			</div>
		</div>
	</div>

</body>
</html>