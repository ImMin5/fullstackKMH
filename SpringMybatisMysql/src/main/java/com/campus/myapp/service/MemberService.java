package com.campus.myapp.service;

import com.campus.myapp.vo.MemberVO;

public interface MemberService {
	
	//ȸ�����
	public int memberInsert(MemberVO vo);
	//�α���
	public MemberVO loginCheck(MemberVO vo);
	//ȸ������ ����(��)
	public MemberVO memberSelect(String userid);
	//ȸ����������(DB)
	public int memberUpdate(MemberVO vo);
	//�ߺ�üũ
	public int idCheck(String userid);
}
