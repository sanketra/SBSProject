<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<c:url var="selectAction" value="/user/authorize"></c:url>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>From</th>
				<th>Type</th>
				<th>Accept</th>
				<th>Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requests}" var="_request">
				<tr>
					<td>${_request.employeeName}</td>
					<td>${_request.requestType}</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="approve" value="${_request.requestId}" />
							<input type="submit" value="Approve" class="btn btn-success" />
						</form>
					</td>
					<td>
						<form action="${selectAction}" method="POST">
							<input type="hidden" name="decline" value="${_request.requestId}" />
							<input type="submit" value="Decline" class="btn btn-danger" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>