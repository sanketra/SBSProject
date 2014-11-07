<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>


<h3 align="center">Critical Transaction Requests</h3>
<div class="container">
	<c:url var="selectAction" value="/admin/admin_processCriticalTransactionRequests"></c:url>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>From Account</th>
				<th>To Account</th>
				<th>Amount $ </th>
				<th>Transaction Type</th>
				<th>Status</th>
				<th>Accept</th>
				<th>Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${criticalTransactions}" var="_requestct">
				<tr>
					<td>${_requestct.accountByFromAcountNum.accountNum} </td>
					<td>${_requestct.accountByToAccountNum.accountNum} </td>
					<td>${_requestct.transactionAmount} </td>
					<td>${_requestct.transactionType}</td>
					<td>${_requestct.transactionStatus} </td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="approve" value="${_requestct.transactionId}" />
							<input type="submit" value="Approve" class="btn btn-success" />
						</form>
					</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="decline" value="${_requestct.transactionId}" />
							<input type="submit" value="Decline" class="btn btn-danger" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>

