<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>header page</title>
</head>
<body>
	<div class="header-container">
		
		<div class="site-name">Online shop</div>
		<div class="header-bar">
		<c:if test="${pageContext.request.userPrincipal.name!=null}">
		Hello
		<a href="${pageContext.request.contextPath}/accountInfo">${pageContext.request.userPrincipal.name} </a>
		&nbsp;|&nbsp;
		<a href="${pageContext.request.contextPath}/logout">logout</a>
		</c:if>
		<c:if test="${pageContext.request.userPrincipal.name==null}">
		<a href="${pageContext.request.contextPath}/login">Login</a>
		</c:if>
		</div>
		
	</div>
</body>
</html>