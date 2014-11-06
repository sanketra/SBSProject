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
function validateForm()
{
var transactionAmount = document.forms["myForm"]["transactionAmount"].value;
if (transactionAmount==null || transactionAmount=="") {
    alert("Enter Transaction amount");
    return false;
}
//var amountPattern = ^[0-9]+(\.[0-9]{1,2})?$;
//if(amountPattern.test(transactionAmount)!=true){
//	alert("Enter a valid Amount");
//	return false;
}


</script>



<c:url var="submitAction" value="/employee/updateUserTransaction"></c:url>
<div class="container">
	<form:form name="myForm" action="updateUserTransaction" onsubmit="return validateForm()" commandName="userTransaction" class="form-horizontal" method="POST">
		<form:hidden path="transactionId" />
		<table class="table">
			<tr>
				<td>From Account</td>
				<td><form:input path="fromAcountNum" class="input-xlarge"
						placeholder="From Account" disabled="true"/></td>
			</tr>
			<tr>
				<td>To Account</td>
				<td><form:input path="toAccountNum" class="input-xlarge"
						placeholder="To Account" disabled="true"/></td>
			</tr>
			<tr>
				<td>Type of Transaction</td>
				<td><form:input path="transactionType" class="input-xlarge"
						placeholder="Transaction Type" disabled="true"/></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><form:input path="transactionAmount" class="input-xlarge"
						placeholder="Amount" /></td>
			</tr>
			<tr>
				<td>Time</td>
				<td><form:input path="transactionTime" class="input-xlarge"
						placeholder="Time" disabled="true"/></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><form:select path="transactionStatus" class="input-xlarge">
							<option value="success" ${userTransaction.transactionStatus=='approved'? 'selected' : ''}>approved</option>
							<option value="pending" ${userTransaction.transactionStatus=='pending'? 'selected' : ''}>pending</option>
                            <option value="declined" ${userTransaction.transactionStatus=='declined'? 'selected' : ''}>declined</option>
                     </form:select>
               </td>
			</tr>
			<tr>
				<td>Captcha</td>
				<td>
					<%
						ReCaptcha c = ReCaptchaFactory
									.newReCaptcha(
											"6LdU5vsSAAAAANqqVjAYmtFDp7gqRk-f71obE5eS",
											"6LdU5vsSAAAAAPAyZqM1Bx3Kh12wdMvimkjC5Xqpyour_private_key",
											false);
							out.print(c.createRecaptchaHtml(null, null));
					%>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Update" class="btn btn-lg btn-primary" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
