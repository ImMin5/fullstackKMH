package com.campus.myapp.controller;


import java.nio.charset.Charset;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.BoardService;
import com.campus.myapp.vo.BoardVO;
import com.campus.myapp.vo.PagingVO;

@RestController // contoller + responsebody
@RequestMapping("/board/")
public class BoardController {
	@Inject
	BoardService service;
	
	@GetMapping("boardList")
	public ModelAndView boardList(PagingVO pvo) {
		ModelAndView mav = new ModelAndView();
		
		//총 레코듯구
		pvo.setTotalRecord(service.totalRecord(pvo));
		
		//DB처리
		mav.addObject("list", service.boardList(pvo));
		mav.addObject("pvo",pvo);
		
		mav.setViewName("board/boardList");
		return mav;
	}
	//글 등록 폼
	
	@GetMapping("boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	//글 등록
	@PostMapping("boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardVO vo, HttpServletRequest request) {
		vo.setIp(request.getRemoteAddr()); //접속자 아이피
		//글쓴이 - session 로그인 아이디를 구한다.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null; //데이터와 처리상태를 가진다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		//headers.setContentType(new MediaType("text","html",Charset.forName("UTF-8")));
		
		try {
			
			service.boardInsert(vo);
			//정상구현
			System.out.println("글 등록");
			String msg = "<script>";
			msg += " console.log('글등록');";
			msg += "alert('글이 동록되었습니다.');";
			msg += "location.href='/myapp/board/boardList';";
			msg += "</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK); //200
			
		}catch(Exception e) {
			System.out.println("글 등록 실패");
			e.printStackTrace();
			//등록 안됨..
			String msg = "<script>";
			msg += "alert('글이 동록 실패.');";
			msg += "history.back();";
			msg += "</script>";
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.BAD_REQUEST); //4
		}
		
		return entity;
	}
	
	//글내용 보기
	@GetMapping("boardView")
	public ModelAndView boardView(int no) {
		ModelAndView mav = new ModelAndView();
			
		service.hitCount(no);
		mav.addObject("vo", service.boardSelect(no));
		mav.setViewName("board/boardView");
		return mav;
	}
	//글 수정 폼
	@GetMapping("boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", service.boardSelect(no));
		mav.setViewName("board/boardEdit");
		
		return mav;
	}
	
	//글 수정 
	@PostMapping("boardEditOk")
	public ResponseEntity<String> boardEditOk(BoardVO vo, HttpSession session) {
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","html",Charset.forName("UTF-8")));
		
		vo.setUserid((String)session.getAttribute("logId"));
		try {
			int result = service.boardUpdate(vo);
			if(result > 0) {
				//수정 성공
				entity = new ResponseEntity<String>(getSuccessMessage("글 수정 성공","/myapp/board/boardView?no="+vo.getNo()),headers, HttpStatus.OK);
			}
			else {
				//수정 실패
				entity = new ResponseEntity<String>(getFailMessage("글 수정 실패"),headers, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			//수정 실패
			e.printStackTrace();
			entity = new ResponseEntity<String>(getFailMessage("글 수정 실패"),headers, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	//글 삭제
	@GetMapping("boardDel")
	public ModelAndView boardDel(int no, HttpSession session){
		String userid = (String)session.getAttribute("logId");
		
		int result = service.boardDelete(no, userid);
		
		ModelAndView mav = new ModelAndView();
		if(result > 0) {
			mav.setViewName("redirect:boardList"); //list로 이동하는 컨트롤러 호출한다.
		}
		else {
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
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
