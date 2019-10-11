<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/common.css" type="text/css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script>
	$(function(){
		$("form").submit(function(){
			$(".error").css("display", "none");
			
			var result = checkInputEmpty($("input[name]"));
			if(result == false){ // 빈 input태그가 존재하면
				return false;
			}
			
		})
	})
</script>
</head>
<body>
	<form action="changePwd.do" method="post">
		<fieldset>
			<input type="hidden" name="memberid" value="${Auth }">
			<legend>비밀번호 변경</legend>
			<p>
				<label>현재 암호</label>
				<input type="password" name="password">
				<span class="error">현재 비밀번호를 입력하세요.</span>
				<c:if test="${notMatch != null }">
					<p style="color:red">비밀번호가 일치하지 않습니다.</p>
				</c:if>
			</p> 
			<p>
				<label>비밀번호</label>
				<input type="password" name="newPassword">
				<span class="error">새 비밀번호를 입력하세요.</span>
			</p>
			<p>
				<input type="submit" value="암호변경">
			</p>
		</fieldset>
	</form>
</body>
</html>
