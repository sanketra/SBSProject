<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar">
	<ul id="admin_menu" class="nav nav-tabs nav-stacked">
		<li><a
			href="${pageContext.request.contextPath}/admin/newUsers">New Registrations</a></li>
		<li><a
			href="${pageContext.request.contextPath}/admin/customerList">Customer List</a></li>
		<li><a
			href="${pageContext.request.contextPath}/admin/employeeList">Employee List</a></li>
		<li><a
			href="${pageContext.request.contextPath}/admin/accountTransactions">Account Transactions</a></li>
		<li><a
			href="${pageContext.request.contextPath}/admin/processRequests">Requests</a></li>
		<li><a
			href="${pageContext.request.contextPath}/admin/bank_branch/list">Others</a></li>
	</ul>
</aside>
