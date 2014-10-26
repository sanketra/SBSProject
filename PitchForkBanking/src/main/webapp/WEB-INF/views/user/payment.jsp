<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<table class="table table-bordered">
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
					<td>${_transaction.transactionAmount}</td>
					<td>${_transaction.transactionType}</td>
					<td><a class="btn btn-success"
						href="${pageContext.request.contextPath}/user/payment/${_transaction.transactionId}">Accept</a></td>
					<td><a class="btn btn-danger"
						href="${pageContext.request.contextPath}/user/payment/${_transaction.transactionId}">Decline</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>