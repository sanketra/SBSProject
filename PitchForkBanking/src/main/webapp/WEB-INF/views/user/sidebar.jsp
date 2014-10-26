<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar">
	<ul id="user_menu" class="nav nav-tabs nav-stacked">
		<li><a 
			href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
		<li><a 
			href="${pageContext.request.contextPath}/user/transfer">Transfer</a></li>
		<li><a
			href="${pageContext.request.contextPath}/user/debit">Debit</a></li>
		<li><a 
			href="${pageContext.request.contextPath}/user/credit">Credit</a></li>
		<li><a 
			href="${pageContext.request.contextPath}/user/payment">Payment</a></li>
		<li><a 
			href="${pageContext.request.contextPath}/user/transactions">Transactions</a></li>
		<li><a 
			href="${pageContext.request.contextPath}/user/authorize">Authorize</a></li>
	</ul>
</aside>