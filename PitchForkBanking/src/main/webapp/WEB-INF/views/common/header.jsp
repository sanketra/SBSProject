<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>

<form action="${logoutAction}" method="post">
	<input class="btn btn-danger" type="submit" value="Logout" />
</form>