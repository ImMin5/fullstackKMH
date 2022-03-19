<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<style>
	#dataList>li{
		float:left;
		width:10%; height:40px; line-height:40px; border-bottom:1px solid gary;
	}
	#dataList>li:nth-child(5n+2){
		width:51%;
		white-space:nowrap; /* 줄 안바꿈*/
		overflow:hidden; /* 넘친내용 숨기기 */
		text-overflow:ellipsis; /* 넘침시 ...표시*/
	}
	
	#dataList>li:nth-child(5n+5){
		width:12%;
		white-space:nowrap; /* 줄 안바꿈*/
		overflow:hidden; /* 넘친내용 숨기기 */
		text-overflow:ellipsis; /* 넘침시 ...표시*/
	}
</style>
<div class="container">
	<h1>자료실 목록</h1>
	<a href="/myapp/data/write"> 글쓰기</a>
	<ul id="dataList">
		<li>번호</li>
		<li>제목</li>
		<li>글쓴이</li>
		<li>첨부파일</li>
		<li>등록일</li>
		
		<c:forEach var="vo" items="${list}">
			<li>${vo.no}</li>
			<li><a href="/myapp/data/view?no=${vo.no}">${vo.subject}</a></li>
			<li>${vo.userid }</li>
			<li>
				<!-- 첫번째 첨부파일 -->
				<a href="/myapp/upload/${vo.filename1}" download><img src="/myapp/img/disk.png" title="${vo.filename1}"/></a>
				<!-- 두번째 첨부파일 -->
				<c:if test="${vo.filename2 != '' && vo.filename2 != null}">
					<a href="/myapp/upload/${vo.filename2}" download><img src="/myapp/img/disk.png" title="${vo.filename2 }"/></a>
				</c:if>
			</li>
			<li>${vo.writedate }</li>
		</c:forEach>
	</ul>
</div>