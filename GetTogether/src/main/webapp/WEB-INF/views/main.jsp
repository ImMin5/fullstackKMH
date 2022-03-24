<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="url" value="<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="static/logo/favicon.ico" type="image/x-icon" sizes="16x16">
<meta charset="UTF-8">
<title>Get Together</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
<link href="https://hangeul.pstatic.net/hangeul_static/css/maru-buri.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script>
	$(function(){
		$("#footer_year").text(new Date().getFullYear());
	});
	
	<!-- 검색 기능 -->
	$(document).ready(function(){
		$("#search_input").on("keyup", function() {
			var value = $(this).val().toLowerCase();
			$("tbody tr").filter(function() {
				$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
			});
		});
	});
</script>
<style>
	*{font-family: 'MaruBuri';}
	html,body{height:100vh;postion:relative;}
	#wrap{margin:0; padding:0;}
	.bi-search, .btn{border: 1px solid #C38F5C; color:white; background-color:#C38F5C;}
	.carousel-inner{margin:0 auto;}
	.card{float:left;width:29%; height:450px; margin:3% 2% 3% 2%; }
	#wrap{width:100%;min-height: 100%;}
	#navbar{}
	
	#main{padding-top:20px; min-height: 100vh;}
	#search_section{width:70%;}
	
	#main>#group_table>table{ margin:0 auto; width:95%; overflow:auto;}
	#main>#group_table thead{font-size:0.8em;}
	#main>#group_table tbody{font-size:0.8em;}
	#joined_group{height:40%; 

	}
	#joined_group>#joined_group_box{ 
	
	}
	#joined_group>#joined_gruop_box>div{float:left;}
	#main>#group_table>nav{padding-top:10px;}
	#main>#group_table>nav a {color:#C38F5C;}
	footer{}
	
	/*반응형 스타일 적용하기*/
	/*320px ~ 600px*/
	/* all,screen, print, tv, projection... */
	@media all and (min-width:320px) and (max-width:600px) {
		.card{height:42vh;}
	@media all and (min-width:601px) and (max-width:700px){
		.card{height:38vh;}
	}
	/*601px~ 900px*/
	@media all and (min-width:701px) and (max-width:900px){
		.card{height:35vh;}
	}
	@media all and (min-width:901px){
		.card{height:30vh;}
	}
	
</style>
</head>
<body>
<div class="container-fluid" id="wrap">
		<nav id="navbar" class="navbar navbar-expand-lg navbar-light">
			<div class="container-fluid">
				<a id="brand_logo" class="navbar-brand" href="${url}"><img src="static/logo/horizontal_logo.png" width="130" alt=""></a>
				<button class="navbar-toggler" id="naver_btn" type="button" data-bs-toggle="collapse" data-bs-target="#navbar_toggle" >
				<span class="navbar-toggler-icon"></span>
				</button>
				<div class="nav justify-content-end navbar-collapse collapse" id="navbar_toggle">
					<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="${url}/main/club/clubForm">그룹 만들기</a></li>
						<li class="nav-item"><a class="nav-link" href="${url}/main/mypage">마이페이지</a></li>
						<li class="nav-item"><a class="nav-link" href="${url}/member/logout">로그아웃</a></li>
					</ul>
				</div>
				
			</div>
		</nav>
	<main id="main">
		<div id="search_section" class="container">
			<div class="input-group mb-3">
			    <input type="search" id="search_input" class="form-control" placeholder="검색하기"/>
			    <button type="button" class="btn"><i class="bi bi-search"></i></button>
			</div>
		</div>
		<div id="joined_group">
			<div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
			
			  <div class="carousel-inner">
			  <c:forEach   begin="0" items="${clublist}"  step="3" varStatus="status">
			  	<div class="carousel-item active">  
				<li>${status.index}</li>
			  	<li>${status.index+1}</li>
			  	<li>${status.index+2}</li>
					<div class="card mb-3" onclick="location.href='${url}/main/club/${clublist.get(staus.index).no}'">
					  <img src="${url}/static/img/${clublist[staus.index].clubthumbnail}" class="card-img-top" alt="...">
					  <div class="card-body">
					    <h5 class="card-title">${status.index}</h5>
					    <p class="card-text">${status.index}</p>
					    <p class="card-text"><small class="text-muted">인원:${clublist.get(staus.index).clubmember} 리뷰:${clublist.get(staus.index).clubpost}	</small></p>
					  </div>
					</div>
					<div class="card mb-3" onclick="location.href='${url}/main/club/${clublist.get(staus.index+1).no}'">
					  <img src="${url}/static/img/${clublist[status.index+1].clubthumbnail}" class="card-img-top" alt="...">
					  <div class="card-body">
					    <h5 class="card-title">${status.index+1}</h5>
					    <p class="card-text">${clublist.get(staus.index+1).description}</p>
					    <p class="card-text"><small class="text-muted">인원:${clublist.get(staus.index+1).clubmember} 리뷰:${clublist.get(staus.index+1).clubpost}	</small></p>
					  </div>
					</div>
					<div class="card mb-3" onclick="location.href='${url}/main/club/${clublist.get(staus.index+2).no}'">
					   <img src="${url}/static/img/${clublist.get(staus.index+2).clubthumbnail}" class="card-img-top" alt="...">
					  <div class="card-body">
					    <h5 class="card-title">${status.index+2}</h5>
					    <p class="card-text">${clublist.get(staus.index+2).description}</p>
					    <p class="card-text"><small class="text-muted">인원:${clublist.get(staus.index+2).clubmember} 리뷰:${clublist.get(staus.index+2).clubpost}	</small></p>
					  </div>
					</div>
			    </div>
			  
			  </c:forEach>
			  </div><!-- inner end -->
			  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span style="color:black;"class="visually-hidden">Previous</span>
			  </button>
			  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Next</span>
			  </button>
			</div>
		</div>
		<div id="group_table" class="table">
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col" style="width:7%;">#</th>
			      <th scope="col" style="width:23%;">그룸 이름</th>
			      <th scope="col" style="width:21%;">설명</th>
			      <th scope="col" style="width:14%;">인원수</th>
			      <th scope="col" style="width:14%;">리뷰수</th>
			      <th scope="col" style="width:21%;">그룹 생성일</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach var="vo" items="${clublistPublic}">
			   <tr onclick="location.href='${url}/main/club/${vo.no}'">
			  	 <th scope="row">${vo.no}</th>
			      <td>${vo.clubid}</td>
			      <td>${vo.description}</td>
			      <td>${vo.clubmember}</td>
			      <td>${vo.clubpost }개</td>
			      <td>${vo.createdate }</td>
			   </tr>
			  </c:forEach>
			  </tbody>
			</table>
			<nav>
			  <ul class="pagination justify-content-center">
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <li class="page-item"><a class="page-link" href="#">1</a></li>
			    <li class="page-item"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</div>
	</main>
	<div id="bottombar">
	</div>
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