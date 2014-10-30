<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Validation script -->
<title>Update Transaction</title>

<c:url var="submitAction" value="/employee/updateUserTransaction"></c:url>
<div class="container">
	<form:form name = "myForm" action="${submitAction}" commandName="transaction"
		class="form-horizontal" method="POST">
		<form:hidden path="transactionId" />
		<table class="table">
			<tr>
				<td>From Account</td>
				<td><form:input path="accountByFromAcountNum.accountNum" class="input-xlarge"
						placeholder="From Account" /></td>
			</tr>
			<tr>
				<td>To Account</td>
				<td><form:input path="accountByToAccountNum.accountNum" class="input-xlarge"
						placeholder="To Account" /></td>
			</tr>
			<tr>
				<td>Type of Transaction</td>
				<td><form:input path="transactionType" class="input-xlarge"
						placeholder="Transaction Type" /></td>
			</tr>
			<tr>
				<td>Amount</td>
				<td><form:input path="transactionAmount" class="input-xlarge"
						placeholder="Amount" /></td>
			</tr>
			<tr>
				<td>Time</td>
				<td><form:input path="transactionTime" class="input-xlarge"
						placeholder="Time" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><form:select path="transactionStatus" class="input-xlarge">
							<option value="approved" ${transaction.transactionStatus=='approved'? 'selected' : ''}>approved</option>
							<option value="pending" ${transaction.transactionStatus=='pending'? 'selected' : ''}>pending</option>
                            <option value="declined" ${transaction.transactionStatus=='declined'? 'selected' : ''}>declined</option>
                     </form:select>
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
