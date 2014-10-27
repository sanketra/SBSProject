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
				<th>Type</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="_transaction">
				<tr>
					<td>${_transaction.accountByFromAcountNum.accountNum}</td>
					<td>${_transaction.accountByToAccountNum.accountNum}</td>
					<td>${_transaction.transactionAmount}</td>
					<td>${_transaction.transactionType}</td>
					<td>${_transaction.transactionStatus}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<br>
</div>