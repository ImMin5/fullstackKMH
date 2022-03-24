package com.campus.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.ClubService;
import com.campus.myapp.service.ReviewService;
import com.campus.myapp.vo.ClubVO;
import com.campus.myapp.vo.ReviewVO;

@RestController
public class ReviewController {
	
	@Inject
	ReviewService service;
	@Inject
	ClubService serviceC;
	//reivew view
	@GetMapping("/main/club/{clubno}/review/{reivewno}")
	public ModelAndView review(@PathVariable("clubno") int clubno, @PathVariable("reivewno") int reivewno,HttpSession session ) {
		ModelAndView mav = new ModelAndView();
		ClubVO cvo = serviceC.clubSelectOne(clubno);
		ReviewVO rvo = service.reviewSelectOne(reivewno);
		mav.addObject("cvo", cvo);
		mav.addObject("rvo", rvo);
		mav.setViewName("review/review");
		return mav;
	}
	
	//review 폼
	@GetMapping("/main/club/{clubno}/review_form")
	public ModelAndView reviewForm(@PathVariable("clubno") int clubno, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("review/review_form");
		return mav;
	}
	
	
	// review 폼 ok
	@PostMapping("main/review/reviewOk/{clubno}")
	public ResponseEntity<String> reviewForm(@PathVariable("clubno") int clubno, ReviewVO vo,HttpServletRequest request, HttpSession session) {
		String userid = (String)session.getAttribute("logId");
		String username = (String)session.getAttribute("logName");
		String ip = request.getRemoteAddr();
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		String msg = "";
		vo.setUserid(userid);
		vo.setUsername(username);
		vo.setIp(ip);
		vo.setClubno(clubno);
		System.out.println("제목 :" + vo.getSubject());
		System.out.println("내용 : "+ vo.getContent());
		System.out.println("방문 일 : " + vo.getVisitdate());
		System.out.println("재방문 의사 : " + vo.isRevisit());
		System.out.println(vo.getScore());	
		
		try {
			service.reviewInsert(vo);
			serviceC.clubUpdatePost(clubno);
			msg = getSuccessMessage("리뷰 작성완료",request.getContextPath()+"/main/club/"+clubno);
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			msg= getFailMessage("리뷰 작성 실패...");
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//글 수정 메세지
			public String getSuccessMessage(String msg, String url) {
				String alert = "<script>";
				alert += "alert('"+msg+".\\n');";
				alert += "location.href='"+url+"';";
				alert += "</script> ";
			
				return alert;
			}
			public String getFailMessage(String msg) {
				String alert = "<script>";
				alert += "alert('"+msg+".\\n');";
				alert += "history.back();";
				alert += "</script> ";	
				return alert;
			}
}
