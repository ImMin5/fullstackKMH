package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataService {
	//�ڷ�� �� ����
	public int dataInsert(DataVO vo);
	// �ڷ�� �� ����Ʈ
	public List<DataVO> dataSelectAll();
	// �ڷ�� �� ����
	public DataVO dataSelect(int no);
}
