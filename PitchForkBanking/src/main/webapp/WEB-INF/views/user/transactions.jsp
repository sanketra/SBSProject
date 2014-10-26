<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="submitAction" value="/user/profile/update"></c:url>
<div class="container">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>From</th>
				<th>To</th>
				<th>Amount</th>
				<th>Type</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="_transaction">
				<tr>
					<td>${_transaction.accountByFromAcountNum.accountNum}</td>
					<td>${_transaction.accountByToAccountNum.accountNum}</td>
					<td>${_transaction.transactionAmount}</td>
					<td>${_transaction.transactionType}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<br>
	<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>

	<form action="${logoutAction}" method="post">
		<input type="submit" value="Logout" />
	</form>
</div>