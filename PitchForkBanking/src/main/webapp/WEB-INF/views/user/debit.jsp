<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<title>PitchFork Bank - Debit</title>
<!-- Validation script -->
<script>
	function validateForm() {
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
<c:url var="submitAction" value="/user/debit"></c:url>
<div class="container">
	<form:form name="myForm" action="${submitAction}"
		onsubmit="return validateForm()" class="form-horizontal" method="POST">
		<table class="table borderless">
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
							src="https://www.google.com/recaptcha/api/noscript?k=6LfLUv0SAAAAABLMjAJRm4vBHmiwQtn7ySuzfULk"
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
					value="<spring:message text="Debit" />"
					class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
