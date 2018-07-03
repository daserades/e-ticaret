<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="menu-container">
		<a href="${pageContext.request.contextPath}/">Home</a>
	|
	<a href="${pageContext.request.contextPath}/productList">Product List</a>
	|
	<a href="${pageContext.request.contextPath}/shoppingCart">My Cart</a>
	|
	<security:authorize access="hasROLE('ROLE_MANAGER','ROLE_EMPLOYEE')">
	<a href="${pageContext.request.contextPath}/orderList">Order List</a>
	|
	</security:authorize>
	<security:authorize access="hasROLE('ROLE_MANAGER')">
		<a href="${pageContext.request.contextPath}/product">Create Product</a>
	</security:authorize>
	|
	</div>
</body>
</html>