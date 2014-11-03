<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="submitAction" value="/user/transfer"></c:url>
<div class="container">
	<form:form action="${submitAction}" class="form-horizontal"
		method="POST">
		<table class="table table borderless">
			<tr>
				<td>Name</td>
				<td><input name="name" autocomplete="off" type="text"
					class="input-block-level" required="required"
					placeholder="Account Holder Name" /></td>
			</tr>
			<tr>
				<td>Account Number</td>
				<td><input name="account_to" autocomplete="off" type="text"
					class="input-block-level" required="required"
					placeholder="Account Number" /></td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td><input name="emailId" autocomplete="off" type="text"
					class="input-block-level" required="required"
					placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><input name="amount" autocomplete="off" type="text"
					class="input-block-level" required="required" placeholder="Amount" /></td>
			</tr>
			<tr>
				<td>Captcha</td>
				<td><script type="text/javascript">
					var RecaptchaOptions = {
						theme : 'clean'
					};
				</script> <script type="text/javascript"
						src="https://www.google.com/recaptcha/api/challenge?k=6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS">
					
				</script>
					<noscript>
						<iframe
							src="https://www.google.com/recaptcha/api/noscript?k=6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS"
							height="260" width="400"></iframe>
						<br>
						<textarea name="recaptcha_challenge_field" rows="3" cols="40">
       </textarea>
						<input type="hidden" name="recaptcha_response_field"
							value="manual_challenge">
					</noscript></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message text="Transfer" />"
					class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
