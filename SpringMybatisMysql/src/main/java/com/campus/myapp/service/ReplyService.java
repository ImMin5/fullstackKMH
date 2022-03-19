package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ReplyVO;

public interface ReplyService {
	//��۵��
	public int replyWrite(ReplyVO vo);
	//���۸��
	public List<ReplyVO> replyList(int no);
	//��ۼ���
	public int replyEdit(ReplyVO vo);
	//��ۻ���
	public int replyDel(int replyno, String userid);


}
