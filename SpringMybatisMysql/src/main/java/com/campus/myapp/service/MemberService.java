package com.campus.myapp.service;

import com.campus.myapp.vo.MemberVO;

public interface MemberService {
	
	//회원등록
	public int memberInsert(MemberVO vo);
	//로그인
	public MemberVO loginCheck(MemberVO vo);
	//회원정보 수정(폼)
	public MemberVO memberSelect(String userid);
	//회원정보수정(DB)
	public int memberUpdate(MemberVO vo);
	//중복체크
	public int idCheck(String userid);
}
