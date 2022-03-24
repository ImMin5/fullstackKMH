package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ClubVO;

public interface ClubService {
	public int clubInsert(ClubVO vo);
	public List<ClubVO> clubSelect(String userid);
	public ClubVO clubSelectOne(int no);
	public List<ClubVO> clubSelectPublic(boolean ispublic,  String clubid);
	public ClubVO clubSelectAdmin(String admin, String clubid);
	public ClubVO clubSelectMember(String userid, int no);
	public int clubUpdatePost(int clubno);
	
}
