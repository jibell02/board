<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${Auth != null }">
		<h1>[${Auth }]님, 반갑습니다.</h1>
		<a href="logout.do">[로그아웃]</a>
		<a href="changePwd.do">[비밀번호 변경]</a>
		<a href="${pageContext.request.contextPath }/article/list.do">[게시판 리스트]</a>
		
		
	</c:if>

	<c:if test="${Auth == null }">
		<a href="join.do">[회원가입]</a>
		<a href="login.do">[로그인]</a>
	</c:if>

	<a href="memberlist.do">[회원 리스트 보기]</a>

</body>
</html>