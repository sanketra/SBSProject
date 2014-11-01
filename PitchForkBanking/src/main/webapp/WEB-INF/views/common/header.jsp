<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>

<div class="row-fluid">
<div class="span2" align="left">
	<a href="${pageContext.request.contextPath}/user/home" class="btn"><i class="icon-home"></i> Home </a>
</div>
<div class="span8" align="center">
<h3>Pitch Fork Bank</h3>
</div>
<div class="span2">
<form action="${logoutAction}" method="post">
 <div class="left-inner-addon">
 	<i class="icon-off"></i>
	<input class="btn btn-inverse" type="submit" value="Logout">
	</div>
</form>
</div>
</div>