<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table, td{
		border: 1px solid black;
		border-collapse: collapse;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<td>아이디</td>
			<td>성명</td>
			<td>비밀번호</td>
			<td>날짜</td>
		</tr>		
		<c:forEach var="m" items="${member }">
			<tr>
				<td>${m.memberid }</td>
				<td>${m.name }</td>
				<td>${m.password }</td>
				<td>${m.regdate }</td>
			</tr>		
		</c:forEach>
	</table>
	
	<a href="index.jsp">[홈]</a>
</body>
</html>
