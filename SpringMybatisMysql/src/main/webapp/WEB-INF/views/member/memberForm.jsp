<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
	#mFrm li{
		float:left;
		height:40px;
		line-height:40px;
		width:20%;
		border-bottom:1px solid #ddd;
	}
	#mFrm li:nth-child(2n){
		width:80%;
	}
	#mFrm li:last-of-type{width:100%}
</style>
<script src="/myapp/js/member.js"></script>
<script>
	$(function(){
		//아이디 중복검사
		$("#userid").keyup(function(){
			var userid= $("#userid").val();
			console.log(userid);
			if(userid != '' && userid.length>=6){
				var url = "/myapp/member/memberIdCheck";
				$.ajax({
					url : url,
					data : "userid="+userid,
					type: "POST",
					success:function(result){
						if(result > 0){
							//사용불가
							$("#chk").html("사용불가합니다");
							$("#idchk").val("N");
							$("#chk").css("color","red");
						}
						else{
							//사용가능
							$("#chk").html("사용가능합니다.");
							$("#idchk").val("Y");
							$("#chk").css("color","blue");
						}
					}
				})
			}else{
				//사이디를 입력 안한경우 사용 불가
				$("#chk").html("사용불가합니다");
				$("#idchk").val("N");
				$("#chk").css("color","red");
			}

		});
	});
</script>
<div class="container">
	
	<h1>회원가입 폼</h1>
	<form method="post" action="/myapp/member/memberOk" id="mFrm" onsubmit="return memberCheck();">
		<ul>
			<li>아이디</li>
			<li>
				<input type="text" name="userid" id="userid" placeholder="아이디입력"/>
				<input type="button" value="아이디 중복확인"/><span id="chk"></span>
				<input type="text" id="idchk" value="N"/>
			</li>
			
			<li>비밀번호</li>
			<li><input tpye="password" name="userpwd" id="userpwd" placeholder="비밀번호"/></li>
			<li>비밀번호확인</li>
			<li><input tpye="password" name="userpw2" id="userpwd2" placeholder="비밀번호 확인"/></li>
			<li>이름</li>
			<li><input type="text" name="username" id="username"/></li>
			<li>전화번호</li>
			<li>
				<select name="tel1">
					<option value="010">010</option>
					<option value="02">02</option>
					<option value="031">031</option>
					<option value="041">041</option>
				</select>
				<input tpye="text" name="tel2" id="tel2"/>
				<input type="text" name="tel3" id="tel3"/>
			</li>
			<li>이메일</li>
			<li><input type="text" name="email" id="email"/></li>
			<li><input type="submit" value="가입하기"/></li>
		</ul>
	</form>
</div>