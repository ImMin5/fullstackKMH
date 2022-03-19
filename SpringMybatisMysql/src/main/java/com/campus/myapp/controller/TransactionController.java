package com.campus.myapp.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.BoardService;
import com.campus.myapp.vo.BoardVO;

@Controller
public class TransactionController {
	@Inject
	BoardService service;
	
	//root-context.xml에 있는 transcation 객체를 가져오기
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@RequestMapping("/board/boardTran")
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public ModelAndView transactionTest() {
		//트랜잭션 처리를 하기 위한 객체 생성
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager.getTransaction(def);
		ModelAndView mav = new ModelAndView();
		
		try {
			BoardVO vo1 = new BoardVO();
			vo1.setSubject("aa99999");
			vo1.setContent("bbb9999");
			vo1.setUserid("goguma");
			vo1.setIp("127.0.0,1");
			service.boardInsert(vo1);
			
			System.out.println("트랜 1");
			BoardVO vo2 = new BoardVO();
			vo2.setSubject("ccc8888");
			vo2.setContent("dddd8888");
			vo2.setUserid("goguma");
			vo2.setIp("127.0.0,1");
			
			service.boardInsert(vo2);
			System.out.println("트랜 2");
			
			transactionManager.commit(status);
		}
		catch(Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}
		
		mav.setViewName("redirect:/board/boardList");
		return mav;
	}
}
