<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="submitAction" value="/user/requestPayment"></c:url>
<div class="container">
	<form:form action="${submitAction}" class="form-horizontal" method="POST">
		<table class="table">
			<tr>
				<td>Name</td>
				<td><input name="name" autocomplete="off" type="text" class="input-block-level" 
				required="required" placeholder="Account Holder Name" /></td>
			</tr>
			<tr>
				<td>Account Number</td>
				<td><input name="account_to" autocomplete="off" type="text" class="input-block-level" 
				required="required" placeholder="Account Number" /></td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td><input name="emailId" autocomplete="off" type="text" class="input-block-level" 
				required="required" placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><input name="amount" autocomplete="off" type="text" class="input-block-level" 
				required="required" placeholder="Amount" /></td>
			</tr>
			<tr>
			<td colspan="2"><b>Further authentication is required to perform this operation.
			Copy the below identifier string, paste in the jar, encrypt it and paste in below text field.</b></td>
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
						<input type="submit" value="<spring:message text="Request" />"
							class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
