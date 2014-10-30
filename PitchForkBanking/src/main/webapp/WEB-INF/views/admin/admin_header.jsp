<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>

<div class="container" align="left">
	<a href="${pageContext.request.contextPath}/admin/admin_home" class="btn btn btn-success"> Home </a>
</div>
<form action="${logoutAction}" method="post">
	<input class="btn btn-danger" type="submit" value="Logout" />
</form>