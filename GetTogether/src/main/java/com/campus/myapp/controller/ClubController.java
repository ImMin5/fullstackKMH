package com.campus.myapp.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.campus.myapp.service.ClubMemberService;
import com.campus.myapp.service.ClubService;
import com.campus.myapp.service.MemberService;
import com.campus.myapp.vo.ClubMemberVO;
import com.campus.myapp.vo.ClubVO;
import com.campus.myapp.vo.MemberVO;

@RestController
public class ClubController {
	
	@Inject
	ClubService service;
	@Inject
	ClubMemberService serviceGM;
	@Inject
	MemberService serviceM;
	
	
	//로그인후의 main 페이지 view
	@GetMapping("/main")
	public ModelAndView main(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userid = (String)session.getAttribute("logId");
		
		//userid가 소속된 club id를 구해봄
		List<ClubMemberVO> clubMemberList = serviceGM.clubMemberSelect(userid);
		System.out.println(userid + "가 소속된 클럽 수 : "+ clubMemberList.size());
		
		//userid가 소속된 클럽을 구하는 과정
		List<ClubVO> clubList = new ArrayList<ClubVO>();
		
		for(ClubMemberVO vo : clubMemberList) {
			int no = vo.getClubno();
			System.out.println("클럽 no :" + vo.getClubno());
			System.out.println("가입일 : " + vo.getJoindate());
			clubList.add(service.clubSelect(no));
			
		}
		System.out.println("소속된 클럽 수 결과: "+ clubList.size());
		mav.addObject("clublist", clubList);
		mav.setViewName("main");
		return mav;
	}
	
	//group 생성 view
	@GetMapping("/main/club/clubForm")
	public ModelAndView clubForm(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/club/clubForm");
		return mav;
	}
	
	//클럽 생성 ok
	@PostMapping("/main/club/clubFormOk")
	public ResponseEntity<String> clubFormOk(ClubVO vo, HttpSession session, HttpServletRequest request){
		String userid = (String)session.getAttribute("logId");
		
		System.out.println(vo.toString());
		System.out.println("group admin : " + userid);
		System.out.println("group name : " + vo.getClubid());
		System.out.println("group description : " + vo.getDescription());
		System.out.println("group thumbnail : " +vo.getClubthumbnail());
		System.out.println("group ispublic : " + vo.getIspublic());
		
	
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		String msg = "";
		
		try {
			if(vo.getClubthumbnail() == null || vo.getClubthumbnail().equals("")) vo.setClubthumbnail("thumbnail_01.jpg");
			//클럽 생성
			vo.setClubadmin(userid);
			System.out.println("club insert : " + service.clubInsert(vo));
			
			// 생성된 클럽의 정보를 받아옴 -> clubno를 알기 위해서
			vo = service.clubSelectAdmin(userid, vo.getClubid());
			
			//그룹 맴버 생성
			ClubMemberVO cvo = new ClubMemberVO();
			cvo.setClubno(vo.getNo());
			cvo.setUserid(userid);
			System.out.println("club member : " + cvo.getUserid());
			System.out.println("club no :  " + cvo.getClubno());
			serviceGM.clubMemberInsert(cvo);
			
			//그룹페이지로 이동
			msg = getSuccessMessage("그룹 생성 성공",request.getContextPath()+"/main/club/"+vo.getNo()); 
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	//클럽 view
	@GetMapping("/main/club/{clubno}")
	public ModelAndView club(@PathVariable("clubno") int clubno, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userid = (String)session.getAttribute("logId");
		
		//접속한 사람이 클럽의 회원인지 판별한다.
		int result = serviceGM.clubMemberCheck(userid, clubno);
		
		if(result > 0) {
			//클럽의 정보를 웹페이지에 전달.
			ClubVO vo = service.clubSelect(clubno);
			System.out.println("clubdi : " + vo.getClubid());

			
			//클럽 관리자의 닉네임을 전달
			System.out.println(vo.getClubadmin());
			MemberVO adminvo = serviceM.memberSelectOne(vo.getClubadmin());
			mav.addObject("clubadmin", adminvo.getUsername());
			
			//클럽의 관리자 권한
			if(vo.getClubadmin().equals(userid)) 
				mav.addObject("adminStatus","Y");
			else
				mav.addObject("adminStatus","N");
			mav.addObject("cvo", vo);
			mav.setViewName("club/club");
		}
		else {
			mav.setViewName("redierect:/main");
		}
		
		
		return mav;
	}
	
	//클럽 지도 view
	@GetMapping("/main/club_info/{clubno}")
	public ModelAndView clubMap(@PathVariable("clubno") int clubno, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userid = (String)session.getAttribute("logId");
		
		//접속한 사람이 클럽의 회원인지 판별한다.
		int result = serviceGM.clubMemberCheck(userid, clubno);
		
		if(result > 0) {
			//클럽의 정보를 웹페이지에 전달.
			ClubVO vo = service.clubSelect(clubno);
			System.out.println("clubdi : " + vo.getClubid());

			
			//클럽 관리자의 닉네임을 전달
			System.out.println(vo.getClubadmin());
			MemberVO adminvo = serviceM.memberSelectOne(vo.getClubadmin());
			mav.addObject("clubadmin", adminvo.getUsername());
			
			//클럽의 관리자 권한
			if(vo.getClubadmin().equals(userid)) 
				mav.addObject("adminStatus","Y");
			else
				mav.addObject("adminStatus","N");
			mav.addObject("cvo", vo);
			mav.setViewName("club/club_map");
		}
		else {
			mav.setViewName("redierect:/main");
		}
		
		
		return mav;
	}
	
	//클럽 관리자 view
	@GetMapping("/main/club/{clubno}/admin")
	public ModelAndView clubAdmin(@PathVariable("clubno") int clubno, HttpSession session) {
		String userid = (String)session.getAttribute("logId");
		ModelAndView mav = new ModelAndView();
		
		
		ClubVO vo = service.clubSelect(clubno);
		
		if(userid.equals(vo.getClubadmin())){
			mav.setViewName("club/club_admin");
		}
		else {
			mav.setViewName("redirect:main/club/"+clubno);
		}
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
