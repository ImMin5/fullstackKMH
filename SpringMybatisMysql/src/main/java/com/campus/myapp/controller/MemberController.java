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
	
	//ȸ������ ������ �̵��ϴ� ����
	@GetMapping("memberForm")
	public String memberForm() {
		System.out.println("memberForm");
		return "/member/memberForm";
	}
	
	
	//ȸ�� ���
	@RequestMapping(value="memberOk", method=RequestMethod.POST)
	public String memberFormOk(MemberVO vo, Model model) {
		int cnt = service.memberInsert(vo);
		
		//Ŭ���̾�Ʈ �������� insert ����� ����
		model.addAttribute("cnt", cnt);
		
		return "member/memberResult";
	}
	
	//�α��� ������ �̵�
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	//�α���
	@PostMapping("loginOk")
	public ModelAndView loginOk(MemberVO vo, HttpSession session) {
		//requset userid, userpw�� ��ġ�ϴ� ���ڵ� ����
		System.out.println("login : " + vo.getUserid() + " " + vo.getUserid());
		MemberVO mvo = service.loginCheck(vo);
		ModelAndView mav = new ModelAndView();
		
		//�α��� ����: session�� ���̵�� �̸��� �����Ѵ�. -> Ȩ�������� �̵�
		 if(mvo != null) {
			 System.out.println("login ok");
			 session.setAttribute("logId", mvo.getUserid());
			 session.setAttribute("logName", mvo.getUserid());
			 session.setAttribute("logStatus", "Y");
			 System.out.println("login mvo : " + mvo.getUserid()  + " " + mvo.getUserpwd());
			 //��Ʈ�ѷ����� �ٸ� ��Ʈ�ѷ� �����ּҸ� �ٷ� ȣ���Ѵ�.(����)
			 mav.setViewName("redirect:/");
		 }else {//�α׸� ���� : �α��� ������ �̵�
			 System.out.println("login failed");
			 mav.setViewName("redirect:loginForm");
		 }
		
		return mav;
	}
	//�α׾ƿ�
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	//ȸ������ ���� (��)
	@GetMapping("memberEdit")
	public ModelAndView memberEdit(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userid = (String)session.getAttribute("logId");
		MemberVO vo = service.memberSelect(userid);
		
		mav.addObject("vo", vo);
		mav.setViewName("member/memberEdit");
		return mav;
	}
	//ȸ�� ���� ���� (DB)
	@PostMapping("memberEditOk")
	public ModelAndView memberEdit(MemberVO vo, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//session �α��� ���̵�...
		
		System.out.println("memberEdit start");
		vo.setUserid((String)session.getAttribute("logId"));
		int cnt = service.memberUpdate(vo);
		System.out.println("update cnt : " + cnt);
		mav.setViewName("redirect:memberEdit");
		return mav;
	}
	
	//���̵� �ߺ��˻� 
	@PostMapping("memberIdCheck")
	@ResponseBody
	public int idCheck(String userid) {
		System.out.println("userid : " + userid);
		int cnt = service.idCheck(userid);
		
		System.out.println("idCheck cnt : " + cnt + " userid : " +  userid);
		return cnt;
	}
	
}
