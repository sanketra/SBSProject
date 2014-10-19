<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar" class="span5">
	<ul id="admin_menu">
		<li><a class="btn btn-primary btn-large"
			href="${page.url_host}${page.url_apppath}admin/customer/list">Customer List</a></li>
		<li><a class="btn btn-primary btn-large"
			href="${page.url_host}${page.url_apppath}admin/customer/search">Transactions</a></li>
		<li><a class="btn btn-primary btn-large"
			href="${page.url_host}${page.url_apppath}admin/account/search">Add Employee</a></li>
		<li><a class="btn btn-primary btn-large"
			href="${page.url_host}${page.url_apppath}admin/bank_branch/list">Others</a></li>
	</ul>
</aside>
