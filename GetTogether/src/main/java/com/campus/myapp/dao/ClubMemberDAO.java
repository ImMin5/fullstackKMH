package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.ClubMemberVO;

public interface ClubMemberDAO {
	public int clubMemberInsert(ClubMemberVO vo);
	public List<ClubMemberVO>  clubMemberSelect(String userid);
	public int clubMemberCheck(String userid, int clubid);
}
