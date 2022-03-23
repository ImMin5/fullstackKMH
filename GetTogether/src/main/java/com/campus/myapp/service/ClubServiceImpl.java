package com.campus.myapp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.ClubDAO;
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
	public ClubVO clubSelect(int no) {
		return dao.clubSelect(no);
	}
	@Override
	public ClubVO clubSelectAdmin(String admin, String clubid) {
		return dao.clubSelectAdmin(admin, clubid);
	}
	

}
