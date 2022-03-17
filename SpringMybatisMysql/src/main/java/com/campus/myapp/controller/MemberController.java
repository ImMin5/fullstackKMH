package com.campus.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.MemberService;
import com.campus.myapp.vo.MemberVO;

@Controller
@RequestMapping("/member/")
public class MemberController {
	@Inject
	MemberService service;
	
	//회원가입 폼으로 이동하는 매핑
	@GetMapping("memberForm")
	public String memberForm() {
		System.out.println("memberForm");
		return "/member/memberForm";
	}
	
	
	//회원 등록
	@RequestMapping(value="memberOk", method=RequestMethod.POST)
	public String memberFormOk(MemberVO vo, Model model) {
		int cnt = service.memberInsert(vo);
		
		//클라이언트 페이지로 insert 결과를 보냄
		model.addAttribute("cnt", cnt);
		
		return "member/memberResult";
	}
	
	//로그인 폼으로 이동
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	//로그인
	@PostMapping("loginOk")
	public ModelAndView loginOk(MemberVO vo, HttpSession session) {
		//requset userid, userpw와 일치하는 레코드 선택
		System.out.println("login : " + vo.getUserid() + " " + vo.getUserid());
		MemberVO mvo = service.loginCheck(vo);
		ModelAndView mav = new ModelAndView();
		
		//로그인 성공: session에 아이디와 이름을 저장한다. -> 홈페이지로 이동
		 if(mvo != null) {
			 System.out.println("login ok");
			 session.setAttribute("logId", mvo.getUserid());
			 session.setAttribute("logName", mvo.getUserid());
			 session.setAttribute("logStatus", "Y");
			 System.out.println("login mvo : " + mvo.getUserid()  + " " + mvo.getUserpwd());
			 //컨트롤러에서 다른 컨트롤러 매핑주소를 바로 호출한다.(접속)
			 mav.setViewName("redirect:/");
		 }else {//로그린 실패 : 로그인 폼으로 이동
			 System.out.println("login failed");
			 mav.setViewName("redirect:loginForm");
		 }
		
		return mav;
	}
	//로그아웃
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	//회원정보 수정 (폼)
	@GetMapping("memberEdit")
	public ModelAndView memberEdit(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userid = (String)session.getAttribute("logId");
		MemberVO vo = service.memberSelect(userid);
		
		mav.addObject("vo", vo);
		mav.setViewName("member/memberEdit");
		return mav;
	}
	//회원 정보 수정 (DB)
	@PostMapping("memberEditOk")
	public ModelAndView memberEdit(MemberVO vo, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//session 로그인 아이디...
		
		System.out.println("memberEdit start");
		vo.setUserid((String)session.getAttribute("logId"));
		int cnt = service.memberUpdate(vo);
		System.out.println("update cnt : " + cnt);
		mav.setViewName("redirect:memberEdit");
		return mav;
	}
	
	//아이디 중복검사 
	@PostMapping("memberIdCheck")
	@ResponseBody
	public int idCheck(String userid) {
		System.out.println("userid : " + userid);
		int cnt = service.idCheck(userid);
		
		System.out.println("idCheck cnt : " + cnt + " userid : " +  userid);
		return cnt;
	}
	
}
