<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product List</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
	<jsp:include page="_header.jsp"/>
	<jsp:include page="_menu.jsp"/>


	<fmt:setLocale value="en_TR" scope="session"/>
	<div class="page-title">Product List</div>
	
	<c:forEach items="${paginationProduct.list}" var="prodInfo">
		<div class="product-preview-container">
			<ul>
				<li><img alt="" src="${pageContext.request.contextPath}/productImage?code=${prodInfo.code}" class="product-image"/></li>
				<li>Code:${prodInfo.code}</li>	
				<li>Name:${prodInfo.Name}</li>
				<li>Price:<fmt:formatNumber value="${proddInfo.price}" type="currency"/></li>
				<li><a href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}">Buy Now</a></li>
				//for manager edit product
				<security:authorize access="hasRole('ROLE_MANAGER')">
					<li><a style="color:red"; href="${pageContext.request.contextPath}/product?code=${prodInfo.code}">Edit Product</a></li>
				</security:authorize>
			</ul>
		</div>
	</c:forEach>
	<br>
	
	 <c:if test="${paginationProducts.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationProducts.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="productList?page=${page}" class="nav-item">${page}</a>
              </c:if>
              <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
              </c:if>
          </c:forEach>
          
       </div>
   </c:if>
	
	<jsp:include page="_footer.jsp"/>
</body>
</html>