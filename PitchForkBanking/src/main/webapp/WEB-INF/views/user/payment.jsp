<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<c:url var="selectAction" value="/user/payment"></c:url>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>From</th>
				<th>To</th>
				<th>Amount</th>
				<th>Status</th>
				<th>Accept</th>
				<th>Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="_transaction">
				<tr>
					<td>${_transaction.accountByFromAcountNum.accountNum}</td>
					<td>${_transaction.accountByToAccountNum.accountNum}</td>
					<td><fmt:formatNumber value="${_transaction.transactionAmount}"
								type="currency" /></td>
					<td>${_transaction.transactionType}</td>
					<td>
					<form action="${selectAction}" method="POST">
					<input type="hidden" name="accept" value="${_transaction.transactionId}"/>
						<input type="submit" value="Accept" class="btn btn-success" />
					</form>
					</td>
					<td>
					<form action="${selectAction}" method="POST">
					<input type="hidden" name="decline" value="${_transaction.transactionId}"/>
						<input type="submit" value="Decline" class="btn btn-danger" />
					</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>