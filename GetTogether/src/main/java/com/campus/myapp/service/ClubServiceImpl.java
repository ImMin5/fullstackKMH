package com.campus.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.ClubDAO;
import com.campus.myapp.vo.ClubInviteVO;
import com.campus.myapp.vo.ClubVO;

@Service
public class ClubServiceImpl implements ClubService {
	@Inject
	ClubDAO dao;
	@Override
	public int clubInsert(ClubVO vo) {
		return dao.clubInsert(vo);
	}
	@Override
	public List<ClubVO> clubSelect(String userid) {
		return dao.clubSelect(userid);
	}
	@Override
	public ClubVO clubSelectAdmin(String admin, String clubid) {
		return dao.clubSelectAdmin(admin, clubid);
	}
	@Override
	public List<ClubVO> clubSelectPublic(boolean ispublic, String userid){
		return dao.clubSelectPublic(ispublic, userid);
	}
	@Override
	public ClubVO clubSelectOne(int no) {
		return dao.clubSelectOne(no);
	}
	@Override
	public ClubVO clubSelectMember(String userid, int no) {
		return dao.clubSelectMember(userid, no);
	}
	@Override
	public int clubUpdatePost(int clubno) {
		return dao.clubUpdatePost(clubno);
	}
	@Override
	public int clubInsertInvite(ClubInviteVO vo) {
		return dao.clubInsertInvite(vo);
	}
	@Override
	public ClubInviteVO clubInviteSelect(String userid, int clubno) {
		return dao.clubInviteSelect(userid, clubno);
	}
	@Override
	public List<ClubInviteVO> clubInviteSelectIsInvite(int clubno, boolean invite) {
		return dao.clubInviteSelectIsInvite(clubno, invite);
	}
	

}
