<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("Cache-control",
			"no-cache, no-store, must-revalidate");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Logs</title>
</head>
<body>

<div class = "container">
<table class="table table-bordered">
		<thead>
			<tr>
				<th align="center">Logs</th>
			</tr>
		</thead>
		<tr>
			<td>
				<textarea rows="100" cols="1000" readonly="readonly" style="overflow: auto; min-width: 900px; max-height: 800px">${logContents}</textarea>
			</td>
		</tr>
</table>
</div>	
</body>
</html>