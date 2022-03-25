package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.ClubInviteVO;
import com.campus.myapp.vo.ClubVO;

public interface ClubDAO {
	public int clubInsert(ClubVO vo);
	public List<ClubVO> clubSelect(String userid);
	public ClubVO clubSelectOne(int no);
	public List<ClubVO> clubSelectPublic(boolean ispublic, String userid);
	public ClubVO clubSelectAdmin(String admin, String clubid);
	public ClubVO clubSelectMember(String userid, int no);
	public int clubUpdatePost(int clubno);
	public int clubUpdateMember(int clubno);
	public int clubInsertInvite(ClubInviteVO vo);
	
	public ClubInviteVO clubInviteSelect(String userid, int clubno);
	public List<ClubInviteVO> clubInviteSelectIsInvite(int clubno, boolean invite);
	public int clubInviteUpdateComplete(int no);
}
