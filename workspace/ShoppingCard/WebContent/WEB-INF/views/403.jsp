<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Acces Denied</title>
<link rel="style/sheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
	<jsp:include page="_header.jsp"/>
	<jsp:include page="_menu.jsp"/>
	
	<div class="page-title">Acces Denied!</div>
	<h3 style="color:red;">sorry not access this page</h3>
	
	<jsp:include page="_footer.jsp"/>
</body>
</html>