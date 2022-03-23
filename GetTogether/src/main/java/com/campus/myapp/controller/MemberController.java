package com.campus.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import com.campus.myapp.service.MemberService;
import com.campus.myapp.vo.MemberVO;

@RestController
public class MemberController {
	@Inject
	MemberService service;
	
	//회원가입 view
	@GetMapping("/signup")
	public ModelAndView signup(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/signup");
		
		return mav;
	}
	
	//회원가입ok
	@PostMapping("/member/signupOk")
	public ResponseEntity<String> signupOk(MemberVO vo, HttpSession session){
		
		System.out.println(vo.getUserid());
		System.out.println(vo.getUsername());
		System.out.println(vo.getUserpassword());
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		String msg = "";
		
		try {
			service.memberInsert(vo);
			msg = getSuccessMessage("회원 가입이 완료 되었습니다.","/myapp/login");
			entity = new ResponseEntity<String>(msg,headers, HttpStatus.OK);
		
		}catch(Exception e) {
			msg = getFailMessage("회원 가입 실패.");
			entity = new ResponseEntity<String>(msg,headers, HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		return entity;
	}
	
	//로그인 view
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/login");
		return mav;
	}
	
	//로그인 ok
	@PostMapping("/member/loginOk")
	public ModelAndView loginOk(MemberVO vo, HttpSession session){
		ModelAndView mav = new ModelAndView();
		vo = service.loginCheck(vo);
		
		if(vo != null) {
			session.setAttribute("logId", vo.getUserid());
			session.setAttribute("logName", vo.getUsername());
			session.setAttribute("logStatus", "Y");
			mav.setViewName("redirect:/main");
		}else{
			mav.setViewName("redirect:/login");
		}
		
		return mav;
	}
	
	//아이디 중복체크
	@PostMapping("/member/idCheck")
	@ResponseBody
	public String idCheck(String userid){
		System.out.println("id check : " + userid);
		int result = service.idCheck(userid);
		return Integer.toString(result);
	}
	//닉네임 중복체크
	@PostMapping("/member/usernameCheck")
	@ResponseBody
	public String usernameCheck(String username){
		System.out.println("id check : " + username);
		int result = service.idCheck(username);
		return Integer.toString(result);
	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public ModelAndView logout(HttpSession session){
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	//마이페이지
	@GetMapping("/main/mypage")
	public ModelAndView mypage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/mypage");
		return mav;
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
