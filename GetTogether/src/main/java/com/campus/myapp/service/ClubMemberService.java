package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ClubMemberVO;

public interface ClubMemberService {
	public int clubMemberInsert(ClubMemberVO vo);
	public List<ClubMemberVO>  clubMemberSelect(String userid);
	public int clubMemberCheck(String userid, int clubid);
}
