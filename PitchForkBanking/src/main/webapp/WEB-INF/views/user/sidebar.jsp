<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar">
	<ul id="user_menu" class="nav nav-pills nav-stacked">
		<li class= "${profile}"><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
		<li class= "${transfer}"><a href="${pageContext.request.contextPath}/user/transfer">Transfer</a></li>
		<li class= "${debit}"><a href="${pageContext.request.contextPath}/user/debit">Debit</a></li>
		<li class= "${credit}"><a href="${pageContext.request.contextPath}/user/credit">Credit</a></li>
		<c:choose>
			<c:when test="${role == \"User\"}">
				<li class= "${payment}"><a href="${pageContext.request.contextPath}/user/payment">Payment</a></li>
			</c:when>
			<c:otherwise>
				<li class= "${requestpayment}"><a href="${pageContext.request.contextPath}/user/requestPayment">Request Payment</a></li>
			</c:otherwise>
		</c:choose>
		<li class= "${transaction}"><a
			href="${pageContext.request.contextPath}/user/transactions">Transactions</a></li>
		<li class= "${authorize}"><a href="${pageContext.request.contextPath}/user/authorize">Authorize</a></li>
		<li class= "${requestaccount}"><a href="${pageContext.request.contextPath}/user/requestaccount">Request Account</a></li>
	</ul>
</aside>