package com.campus.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.ReviewDAO;
import com.campus.myapp.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Inject
	ReviewDAO dao;
	
	@Override
	public int reviewInsert(ReviewVO vo) {
		return dao.reviewInsert(vo);
	}

	@Override
	public List<ReviewVO> reviewSelect(int clubno) {
		return dao.reviewSelect(clubno);
	}

	@Override
	public ReviewVO reviewSelectOne(int no) {
		return dao.reviewSelectOne(no);
	}
	
}
