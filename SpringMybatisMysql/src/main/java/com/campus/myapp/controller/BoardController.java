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
		
		//�� ���ڵ�
		pvo.setTotalRecord(service.totalRecord(pvo));
		
		//DBó��
		mav.addObject("list", service.boardList(pvo));
		mav.addObject("pvo",pvo);
		
		mav.setViewName("board/boardList");
		return mav;
	}
	//�� ��� ��
	
	@GetMapping("boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	//�� ���
	@PostMapping("boardWriteOk")
	public ResponseEntity<String> boardWriteOk(BoardVO vo, HttpServletRequest request) {
		vo.setIp(request.getRemoteAddr()); //������ ������
		//�۾��� - session �α��� ���̵� ���Ѵ�.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null; //�����Ϳ� ó�����¸� ������.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		//headers.setContentType(new MediaType("text","html",Charset.forName("UTF-8")));
		
		try {
			
			service.boardInsert(vo);
			//������
			System.out.println("�� ���");
			String msg = "<script>";
			msg += " console.log('�۵��');";
			msg += "alert('���� ���ϵǾ����ϴ�.');";
			msg += "location.href='/myapp/board/boardList';";
			msg += "</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK); //200
			
		}catch(Exception e) {
			System.out.println("�� ��� ����");
			e.printStackTrace();
			//��� �ȵ�..
			String msg = "<script>";
			msg += "alert('���� ���� ����.');";
			msg += "history.back();";
			msg += "</script>";
			entity = new ResponseEntity<String>(msg,headers,HttpStatus.BAD_REQUEST); //4
		}
		
		return entity;
	}
	
	//�۳��� ����
	@GetMapping("boardView")
	public ModelAndView boardView(int no) {
		ModelAndView mav = new ModelAndView();
			
		service.hitCount(no);
		mav.addObject("vo", service.boardSelect(no));
		mav.setViewName("board/boardView");
		return mav;
	}
	//�� ���� ��
	@GetMapping("boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", service.boardSelect(no));
		mav.setViewName("board/boardEdit");
		
		return mav;
	}
	
	//�� ���� 
	@PostMapping("boardEditOk")
	public ResponseEntity<String> boardEditOk(BoardVO vo, HttpSession session) {
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text","html",Charset.forName("UTF-8")));
		
		vo.setUserid((String)session.getAttribute("logId"));
		try {
			int result = service.boardUpdate(vo);
			if(result > 0) {
				//���� ����
				entity = new ResponseEntity<String>(getSuccessMessage("�� ���� ����","/myapp/board/boardView?no="+vo.getNo()),headers, HttpStatus.OK);
			}
			else {
				//���� ����
				entity = new ResponseEntity<String>(getFailMessage("�� ���� ����"),headers, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			//���� ����
			e.printStackTrace();
			entity = new ResponseEntity<String>(getFailMessage("�� ���� ����"),headers, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	//�� ����
	@GetMapping("boardDel")
	public ModelAndView boardDel(int no, HttpSession session){
		String userid = (String)session.getAttribute("logId");
		
		int result = service.boardDelete(no, userid);
		
		ModelAndView mav = new ModelAndView();
		if(result > 0) {
			mav.setViewName("redirect:boardList"); //list�� �̵��ϴ� ��Ʈ�ѷ� ȣ���Ѵ�.
		}
		else {
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		
		return mav;
		
	}
	//�� ���� �޼���
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
