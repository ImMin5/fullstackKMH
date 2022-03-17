<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script>
	function delCheck(){
		if(confirm("삭제하시겠습니까?")){
			//확인버튼 눌렀을 경우
			location.href = "/myapp/board/boardDel?no=${vo.no}";
		}
	}
	//댓글
	$(function(){
		//댓글 목록을 가져오는 함수
		function replyListAll(){
			console.log("reply start");
			var url = "/myapp/reply/list";
			var params = "no=${vo.no}"; // 
			
			$.ajax({
				url:url,
				data:params,
				success:function(result){
					var $result = $(result); // vo,vo,vo,vo,vo,...
					var tag = "<ul>";
					
					$result.each(function(idx,vo){
						tag += "<li><div>"+ vo.userid;
						tag += "(" + vo.writedate+")";
						if(vo.userid =='${logId}'){
							tag += "<input type='button' value='Edit'/>";
							tag += "<input type='button' title='"+vo.replyno+"' value='Del'/>";
						}
						tag += "<br/>"+vo.coment;
						tag += "</div>";
						
						//본인글일때 수정폼이 이어야한다.
						if(vo.userid == '${logId}'){
							tag += "<div style='display:none'><form method='post'>";
							tag += "<input type='hidden' name='replyno' value ='"+vo.replyno+"'/>";
							tag += "<textarea name='coment' style='width:400px'>"+vo.coment+"</textarea>";
							tag += "<input type='submit' value='수정'/>";
							tag += "</form></div>";
						}
						
					});
					tag += "<hr/></li></ul>";
					
					$("#replyList").html(tag);
	
					
				},error:function(e){
					console.log(e.responseText)
				}
			})
			
		}
		//댓글 등록
		$("#replyFrm").submit(function(){
			//form 기본 이벤트
			event.preventDefault();
			if($("#coment").val() == ""){
				alert("댓글을 입력해 주세요.");
				return false;
			}
			else{
				var params = $("#replyFrm").serialize();
				
				$.ajax({
					url:'/myapp/reply/writeOk',
					data:params,
					type:'POST',
					success:function(r){
						//폼을 초기화
						if($("#coment").val("")){
							alert("댓글을 입력 후 등록하세요.");
							return;
						}
						//댓글 목록이 refesh되어야 한다.
						replyListAll();
						
					},error:function(e){
						console.log(e.responseText);
					}
				})
			}
		});
		
		$(document).on('click',"#replyList input[value=Edit]", function(){
			//숨기기
			$(this).parent().css("display","none");
			//보여주기
			$(this).parent().next().css("display","block");
		});
		
		//댓ㄱ슬 수정 (DB)
		$(document).on("submit", "#replyList form", function(){
			event.preventDefault();
			//데이터
			console.log("reply edit");
			var params = $(this).serialize();
			var url = '/myapp/reply/editOk';
			$.ajax({
				url:url,
				data:params,
				type:"POST",
				success: function(result) {
					console.log(result);
					replyListAll();
				},error: function(){
					console.log('수정에러 발생');
				}
			})
		});
		//댓글 삭제 
		$(document).on("click", "#replyList input[value=Del]", function(){
			console.log("del");
			if(confirm("댓글을 삭제하겠습니까?")){
				var params = "replyno="+$(this).attr("title");
				$.ajax({
					url : '/myapp/reply/del',
					data:params,
					success:function(result){
						console.log(result);
						replyListAll();
					},error:function(){
						console.log("댓글 삭제 에러...");
					}
				});
			}
		});
		//현재글의 댓글
		replyListAll();
	});
</script>
<div class="container">
	<h1>글 내용 보기</h1>
	<ul>
		<li>번호 : ${vo.no}</li>
		<li>작성자 : ${vo.userid}</li>
		<li>작성일 : ${vo.writedate}, 조회수 : ${vo.hit }</li>
		<li>제목 : ${vo.subject}</li>
		<li>글 내용</li>
		<li>${vo.content}</li>
	</ul>
	<div>
		<!-- 로그인 아이디와 글쓴이가 같은 경우 수정 삭제 표시 -->
		<c:if test="${logId == vo.userid }">
			<a href="/myapp/board/boardEdit?no=${vo.no}">수정</a>
			<a href="javascript:delCheck()">삭제</a>
		</c:if>
	</div>
	<hr/>
	<!-- 댓글 쓰기 폼 -->
	<c:if test="${logStatus == 'Y'}">
		<form method="post" id="replyFrm">
			<input type="hidden" name="no" value="${vo.no}"/>
			<textarea name="coment" id="coment" style="width:500px; height:80px;"></textarea>
			<input type="submit" value="댓글 등록"/>
		</form>
	</c:if>
	<!-- 댓글 목록이 나올 자리 -->
	<div id="replyList">
	</div>
</div>