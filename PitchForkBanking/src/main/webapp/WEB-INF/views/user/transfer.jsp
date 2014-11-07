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
		var namePattern1 = /[*|\":<>.[\]{}`\\()';@&$]/;
		if (namePattern1.test(name) == true) {
			alert('Special characters are not allowed in Name');
			return false;
		}
		var namePattern2 = /^[a-zA-Z ]*$/;
		if (namePattern2.test(name) != true) {
			alert('Only alphabets are allowed in Name');
			return false;
		}
		// Account Number
		var account_to = document.forms["myForm"]["account_to"].value;
		if (account_to == null || account_to == "") {
			alert("Account Number must be filled out");
			return false;
		}
		var account_toPattern1 = /[*|\":<>[\]{}`\\()';@&$]/;
		if (account_toPattern1.test(account_to) == true) {
			alert('Special characters are not allowed in Account Number');
			return false;
		}
		var account_toPattern2 = /^[0-9]+$/;
		if (account_toPattern2.test(account_to) != true) {
			alert('Only numbers are allowed in Account Number');
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
		// Amount
		var amount = document.forms["myForm"]["amount"].value;
		if (amount == null || amount == "") {
			alert("Amount must be filled out");
			return false;
		}
		var amountPattern1 = /[*|\":<>[\]{}`\\()';@&$]/;
		if (amountPattern1.test(amount) == true) {
			alert('Special characters are not allowed in amount');
			return false;
		}
		var amountPattern2 = /^[0-9]+.?[0-9]*$/;
		if (amountPattern2.test(amount) != true) {
			alert('Only numbers are allowed in amount');
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
					class="input-block-level" placeholder="FirstName LastName" /></td>
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
						src="https://www.google.com/recaptcha/api/challenge?k=6LfLUv0SAAAAABLMjAJRm4vBHmiwQtn7ySuzfULk">
					
				</script>
					<noscript>
						<iframe
							src="https://www.google.com/recaptcha/api/noscript?k=66LfLUv0SAAAAABLMjAJRm4vBHmiwQtn7ySuzfULk"
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
