<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<title>PitchFork Bank - Transfer</title>
<!-- Validation Script -->
<script>
	function validateForm() {
		// First Name
		var name = document.forms["myForm"]["name"].value;
		if (name == null || name == "") {
			alert("Name must be filled out");
			return false;
		}
		var namePattern = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (namePattern.test(name) == true) {
			alert('Special characters are not allowed in Name');
			return false;
		}
		// Account Number
		var account_to = document.forms["myForm"]["account_to"].value;
		if (account_to == null || account_to == "") {
			alert("Email-Id must be filled out");
			return false;
		}
		var account_toPattern = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (account_toPattern.test(account_to) == true) {
			alert('Special characters are not allowed in Account');
			return false;
		}
		// Email_Id
		var emailId = document.forms["myForm"]["emailId"].value;
		if (emailId == null || emailId == "") {
			alert("Email-Id must be filled out");
			return false;
		}
		var emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if (emailPattern.test(emailId) != true) {
			alert("Enter a valid Email-ID");
			return false;
		}
	}
</script>

<c:url var="submitAction" value="/user/transfer"></c:url>
<div class="container">
	<form:form name="myForm" action="${submitAction}"
		onsubmit="return validateForm()" class="form-horizontal" method="POST">
		<table class="table table borderless">
			<tr>
				<td>Name</td>
				<td><input name="name" autocomplete="off" type="text"
					class="input-block-level" placeholder="Account Holder Name" /></td>
			</tr>
			<tr>
				<td>Account Number</td>
				<td><input name="account_to" autocomplete="off" type="text"
					class="input-block-level" placeholder="Account Number" /></td>
			</tr>
			<tr>
				<td>Email Id</td>
				<td><input name="emailId" autocomplete="off" type="text"
					class="input-block-level" placeholder="Email Id" /></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><input name="amount" autocomplete="off" type="text"
					class="input-block-level" placeholder="Amount" /></td>
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
