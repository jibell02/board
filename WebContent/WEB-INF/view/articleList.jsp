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
		border:1px solid black;
		border-collapse: collapse;
		padding:10px;
		text-align: center;
	}
</style>
</head>
<body>
	<p>
		<a href="add.do">등록하기</a>
	</p>
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>조회수</td>
		</tr>
		<c:forEach var="a" items="${list }">
			<tr>
				<td>${a.article_no }</td>
				<td><a href="read.do?no=${a.article_no }">${a.title }</a></td>
				<td>${a.writer_id }</td>
				<td>${a.read_cnt }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
