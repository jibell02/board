<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/common.css"> --%>
<script src="${pageContext.request.contextPath }/js/common.js"></script>
<style>
	table, td{
	border:1px solid black;
	border-collapse: collapse;
	padding:10px;
}
	
</style>
</head>
<body>
	<table>
		<tr>
			<td>번호</td>
			<td>${article.article_no}</td>
		</tr>
		<tr>
			<td>아이디</td>
			<td>${article.writer_id}</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${article.title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${article.content}</td>
		</tr>
		<tr>
			
			<td colspan="2">
				<a href="${pageContext.request.contextPath }/article/list.do">[목록]</a>
				<c:if test="${Auth == article.writer_id }"> <!-- 작성자만 삭제, 수정 가능하게 하기위해 로그인한 아이디와 작성자 아이디를 비교 -->
					<a href="${pageContext.request.contextPath }/article/update.do?no=${article.article_no}">[게시글 수정]</a>
					<a href="${pageContext.request.contextPath }/article/delete.do?no=${article.article_no}">[게시글 삭제]</a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>