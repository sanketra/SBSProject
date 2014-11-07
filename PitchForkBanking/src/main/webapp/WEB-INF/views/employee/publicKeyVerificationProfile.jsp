<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="submitAction" value="/employee/verifiedEncryptedTextUserProfile"></c:url>
<div class="container">
	<form:form action="${submitAction}" class="form-horizontal" method="POST">
		<table class="table">
		<tr>
				<th colspan="2">Public Key Verification</th>
			</tr>
			<tr>
				<td>Identifier String</td>
				<td>
				${randomString}
				<input type="hidden" name ="randomString" value="${randomString}"/>
				</td>
			</tr>
			<tr>
				<td>Encrypted String</td>
				<td><input type="text" name="encrypedString"/></td>
			</tr>
			<tr>
				<td colspan="2">
						<input type="submit" value="<spring:message text="submit" />"
							class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
