package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataDAO {
	//자료실 글 쓰기
	public int dataInsert(DataVO vo);
	// 자료실 글 리스트
	public List<DataVO> dataSelectAll();
	// 자료실 글 보기
	public DataVO dataSelect(int no);
}
