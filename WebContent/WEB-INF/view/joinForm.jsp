<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			
			// 아이디(영어, 숫자 6~15)
			var idReg = /^[a-z][a-z0-9]{5,14}$/i;
			var id = $("input[name='memberid']").val();
			if(idReg.test(id) == false ){
				$("input[name='memberid']").next().css("display", "inline");
				return false; //전송 취소
			}
			
			// 이름
			var nameReg = /^[가-힣]{2,5}$/;
			var name = $("input[name='name']").val();
			if(nameReg.test(name) == false ){
				$("input[name='name']").next().css("display", "inline");
				return false; //전송 취소
			}
			
			// 비밀번호
			var pswReg = /^[a-z0-9]{8,20}$/i;
			var password = $("input[name='password']").val();
			if(pswReg.test(password) == false ){
				$("input[name='password']").next().css("display", "inline");
				return false; //전송 취소
			}
			
			// 비밀번호 일치 확인
			if($("input[name='confirmPassword']").val() != $("input[name='password']").val()){
				/* if($("input[name='confirmPassword']").val() == ""){
					$(".error").css("display", "none");
					$("input[name='confirmPassword']").next().css("display", "inline");
					return false;
				} */
				$("input[name='confirmPassword']").next().next().css("display", "inline");
				return false;
			}
			
						
			return true;
		})
	})
</script>
</head>
<body>
	<form action="join.do" method="post">
		<fieldset>
			<legend>회원가입</legend>
			<p>
				<label>아이디</label>
				<input type="text" name="memberid">
				<span class="error">아이디(영어, 숫자 6~15자리)를 입력하세요</span>
			</p>
			<p>
				<label>이름</label>
				<input type="text" name="name">
				<span class="error">이름(한글 2~5자리)을 입력하세요</span>
			</p>
			<p>
				<label>비밀번호</label>
				<input type="password" name="password">
				<span class="error">비밀번호(영어, 숫자 8~20자리)를 입력하세요</span>
			</p>
			<p>
				<label>비밀번호 확인</label>
				<input type="password" name="confirmPassword">
				<span class="error">비밀번호 확인란을 입력해주세요</span>
				<span class="error">비밀번호가 일치하지 않습니다</span>
			</p>
			<p>
				<input type="submit" value="가입">
			</p>
		</fieldset>
	</form>
</body>
</html>
