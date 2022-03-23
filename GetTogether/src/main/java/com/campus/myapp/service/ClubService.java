package com.campus.myapp.service;

import com.campus.myapp.vo.ClubVO;

public interface ClubService {
	public int clubInsert(ClubVO vo);
	public ClubVO clubSelect(int no);
	public ClubVO clubSelectAdmin(String admin, String clubid);
	
}
