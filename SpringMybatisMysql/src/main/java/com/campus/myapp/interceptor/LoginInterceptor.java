package com.campus.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	//��Ʈ�ѷ��� ȣ��Ǳ����� ����� �޼ҵ�
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//false  : �α������� ����
		//true : �ش� ��Ʈ�ѷ��� �̵��Ѵ�.
		
		//request��ü���� session��ü�� ������
		HttpSession session = request.getSession();
		
		//�α��� ���� ���ϱ�
		String logStatus = (String)session.getAttribute("logStatus");
		
		if(logStatus != null && logStatus.equals("Y")) {
			//�α��� ��
			return true;
		}
		else {
			//�α��� ���� �ƴ�
			response.sendRedirect(request.getContextPath()+"/member/loginForm");
			return false;
		}
		
	}
}
