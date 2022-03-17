<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#subject{width:98%;}
	#boardFrm li{
		padding:10px 5px;
	}
</style>
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<script>
	$(function(){
		
		if(${logId != vo.userid}){
			history.back();
		}
		CKEDITOR.replace("content");
		
		//샗 추가되는 첨부파일
		$("#dataFrm b").click(function(){
			console.log("22");
			$(this).parent().css("display","none");
			$(this).parent().next().attr("name","delFile");
			$(this).parent().next().next().attr("type","file");
		})
		
		$("#dataFrm").submit(function(event){
			if($("#subject").val() ==''){
				alert("글 제목을 입력하세요.");
				return false;
			}
			if(CKEDITOR.instances.content.getData() == ''){
				alert("글 내용을 입력해주세요.");
				return false;
			}
			
			
			
			//첨부 파일 선택 갯수
			if($("#filename1").val() != ''){
				fileCount++;
			}
			if($("#filename2").val() != ''){
				fileCount++;
			}
			
			console.log("file Count : " + fileCount);
			if(fileCount<1){
				alert("첨부파일은 반드시 1개 이상이어야 합니다.");
				return false;
			}
			
			return false;
		});
	});
</script>
<div class="container">
	<h1>글 수정 폼 </h1>
	<form id="dataFrm" method="post" action="/myapp/data/editOk" enctype="multipart/form-data">
		<input type="hidden" name="no" value="${vo.no}"/>
		<ul>
			<li>제목</li>
			<li><input type="text" name="subject" id="subject" value="${vo.subject }"/></li>
			<li>글내용</li>
			<li><textarea name="content" id="content">${vo.content}</textarea></li>
			<li>첨부파일</li>
			<li>
				<!-- 첫번째 첨부파일 -->
				<div>${vo.filename1 } &nbsp;&nbsp; <b>X</b></div>
				<!-- x를 누르면 삭제 파일명이 있는 input의 name속성값을 delFile -->
				<input type="hidden" name="" value="${vo.filename1}"/>
				<!-- x를 누르면 파일이 삭제되고 새로운 파일 선택이 선택 가능하도록 input만들어 준다. -->
				<input type="hidden" name="filename" id="filename1"/>
			</li>
			<li>
			<!-- 두번쨰 첨부파일 있을때 -->
			<c:if test="${vo.filename2 != '' && vo.filename2 != null}">
				
					<div>${vo.filename2 } &nbsp;&nbsp; <b>X</b></div>
					<!-- x를 누르면 삭제 파일명이 있는 input의 name속성값을 delFile -->
					<input type="hidden" name="" value="${vo.filename2}"/>
					<!-- x를 누르면 파일이 삭제되고 새로운 파일 선택이 선택 가능하도록 input만들어 준다. -->
					<input type="hidden" name="filename" id="filename2"/>
				
			</c:if>
			<!-- 두번째 첨부파일 없을 때  -->
			<c:if test="${vo.filename2 == null || vo.filename2==''}">
					<input type="file" name="filename" id="filename2"/>
			</c:if>
			
			</li>
			<li><input type="submit" value="자료실 글 수정"/></li>
		</ul>
	</form>
</div>