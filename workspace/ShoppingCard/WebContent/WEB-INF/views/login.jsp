<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/styles.css">
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>

<div class="page-title">Login(For Employee,Manager)</div>
	<div class="login-container">
		<h3>Enter Username and Password</h3>
		<br>
		<!-- login?error -->
	<c:if test="${param.error==true }">
		<div style="color:red;margin:10px 0px">
			
			Login Failed<br>Reason:
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message }
			
		</div>
	
	</c:if>
	
	<form action="${pageContext.request.contextPath }/j_spring_security_check" method="post">
			<table>
				<tr>
					<td>User name:</td>
					<td><input type="text" name="userName"></td>
				</tr>
				
				<tr>
					<td>Password:</td>
					<td><input name="password" type="password"></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td>
						<input type="submit" value="login"/>
						<input type="reset" value="Reset">
					</td>
				</tr>
			</table>
				
	</form>
	<span class="error-message">${error}</span>
	</div>
	<jsp:include page="_footer.jsp"/>
</body>
</html>