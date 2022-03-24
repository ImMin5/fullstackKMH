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
<!-- ck에디터 -->
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<style>
	*{font-family: 'MaruBuri';}
	html,body{height:100vh;postion:relative;}
	li{font-size:0.9em;}
	#wrap{margin:0; padding:0;}
	.bi-search, .btn{border: 1px solid #C38F5C; color:white; background-color:#C38F5C;}
	td{word-break:break-all; font-size:0.8em;}
	#wrap{width:100%;height: 100%;}
	#navbar{}
	#main{padding-top:20px; min-height: 100%;}
	#group_thubnail{width:60%; margin:0 auto; 
		border-radius:100px;
		margin-bottom:10px;
	}
	#review_score_title{ text-align:left;}
	.col-4, .rounded{text-align:center;}	
	footer{}
	
	/*  별표 점수 주기*/
	.star-rating { 
		border:solid 1px #ccc;
		display:flex;
	  	flex-direction: row-reverse;
	  	font-size:1.5em;
	  	justify-content:space-around;
	  	padding:0 .2em;
	  	text-align:center;
	  	width:5em;
	}
	.star-rating input {display:none;}
	.star-rating label {color:#ccc;cursor:pointer;}
	.star-rating :checked ~ label {color:#C38F5C;}
	.star-rating label:hover,.star-rating label:hover ~ label {color:#C38F5C;}
</style>
<script>
	var flag_map = false;
	var flag_score = false;
	
	$(function(){
		$("#footer_year").text(new Date().getFullYear());
	});
	
	function review_check(){
			var subject = $("#subject");
			var people = $("#people");
			var link = $("#link");
			var visitdate = $("#visitdate");
			var loaction = $("#location");
			var revisit = $("#revisit");
			var score = $("input[name=score]");
			
			console.log(score.val());
			if(flag_score== false){
				alert("평점을 표시해 주세요.");
				console.log("name :" +score.val() );
				return false;
			}
		
			
			
			if(subject.val() == ""){
				alert("제목을 입력해 주세요.");
				console.log("dddd");
				return false;
			}
			
			if(people.val() == ""){
				alert("인원수를 입력해 주세요.");
				return false;
			}
			if(link.val() == ""){
				alert("링크를 입력해 주세요.");
				return false;
			}
			if(visitdate.val() == ""){
				alert("방문 날짜를 입력해 주세요.");
				return false;
			}
			
			if(loaction.val() == ""){
				alert("위치를 입력해 주세요.");
				return false;
			}
			if(revisit.val() == ""){
				alert("재방문 의사를 표시해 주세요.");
				return false;
			}
			
		
		
	}
	$(function(){
		CKEDITOR.replace("content");
		$("#reviewFrm").submit(function(){
			//ckeditor의 textarea태그의 value를 얻어오는 함수
			var txt= CKEDITOR.instances.content.getData();
			console.log(txt);
			if(txt == ""){
				alert("글 내용을 입력하세요..");
				return false;
			}
			return true;
		});
	});
	
	
	
	$(document).ready(function(){
		
		//기본 평점 설정
	
		// 리뷰 작성 이동
		$(document).on("click", "#write_btn", function(){
			location.href='review_form.html';
		})
		
		 $(document).on('click','.star-rating input',function(e){
			$(this).attr("name","score");
	        $(this).nextAll("input").attr("name","");
			$(this).prevAll("input").attr("name","");
	        
			$(this).parent().children("i").removeClass("");  /* 별점의 on 클래스 전부 제거 */ 
	        $(this).addClass("bi bi-star-fill").prevAll("i").addClass("bi bi-star-fill"); /* 클릭한 별과, 그 앞 까지 별점에 on 클래스 추가 */
	        
	        // $(this).nextAll("i").addClass("bi bi-star"); /* 클릭한 별과, 그 앞 까지 별점에 on 클래스 추가 */
	       
	       flag_score=true;
	    })
	});
	  

	
</script>
</head>
<body>
<div class="container-fluid" id="wrap">
		<nav id="navbar" class="navbar navbar-expand-lg navbar-light">
			<div class="container-fluid">
				<a id="brand_logo" class="navbar-brand" href="main.html"><img src="${url}/static/logo/horizontal_logo.png" width="130" alt=""></a>
				<button class="navbar-toggler" id="naver_btn" type="button" data-bs-toggle="collapse" data-bs-target="#navbar_toggle" >
				<span class="navbar-toggler-icon"></span>
				</button>
				<div class="nav justify-content-end navbar-collapse collapse" id="navbar_toggle">
					<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="${url}/main/mypage.html">마이페이지</a></li>
					<li class="nav-item"><a class="nav-link" href="${url}/member/logout">로그아웃</a></li>
					</ul>
				</div>
				
			</div>
		</nav>
	<main id="main">
		<div class="row">
			<div class="col-4">
				<div class="rounded mx-auto d-block" onclick="${url}/main">
	  				<img id="group_thubnail" src="${url}/static/img/hamburger_01.jpg"  alt="커버사진">
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item" >양재동 수제버거 맛집</li>
					<li class="list-group-item">생성일 : 2021.02.02</li>
					<li class="list-group-item">그룹장 : gildong</li>
					<li class="list-group-item">인원	: 30명</li>
					<li class="list-group-item">리뷰 수 : 12개 </li>
					<li class="list-group-item" >평균 평점 : 4.5 </li>
					
				</ul>
			</div>
			<div class="col-8">
				<div class="container">
					<h3>리뷰 쓰기</h3>
					<hr/>
					<form method="post" id="reviewFrm" onsubmit="return review_check();" action="${url}/main/review/reviewOk/${clubno}">
						<div class="mb-3">
							<input class="form-control" type="text" name="subject" id="subject" placeholder="제목을 입력해 주세요."/>
						</div>
						<div class="mb-3">
							<input class="form-control" min="1" type="number" name="people" id="people" placeholder="인원수"/>
							<input class="form-control" type="text" name="link" id="link" placeholder="sns링크"/>
						</div>
						<div class="mb-3">
							<input class="form-control" type="date" name="visitdate" id="visitdate" placeholder="방문 날짜"/>
						</div>
						<div class="mb-3">
							<input class="form-control" type="text" name="location" id="location" placeholder="위치"/>
						</div>
						<div class="input-group mb-3">
						  <div class="input-group-text" style="background-color:white;">
						    <input class="form-check-input mt-0" type="checkbox" name="revisit" id="revisit" >
						  </div>
						  <span class="input-group-text" style="background-color:white;">재방문 의사</span>
						</div>
				       <div id="review_score" class="mb-3 input-group">
				            <div class="star-rating">
							  <input type="radio" id="5-stars" class="form-control" name="rating" value="5" />
							  <label for="5-stars" class="star">&#9733;</label>
							  <input type="radio" id="4-stars" name="rating" value="4" />
							  <label for="4-stars" class="star">&#9733;</label>
							  <input type="radio" id="3-stars" name="rating" value="3" />
							  <label for="3-stars" class="star">&#9733;</label>
							  <input type="radio" id="2-stars" name="rating" value="2" />
							  <label for="2-stars" class="star">&#9733;</label>
							  <input type="radio" id="1-star" name="rating" value="1" />
							  <label for="1-star" class="star">&#9733;</label>
							</div>
							<span class="input-group-text" style="background-color:white;">평점 </span>
				        </div>
						<div class="mb-3">
							<textarea name="content" id="content" placeholder="내용을 입력하세요.">
							1. 분위기 : <br/>
							2. 서비스 : <br/>
							3. 주차 : <br/>
							4. 후기 (자유 형식) : <br/> 
							</textarea>
						</div>
						<div class="mb-3">
							<input class="btn" type="submit" value="등록"/>
						</div>
					</form>
				</div>
			</div>
		
		</div>
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