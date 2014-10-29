<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar">
	<ul id="user_menu" class="nav nav-tabs nav-stacked">
		<li><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
		<li><a href="${pageContext.request.contextPath}/user/transfer">Transfer</a></li>
		<li><a href="${pageContext.request.contextPath}/user/debit">Debit</a></li>
		<li><a href="${pageContext.request.contextPath}/user/credit">Credit</a></li>
		<c:choose>
			<c:when test="${role == \"User\"}">
				<li><a href="${pageContext.request.contextPath}/user/payment">Payment</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/user/requestPayment">Request Payment</a></li>
			</c:otherwise>
		</c:choose>
		<li><a
			href="${pageContext.request.contextPath}/user/transactions">Transactions</a></li>
		<li><a href="${pageContext.request.contextPath}/user/authorize">Authorize</a></li>
	</ul>
</aside>