<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="update.do" method="post">
		<p>
			<label>번호 : </label>
			<input type="text" name="article_no" value="${no }" readonly="readonly"> 
		</p>
		<p>
			<label>제목 : </label>
			<input type="text" name="title" value="${article.title }">
		</p>
		<p>
			<label>내용 : </label>
			<textarea name="content">${article.content }</textarea>
		</p>	
		<!-- value에서 no, article은 ArticleUpdateHandler.java에서 setAttribute로 넘어온 것임 -->
		<input type="submit" value="수정">
	</form>
</body>
</html>
