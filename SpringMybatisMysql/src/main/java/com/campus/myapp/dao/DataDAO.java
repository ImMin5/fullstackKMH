package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataDAO {
	//�ڷ�� �� ����
	public int dataInsert(DataVO vo);
	// �ڷ�� �� ����Ʈ
	public List<DataVO> dataSelectAll();
	// �ڷ�� �� ����
	public DataVO dataSelect(int no);
}
