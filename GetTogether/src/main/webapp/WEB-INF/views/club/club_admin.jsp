<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="url" value="<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="${url}/static/logo/favicon.ico" type="image/x-icon" sizes="16x16">
<meta charset="UTF-8">
<title>양재동 수제버거 맛집 | Get Together</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
<link href="https://hangeul.pstatic.net/hangeul_static/css/maru-buri.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>


<script>
	var flag_map = false;
	
	$(function(){
		$("#footer_year").text(new Date().getFullYear());
	});
	
	$(document).ready(function(){
		
	});
	

	
</script>
<style>
	*{font-family: 'MaruBuri';}
	html,body{height:100vh;postion:relative;}
	h5{border-bottom:1px solid #C38F5C; width:40%; font-size:1.1em;}
	li{font-size:0.9em;}
	.container{padding-bottom:40px;}
	#wrap{margin:0; padding:0;}
	.bi-search, .btn{border: 1px solid #C38F5C; color:white; background-color:#C38F5C;}
	thead{font-size:0.9em;}
	td{word-break:break-all; font-size:0.8em;}
	#wrap{width:100%;height: 100%;}
	#navbar{}
	#main{padding-top:20px; min-height: 100%;}
	#group_thubnail{width:60%; margin:0 auto; 
		border-radius:100px;
		margin-bottom:10px;
	}
	.col-4, .rounded{text-align:center;}

	
	footer{}
	
</style>
</head>
<body>
<div class="container-fluid" id="wrap">
		<nav id="navbar" class="navbar navbar-expand-lg navbar-light">
			<div class="container-fluid">
				<a id="brand_logo" class="navbar-brand" href="${url}/main"><img src="${url}/static/logo/horizontal_logo.png" width="130" alt=""></a>
				<button class="navbar-toggler" id="naver_btn" type="button" data-bs-toggle="collapse" data-bs-target="#navbar_toggle" >
				<span class="navbar-toggler-icon"></span>
				</button>
				<div class="nav justify-content-end navbar-collapse collapse" id="navbar_toggle">
					<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="${url}/main/mypage">마이페이지</a></li>
					<li class="nav-item"><a class="nav-link" href="${url}/member/logout">로그아웃</a></li>
					</ul>
				</div>
				
			</div>
		</nav>
	<main id="main">
		<div class="row">
			<div class="col-4">
				<div class="rounded mx-auto d-block" onclick="location.href='${url}/main/club/${clubno}'">
	  				<img id="group_thubnail" src="${url}/static/img/${cvo.clubthumbnail}"  alt="커버사진">
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item" >${cvo.clubid}</li>
					<li class="list-group-item">생성일 : ${cvo.createdate }</li>
					<li class="list-group-item">그룹장 : ${clubadmin}</li>
					<li class="list-group-item">인원	: ${cvo.clubmember} 명</li>
					<li class="list-group-item">리뷰 수 : ${cvo.clubpost}개</li>
					<li class="list-group-item" >
						
								공지사항 입니다.
								<c:if test="${cvo.clubnotice != null}">
									${cvo.clubnotice}
								</c:if>
					
					</li>
					
					
				</ul>
			</div>
			<div class="col-8">
				<div id="groupinfo_section" class="container">
					<h5 class="title">그룹 정보 </h5>
					<form id="groupinfo_section_form">
						<div class="input-group mb-3">
							<input type="text" class="form-control" value="${cvo.clubid}"/>
							<input type="button" class="btn" value="변경"/>
						</div>
						<div class="input-group mb-3">
							<input type="text" class="form-control" value="${cvo.clubnotice }" placeholder="공지사항 "/>
							<input type="button" class="btn" id="userid_check_btn" value="변경"/>
						</div>
					</form>
				</div>
				<div id="member_section" class="container">
					<h5 id="member_section_title">멤버 관리 </h5>
					<table id="member_section_table" class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>아이디</th>
								<th>닉네임</th>
								<th>가입일</th>
							<th style="min-width:62px;" >비고</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="vo" items="${clubmemberlist}">
							<tr>
								<td>${vo.no}</td>
								<td>${vo.userid}</td>
								<td>${vo.username}</td>
								<td>${vo.joindate}</td>
								<td>
									<button class="btn btn-sm">강퇴</button>
									<button class="btn btn-sm">양도</button>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				
				<div id="post_section" class="container">
					<h5 id="post_section_title">리뷰 관리 </h5>
					<table id="postr_section_table" class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
							<th style="min-width:62px;" >비고</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="vo" items="${reviewlist}">
						<tr>
								<td>${vo.no}</td>
								<td onclick="location.href='${url}/main/club/${clubno}/review/${vo.no}'">${vo.subject}</td>
								<td>${vo.username}</td>
								<td>${vo.writedate}</td>
								<td>
									<button class="btn btn-sm">삭제</button>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				
				<!-- 그룹 가입 요청 현황 -->
				<div id="invited_section" class="container">
					<h5 id="invited_title">그룹 가입 신청 현황</h5>
					<table id="invited_section_table" class="table">
						<thead>
							<tr>
								<th>#</th>
								<th scope="col">아아디</th>
								<th scope="col">닉네임</th>
								<th scope="col">요청 시간</th>
								<th scope="col" style="min-width:62px;" >비고</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="vo" items="${submitlist}">
							<tr>
								<td>${vo.no}</td>
								<td>${vo.userid}</td>
								<td>${vo.username }</td>
								<td>${vo.createdate }</td>
								<td id="option_btn">
									<button class="btn btn-sm">수락</button>
									<button class="btn btn-sm">거절</button>
								</td>
							</tr>						
						</c:forEach>
							
						</tbody>
					</table>
				</div>
				<!-- 그룹 초대 신청 현황 -->
				<div id="invited_section" class="container">
					<h5 id="invited_title">그룹 초대 신청 현황</h5>
					<table id="invited_section_table" class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>아이디</th>
								<th>초대한 사람</th>
								<th>요청 시간</th>								
								<th style="min-width:62px;">비고</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="vo" items="${invitelist}">
							<tr>
								<td>${vo.no}</td>
								<td>${vo.userid }</td>
								<td>${vo.username }</td>
								<td>${vo.createdate }</td>
								<td>
									<button class="btn btn-sm" disabled>대기</button>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div> <!-- col-8 end -->
		</div> <!-- row end -->
	</main>
	<div id="bottombar"></div>
	<footer class="bg-light text-center text-lg-star">
		  
		  <!-- Copyright -->
		  <div class="text-center p-3" style="background-color: #C38F5C;">
		    © 2022-<span id="footer_year"></span> Copyright:
		    <a class="text-dark" href="#">Get Together</a>
		  </div>
	  	<!-- Copyright -->
	</footer>
</div>


</body>
</html>