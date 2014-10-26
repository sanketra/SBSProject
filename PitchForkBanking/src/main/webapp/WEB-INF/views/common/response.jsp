<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="response">
	<div class="alert alert-${response.status}">
		<c:out value="${response.message}" />
	</div>
</div>