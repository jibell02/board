<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${result > 0 }">
		<h3>회원 가입에 성공하였습니다.</h3>
	</c:if>
	
	<c:if test="${result <= 0 }">
		<h3>회원 가입에 실패했습니다.</h3>
	</c:if>

	<!-- <a href="/board">홈</a> 프로젝트 이름 -->
	<!-- <a href="index.jsp">홈</a> 두가지 방법 모두 괜찮음.-->
	<%-- <a href="<%request.getContextPath %>">홈</a> --%>
	<a href="${pageContext.request.contextPath}">홈</a> 
</body>
</html>
