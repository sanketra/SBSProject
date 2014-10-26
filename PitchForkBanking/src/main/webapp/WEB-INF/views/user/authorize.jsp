<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<table class="table table-bordered">
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
					<td>${_request.userByFromUserId.fname}</td>
					<td>${_request.type}</td>
					<td><a class="btn btn-success"
						href="${pageContext.request.contextPath}/user/authorize">Accept</a></td>
					<td><a class="btn btn-danger"
						href="${pageContext.request.contextPath}/user/authorize">Decline</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br> <br>
</div>