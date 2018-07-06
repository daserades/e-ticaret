<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="p" %> 
   <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
   <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IAccount Info</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="page-title">Account Info</div>
<div class="account-container">
	<ul>
		<li>User name:"${pageContext.request.contextPath}/styles.css"</li>
		<li>Role:
			<ul>
			<p:forEach items="${userDetails.authorities}" var="auth">
				<li>${auth.authority}</li>
			</p:forEach>
			</ul>
		</li>
	</ul>
</div>

<jsp:include page="_footer.jsp"/>
</body>
</html>