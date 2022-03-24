package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.ReviewVO;

public interface ReviewDAO {
	public int reviewInsert(ReviewVO vo);
	public List<ReviewVO> reviewSelect(int clubno);
	public ReviewVO reviewSelectOne(int no);
}
