package com.campus.myapp.dao;

import com.campus.myapp.vo.ClubVO;

public interface ClubDAO {
	public int clubInsert(ClubVO vo);
	public ClubVO clubSelect(int no);
	public ClubVO clubSelectAdmin(String admin, String clubid);
}
