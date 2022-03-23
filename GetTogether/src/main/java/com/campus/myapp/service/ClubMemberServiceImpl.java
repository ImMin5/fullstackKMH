package com.campus.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.ClubMemberDAO;
import com.campus.myapp.vo.ClubMemberVO;


@Service
public class ClubMemberServiceImpl implements ClubMemberService {
	
	@Inject
	ClubMemberDAO dao;
	
	@Override
	public int clubMemberInsert(ClubMemberVO vo) {
		return dao.clubMemberInsert(vo);
	}

	@Override
	public List<ClubMemberVO> clubMemberSelect(String userid) {
		return dao.clubMemberSelect(userid);
	}

	@Override
	public int clubMemberCheck(String userid, int clubid) {
		return dao.clubMemberCheck(userid, clubid);
	}
	
}
