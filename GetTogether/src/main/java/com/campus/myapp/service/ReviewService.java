package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ReviewVO;

public interface ReviewService {
	public int reviewInsert(ReviewVO vo);
	public List<ReviewVO> reviewSelect(int clubno);
	public ReviewVO reviewSelectOne(int no);
}
