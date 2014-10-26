<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside id="side_bar">
	<ul id="user_menu" class="nav nav-tabs nav-stacked">
		<li><a 
			href="${page.url_host}${page.url_apppath}profile">Profile</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}transfer">Transfer</a></li>
		<li><a
			href="${page.url_host}${page.url_apppath}debit">Debit</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}credit">Credit</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}payment">Payment</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}transactions">Transactions</a></li>
		<li><a 
			href="${page.url_host}${page.url_apppath}authorize">Authorize</a></li>
	</ul>
</aside>