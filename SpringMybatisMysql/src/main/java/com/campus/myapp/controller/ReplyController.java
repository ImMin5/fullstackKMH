package com.campus.myapp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campus.myapp.service.ReplyService;
import com.campus.myapp.vo.ReplyVO;

@RestController
public class ReplyController {
	@Inject
	ReplyService service;
	
	//��۵��
	@RequestMapping(value="/reply/writeOk",method=RequestMethod.POST)
	public int writeOk(ReplyVO vo, HttpSession session) {
		vo.setUserid((String)session.getAttribute("logId"));
		return service.replyWrite(vo);
	}
	
	//��� ��� 
	@RequestMapping("/reply/list")
	public List<ReplyVO> list(int no){
		return service.replyList(no);
	}
	//��� ���
	@PostMapping("/reply/editOk")
	public int EditOk(ReplyVO vo, HttpSession session) {
		System.out.println("reply edit start");
		vo.setUserid((String)session.getAttribute("logId"));
		return service.replyEdit(vo);
	}
	
	//��� ����
	@GetMapping("/reply/del")
	public int delOk(int replyno, HttpSession session) {
		System.out.println("replyno : " + replyno + " id : " + session.getAttribute("logId"));
		return service.replyDel(replyno, (String)session.getAttribute("logId"));
	}
}