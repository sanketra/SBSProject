<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<title>Update Transaction</title>
<script>
	function validateForm() {
		var transactionAmount = document.forms["myForm"]["transactionAmount"].value;
		if (transactionAmount == null || transactionAmount == "") {
			alert("Enter Transaction amount");
			return false;
		}
		var transactionAmountPattern = /^\d{0,6}\.?\d{0,2}$/;
		if (!transactionAmount.match(transactionAmountPattern)) {
			alert("Enter a valid Transaction Amount");
			return false;
		}
	}
</script>

<c:url var="submitAction" value="/employee/updateUserTransaction"></c:url>
<div class="container">
	<form:form name="myForm" action="updateUserTransaction"
		onsubmit="return validateForm()" commandName="userTransaction"
		class="form-horizontal" method="POST">
		<form:hidden path="transactionId" />
		<table class="table">
			<tr>
				<td>From Account</td>
				<td><form:input path="fromAcountNum" class="input-xlarge"
						placeholder="From Account" disabled="true" /></td>
				<form:hidden path="fromAcountNum" />
			</tr>
			<tr>
				<td>To Account</td>
				<td><form:input path="toAccountNum" class="input-xlarge"
						placeholder="To Account" disabled="true" /></td>

				<form:hidden path="toAccountNum" />
			</tr>
			<tr>
				<td>Type of Transaction</td>
				<td><form:input path="transactionType" class="input-xlarge"
						placeholder="Transaction Type" disabled="true" /></td>
				<form:hidden path="transactionType" />
			</tr>
			<tr>
				<td>Amount</td>
				<td><form:input path="transactionAmount" class="input-xlarge"
						placeholder="Amount(Only 2 digits after decimal)" /></td>
			</tr>
			<tr>
				<td>Time</td>
				<td><form:input path="transactionTime" class="input-xlarge"
						placeholder="Time" disabled="true" /></td>
				<form:hidden path="transactionTime" />
			</tr>
			<tr>
				<td>Status</td>
				
				<c:choose>
      			<c:when test="${userTransaction.transactionStatus=='Success'}">
    				<td><form:input path="transactionStatus" class="input-xlarge"
						placeholder="Transaction Satus" disabled="true" /></td>
						<form:hidden path="transactionStatus" />
      			</c:when>

      			<c:otherwise>
    				<td><form:select path="transactionStatus" class="input-xlarge">
						<option value="Success"
							${userTransaction.transactionStatus=='Success'? 'selected' : ''}>Success</option>
						<option value="AdminPending"
							${userTransaction.transactionStatus=='AdminPending'? 'selected' : ''}>Admin Pending</option>
						<option value="UserPending"
							${userTransaction.transactionStatus=='UserPending'? 'selected' : ''}>User Pending</option>
					</form:select></td>
      			</c:otherwise>
				</c:choose>
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
				<td><input type="submit" value="Update"
					class="btn btn-lg btn-primary" /></td>
			</tr>
		</table>
	</form:form>
</div>
