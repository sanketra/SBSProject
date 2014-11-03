<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="submitAction" value="/user/requestaccount"></c:url>
<div class="container">
	<form:form action="${submitAction}" class="form-horizontal" method="POST">
		<table class="table borderless">
			<tr>
				<td colspan="2">
						<input type="submit" value="<spring:message text="Request Account" />"
							class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
